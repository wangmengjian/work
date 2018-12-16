import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'
import Modal from './modules/modal'
import SonTableModal from './modules/sonTable'
import { Form, Tabs } from 'antd'

const TabPane = Tabs.TabPane

class DailyPlan extends Component {

    // 切换 div
    switchTab(currentIndex) {
        store.currentIndex = parseInt(currentIndex)
        store.actions.search()
    }

    render() {
        return <Provider store={store} form={this.props.form}>
            <Fragment>
                {/*<div style={{ float: 'right', marginRight: 50 }}>*/}
                    <Tabs defaultActiveKey="1" onChange={this.switchTab}>
                        <TabPane tab="全部的" key="1" />
                        <TabPane tab="未完成" key="2" />
                        <TabPane tab="已完成" key="3" />
                        <TabPane tab="常规工作" key="4" />
                        <TabPane tab="临时工作" key="5" />
                    </Tabs>
                {/*</div>*/}
                <div className="content">
                    <Table/>
                    <Modal/>
                    <SonTableModal/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default Form.create()(DailyPlan)