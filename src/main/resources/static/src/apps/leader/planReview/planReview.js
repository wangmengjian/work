import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/store'
import Table from './modules/table'
import Modal from './modules/modal'
import { Tabs } from 'antd'

const TabPane = Tabs.TabPane

class planReview extends Component {

    // 切换 div
    switchTab(currentIndex) {
        store.currentIndex = parseInt(currentIndex)
        store.actions.search(1, store.pageSize)
    }

    render() {
        return <Provider store={store}>
            <Fragment>
                <Tabs defaultActiveKey="1" onChange={this.switchTab}>
                    <TabPane tab="全部的" key="1" />
                    <TabPane tab="未完成" key="2" />
                    <TabPane tab="已完成" key="3" />
                    <TabPane tab="常规工作" key="4" />
                    <TabPane tab="临时工作" key="5" />
                </Tabs>
                <div className="content">
                    <Table/>
                    <Modal/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default planReview