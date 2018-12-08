import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';

class tableStore {
    @observable workName = undefined   // 查询条件：工作项名称
    // 分页
    @observable current = 1
    @observable pageSize = 10
    // 表格源数组
    @observable dataSource = []
    @observable all = undefined

    @observable loading = false


    actions = {
        search: action((pageNumber, pageSize) => {

            this.loading = true

            let url = '/api/work/schedule/employee/queryWork?'

            url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")
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
                    } else {
                        message.error("提交数据失败: " + response.data.status.message)
                    }
                })
        }),

        // 改变显示条数
        handlePageSizeChange: action((value) => {
            this.pageSize = value
            this.actions.search(1, this.pageSize)
        }),
    }
}

export default new tableStore()