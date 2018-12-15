import {observable, action } from 'mobx';
import axios from "axios";
import { message, Modal } from 'antd';

class tableStore {
    // 分页
    @observable current = 1
    @observable pageSize = 8

    @observable fileData = []   // 上传列表源

    // 表格源数组
    @observable dataSource = []
    @observable all = undefined

    @observable loading = false
    @observable loadingButton = false

    @observable id = undefined
    @observable workName = undefined
    @observable employeeId = undefined
    @observable employeeList = []

    @observable visible = false

    actions = {
        search: action((pageNumber) => {
            this.loading = true
            let url = '/api/work/schedule/personnel/queryWork?'
            url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")
            url = url + (( this.employeeId === undefined || this.employeeId === '' ) ? '' : "employeeId="+ this.employeeId + "&")
            url = url + "pageNumber="+ pageNumber + "&pageSize="+ this.pageSize

            axios({
                method: 'get',
                url: url,
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.all = response.data.result.all
                        this.current = pageNumber
                        this.dataSource = response.data.result.data
                    } else {
                        message.error("获取数据失败: " + response.data.status.message)
                    }
                    this.loading = false
                })
        }),

        submit: action((values, form) => {
            this.loadingButton = true
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
                    this.loadingButton = false
                })
        }),

        alter: action((values, form) => {
            this.loadingButton = true
            const data = new FormData()

            data.append("workMinutes", values['workMinutes']);
            data.append("workName", values['workContent']);
            data.append("workContent", values['workContent']);
            data.append("id", this.id);

            // 上传了文件
            if (this.fileData.length > 0) {
                data.append("file", this.fileData[0]);
            }

            axios({
                method: 'post',
                url: '/api/work/schedule/personnel/updateWork',
                data: data
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        Modal.success({
                            title: '提交成功',
                            content: response.data.status.message,
                        })
                        this.actions.hideModal()
                        this.fileData = []
                        form.resetFields()
                        this.actions.search(1)
                    } else {
                        Modal.error({
                            title: '提交失败',
                            content: response.data.status.message,
                        });
                    }
                    this.loadingButton = false
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