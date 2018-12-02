import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';
import util from '../../../common/util'

class detailStockInOutStore {
    @observable dataSource = []   // 表格每行数据封装为对象数组
    @observable loading = false
    @observable current = 1
    @observable all = 0     // 行数据条数
    @observable pageSize = 10

    actions = {
        // 筛选数据
        search: action((values, pageNumber, pageSize) => {
            let beginTime = ''
            let endTime = ''
            if (values.time !== undefined){
                beginTime = util.gmtToStr(values.time[0]["_d"])
                endTime = util.gmtToStr(values.time[1]["_d"])
            }
            const { type, proNumber} = values
            var url = "/api/warehouse/operation/queryAllDetail?";
            url = url + (type === undefined ? '': "type="+type+'&')
            url = url + (proNumber === undefined || proNumber === '' ? '': "proNumber="+proNumber+'&')
            url = url + (beginTime === '' ? '': "beginTime="+beginTime+'&')
            url = url + (endTime === '' ? '': "endTime="+endTime+'&')
            url = url + "pageNumber="+ pageNumber + "&pageSize="+pageSize
            this.loading = true

            axios.get(url).then(res => {
                if (res.data.result !== null){
                    const dataSource = res.data.result.data;
                    const all = res.data.result.all;

                    this.loading = false
                    this.current = pageNumber
                    this.dataSource = dataSource
                    this.all = all
                }else{
                    message.error(res.data.status.message)
                }
            })
        }),

        // 改变显示条数
        handlePageSizeChange: action((form, value) => {
            this.pageSize = value
            this.actions.search(form, 1, this.pageSize)
        }),
    }
}

export default new detailStockInOutStore()