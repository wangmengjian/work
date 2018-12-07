import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';
import util from '../../../../common/util'

class tableStore {
    // 分页
    @observable current = 1
    @observable pageSize = 10
    // 表格源数组
    @observable dataSources = []


    actions = {

    }
}

export default new tableStore()