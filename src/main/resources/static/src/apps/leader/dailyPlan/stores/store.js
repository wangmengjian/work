import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';

class store {
    // 分页
    @observable current = 1
    @observable pageSize = 10
    // 表格源数组
    @observable dataSource = []

    // 选择
    @observable selectedRowKeys = []         // 选择框数据


    actions = {

    }
}

export default new store()