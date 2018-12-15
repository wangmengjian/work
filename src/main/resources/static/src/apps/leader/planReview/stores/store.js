import {observable, action } from 'mobx';
import axios from "axios";
import { Modal } from 'antd';

class store {
    // 分页
    @observable all = undefined
    @observable current = 1
    @observable pageSize = 8

    @observable currentIndex = 1    // 当前所在标签页

    // 表格源数组
    @observable dataSource = []

    @observable loading = false
    @observable loadingButton = false

    // 筛选
    @observable workName = undefined
    @observable employeeId = undefined
    @observable beginTime = undefined
    @observable endTime = undefined

    @observable employeeList = []

    // 选择
    @observable selectedRowKeys = []         // 选择框数据
    @observable visible = false
    @observable accessGrade = undefined

    actions = {
        search: action((pageNumber, pageSize) => {
            this.reason = undefined
            this.loading = true
            this.selectedRowKeys = []

            let url = '/api/work/assess/queryAssessWork?'

            url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")
            url = url + (( this.employeeId === undefined || this.employeeId === '' ) ? '' : "employeeId="+ this.employeeId + "&")


            url = url + (this.beginTime === '' || this.beginTime === undefined ? '': "beginTime="+this.beginTime+'&')
            url = url + (this.endTime === '' || this.endTime === undefined ? '': "endTime="+this.endTime+'&')
            url = url + "pageNumber="+ pageNumber + "&pageSize=" + pageSize + '&'

            console.log(this.currentIndex)
            switch (this.currentIndex) {
                case 2:
                    url = url + "finishStatus=uncompleted"
                    break
                case 3:
                    url = url + "finishStatus=completed"
                    break
                case 4:
                    url = url + "workFrom=w3"
                    break
                case 5:
                    url = url + "workFrom=w2"
                    break
            }

            axios({
                method: 'get',
                url: url,
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.all = response.data.result.all
                        this.current = pageNumber
                        this.dataSource = response.data.result.data
                        this.loading = false
                    } else {
                        Modal.error({
                            title: '获取数据失败',
                            content: response.data.status.message
                        });
                    }
                })
        }),

        submit: action((form) => {
            this.loadingButton = true
            let object = new FormData();
            form.validateFields((err, values) => {
                if (!err) {
                    for (let i = 0; i < this.selectedRowKeys.length; i++) {
                        object.append("workAssessList["+i+"].scheduleDetailId", this.selectedRowKeys[i]);
                        object.append("workAssessList["+i+"].assessGrade", values['assessGrade']);
                        object.append("workAssessList["+i+"].assessDesc", values['assessDesc']);
                    }
                }
            })

            axios({
                method: 'post',
                url: '/api/work/assess/assessWork',
                data: object
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        Modal.success({
                            title: '提交成功',
                            content: response.data.status.message
                        });
                        this.actions.hideModal()
                        form.resetFields()
                        this.actions.search(1, this.pageSize)
                    } else {
                        Modal.error({
                            title: '提交失败',
                            content: response.data.status.message
                        });
                    }
                    this.loadingButton = false
                })
        }),

        // 改变显示条数
        handlePageSizeChange: action((value) => {
            this.pageSize = value
            this.actions.search(1, this.pageSize*1)
        }),

        showModal: action(() => {
            this.visible = true
        }),

        hideModal: action(() => {
            this.visible = false
        })
    }
}

export default new store()