import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';

class store {
    // 分页
    @observable all = 0
    @observable current = 1
    @observable pageSize = 10
    // 表格源数组
    @observable dataSource = []

    @observable date = undefined    // 查询条件：日期
    @observable employeeList = []       // 查询条件：员工姓名
    @observable employeeId = undefined    // 员工姓名提交查询

    // 选择
    @observable selectedRowKeys = []         // 选择框数据

    @observable loading = false


    actions = {
        search: action((pageNumber, pageSize) => {
            this.loading = true

            let url = '/api/work/schedule/leader/querySchedule?'

            url = url + (( this.date === undefined || this.date === '' ) ? '' : "date="+ this.date + "&")
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
                        if (response.data.result.data !== null) {
                            this.dataSource = response.data.result.data.workScheduleDetailDtoList
                        } else {
                            this.dataSource = []
                        }
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
    }
}

export default new store()