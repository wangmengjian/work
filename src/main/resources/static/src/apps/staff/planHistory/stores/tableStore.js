import {observable, action } from 'mobx';
import { message } from 'antd'
import axios from 'axios'
import util from '../../../../common/util'

class tableStore {
    @observable dataSource = []
    @observable count = 1
    @observable loading = false

    actions = {

        // 筛选数据
        search: action( values => {

            let url = "/api/work/schedule/employee/querySchedule?";

            if (values !== undefined) {
                if (values.date !== undefined) {
                    url = url + (values.date['_d'] === undefined ? '': "date="+util.gmtToStr(values.date["_d"])+"&")
                }
                url = url + (values.workName === undefined ? '': "workName="+values.workName)
            }

            this.loading = true

            axios({
                method: 'get',
                url: url
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        if (response.data.result !== null) {
                            this.dataSource = response.data.result.data.workScheduleDetailDtoList
                        } else {
                            message.success("查询成功，尚无记录")
                            this.dataSource = []
                        }
                        this.loading = false
                    } else {
                        message.error("获取表格数据失败: " + response.data.status.message)
                    }
                })
        }),
    }
}

export default new tableStore()