import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';

class store {
    @observable workName = undefined   // 查询条件：工作项名称

    // 分页
    @observable current = 1
    @observable pageSize = 10
    @observable all = undefined

    // 表格源数组
    @observable dataSource = []
    @observable loading = false

    // 选择
    @observable selectedRowKeys = []         // 选择框数据


    actions = {
        submit: action(() => {
            axios({
                method: 'post',
                url: '/api/work/schedule/employee/newSchedule',
                data: JSON.parse(JSON.stringify(this.selectedRowKeys))
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        message.success("生成计划成功")
                        this.selectedRowKeys = []
                        this.actions.search()
                    } else {
                        message.error("生成计划失败: " + response.data.status.message)
                    }
                })
        }),

        search: action(() => {

            this.loading = true
            let url = '/api/work/schedule/employee/queryWorkPool?'
            url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")

            axios({
                method: 'get',
                url: url,
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.dataSource = response.data.result.data
                        this.loading = false
                    } else {
                        message.error("获取数据失败: " + response.data.status.message)
                    }
                })
        }),

        // 改变显示条数
        // handlePageSizeChange: action((value) => {
        //     this.pageSize = value
        //     this.actions.search(1, this.pageSize*1)
        // }),
    }
}

export default new store()