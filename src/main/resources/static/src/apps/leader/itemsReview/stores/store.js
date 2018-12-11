import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';

class store {
    @observable workName = undefined    // 查询条件：工作项名称
    @observable employeeList = []       // 查询条件：员工姓名
    @observable employeeId = undefined    // 员工姓名提交查询

    @observable reason = undefined
    @observable otherReason = undefined // 其他原因
    @observable reasonChoices = []      // 不通过的原因选择

    // 分页
    @observable current = 1
    @observable pageSize = 10
    // 表格源数组
    @observable dataSource = []
    @observable loading = false
    @observable loadingDisButton = false    // 不通过按钮缓冲
    @observable loadingArgButton = false    // 通过按钮缓冲

    @observable visible = false

    @observable selectedRowKeys = []

    actions = {

        agree: action(() => {
            this.loadingArgButton = true
            axios({
                method: 'post',
                url: '/api/work/audit/leader/agreeAuditWork',
                data: this.selectedRowKeys
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        message.success("审批提交成功")
                        this.loadingArgButton = false
                        this.actions.search(1, 10)
                    } else {
                        message.error("提交失败: " + response.data.status.message)
                    }
                })
        }),

        disagree: action((form) => {
            let data = []
            this.loadingDisButton = true

            if (this.reason !== undefined) {
                for (let i in this.selectedRowKeys) {
                    let object = {}
                    object.auditItemId = this.selectedRowKeys[i]
                    object.auditFailReason = this.reason
                    data.push(object)
                }
            }

            axios({
                method: 'post',
                url: '/api/work/audit/leader/disagreeAuditWork',
                data: data
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.loadingDisButton = false
                        this.actions.hideModal()
                        form.resetFields()
                        message.success("审批提交成功")
                        this.actions.search(1, 10)
                    } else {
                        message.error("提交失败: " + response.data.status.message)
                    }
                })
        }),

        search: action((pageNumber, pageSize) => {
            this.reason = undefined
            this.loading = true

            let url = '/api/work/audit/leader/queryUnAuditedWork?'

            url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")
            url = url + (( this.employeeId === undefined || this.employeeId === '' ) ? '' : "employeeId="+ this.employeeId + "&")
            url = url + "pageNumber="+ pageNumber + "&pageSize=" + pageSize

            axios({
                method: 'get',
                url: url,
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.all = response.data.result.all
                        this.current = pageNumber
                        this.dataSource = response.data.result.data
                        this.selectedRowKeys = []   // 按钮禁用
                        this.loading = false
                    } else {
                        message.error("获取数据失败: " + response.data.status.message)
                    }
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