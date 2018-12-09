import {observable, action } from 'mobx';
import axios from "axios";
import { message } from 'antd';

class store {
    // 分页
    @observable current = 1
    @observable pageSize = 10
    // 表格源数组
    @observable dataSource = []

    @observable visible = false


    actions = {
        showModal: action(() => {
            this.visible = true
        }),

        hideModal: action(() => {
            this.visible = false
        })
    }
}

export default new store()