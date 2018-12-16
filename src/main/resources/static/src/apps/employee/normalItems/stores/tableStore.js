import {observable, action } from 'mobx';
import axios from "axios";
import { message, Modal } from 'antd';

class tableStore {
    @observable workName = undefined   // 查询条件：工作项名称
    // 分页
    @observable current = 1
    @observable pageSize = 8

    @observable currentIndex = 1

    // 表格源数组
    @observable dataSource = []
    @observable all = undefined

    @observable loading = false
    @observable loadingNewItem = false

    @observable fileData = []   // 上传列表源
    @observable visible = false

    actions = {
        search: action((pageNumber) => {
            this.loading = true
            let url = '/api/work/schedule/employee/queryWork?'
            url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")
            url = url + "pageNumber="+ pageNumber + "&pageSize="+ this.pageSize + '&'

            switch (this.currentIndex) {
                case 2:
                    url = url + "auditStatus=agree"
                    break
                case 3:
                    url = url + "auditStatus=disagree"
                    break
                case 4:
                    url = url + "auditStatus=unaudited"
                    break
                case 5:
                    url = url + "workFrom=w3"
                    break
                case 6:
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
                        message.error("提交数据失败: " + response.data.status.message)
                    }
                })
        }),

        submit: action((values, form) => {
            this.loadingNewItem = true
            const object = new FormData()

            if (this.fileData.length > 0) {
                object.append('workAudits[0].file', this.fileData[0])
            }
            object.append('workAudits[0].workFrom', values['workFrom'].substring(0,2))
            object.append('workAudits[0].workName', values['workName'])
            object.append('workAudits[0].workContent', values['workContent'])
            object.append('workAudits[0].workMinutes', values['workMinutes'])

            axios({
                method: 'post',
                url: '/api/work/schedule/employee/addWork',
                data: object
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        Modal.success({
                            title: '提交成功',
                            content: response.data.status.message,
                        });
                        this.actions.hideModal()
                        this.fileData = []
                        form.resetFields()
                    } else {
                        Modal.error({
                            title: '提交失败',
                            content: response.data.status.message,
                        });
                    }
                    this.loadingNewItem = false
                })
        }),

        // 拦截文件上传
        beforeUploadHandle: action(file => {
            // 允许的类型
            const type = [
                'application/pdf',
                'application/vnd.ms-powerpoint',
                'application/msword',
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
            ]
            if (type.indexOf(file.type) > -1) {
                this.fileData = [...this.fileData, file]
                return true
            } else {
                message.error("请上传正确的格式")
                return false
            }
        }),

        // 文件列表的删除
        fileRemove: action(file => {
            const index = this.fileData.indexOf(file)
            let newFileList = this.fileData.slice()
            newFileList = newFileList.filter((value, inde_x) => inde_x != index);
            this.fileData = newFileList
        }),

        // 改变显示条数
        handlePageSizeChange: action((value) => {
            this.pageSize = value
            this.actions.search(1, this.pageSize*1)
        }),

        // 显示对话框
        showModal: action(() => {
            this.visible = true
        }),

        // 隐藏对话框，清除数据
        hideModal: action(() => {
            this.visible = false
        }),
    }
}

export default new tableStore()