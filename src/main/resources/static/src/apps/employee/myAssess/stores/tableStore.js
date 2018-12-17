import {observable, action } from 'mobx';
import axios from "axios";
import { message, Modal } from 'antd';

class tableStore {
    @observable workName = undefined   // 查询条件：工作项名称
    @observable beginTime = undefined
    @observable endTime = undefined

    @observable current = 1
    @observable pageSize = 8

    @observable currentIndex = 1

    @observable dataSource = []
    @observable all = undefined

    @observable loading = false

    actions = {
        search: action((pageNumber) => {
            this.loading = true
            let url = '/api/work/assess/employee/queryAssess?'
            url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")
            url = url + (this.beginTime === '' || this.beginTime === undefined ? '': "beginTime="+this.beginTime+'&')
            url = url + (this.endTime === '' || this.endTime === undefined ? '': "endTime="+this.endTime+'&')
            url = url + "pageNumber="+ pageNumber + "&pageSize="+ this.pageSize + '&'

            switch (this.currentIndex) {
                case 2:
                    url = url + "assessGrade=优"
                    break
                case 3:
                    url = url + "assessGrade=中"
                    break
                case 4:
                    url = url + "assessGrade=良"
                    break
                case 5:
                    url = url + "assessGrade=差"
                    break
                case 6:
                    url = url + "assessStatus=1"
                    break
                case 7:
                    url = url + "assessStatus=0"
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

    }
}

export default new tableStore()