import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'
import Modal from './modules/modal'
import { Tabs, Form } from 'antd'

const TabPane = Tabs.TabPane

class NormalItems extends Component {

    // 切换 div
    switchTab(currentIndex) {
        store.currentIndex = parseInt(currentIndex)
        store.actions.search(1)
    }

    render() {
        return <Provider store={store} form={this.props.form}>
            <Fragment>

                {/*<div style={{ float: 'right', marginRight: 50 }}>*/}
                    <Tabs defaultActiveKey="1" onChange={this.switchTab}>
                        <TabPane tab="全部的" key="1" />
                        <TabPane tab="已通过" key="2" />
                        <TabPane tab="未通过" key="3" />
                        <TabPane tab="待审核" key="4" />
                        <TabPane tab="常规工作" key="5" />
                        <TabPane tab="临时工作" key="6" />
                    </Tabs>
                {/*</div>*/}

                <div className="content">
                    <Table/>
                    <Modal/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default Form.create()(NormalItems)