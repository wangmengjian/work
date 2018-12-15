import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';

class store {
    // 分页
    @observable all = undefined
    @observable current = 1
    @observable pageSize = 8
    // 表格源数组
    @observable dataSource = []

    @observable loading = false

    @observable workName = undefined
    @observable employeeId = undefined
    @observable employeeList = []

    // 选择
    @observable selectedRowKeys = []         // 选择框数据


    actions = {
        search: action((pageNumber, pageSize) => {
            this.reason = undefined
            this.loading = true

            let url = '/api/work/schedule/leader/queryWork?'

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
                        this.loading = false
                        this.selectedRowKeys = []   // 按钮禁用
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
    }
}

export default new store()