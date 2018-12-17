import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'
import { Tabs } from 'antd'

const TabPane = Tabs.TabPane

class MyAssess extends Component {

    // 切换 div
    switchTab(currentIndex) {
        store.currentIndex = parseInt(currentIndex)
        store.actions.search(1)
    }

    render() {
        return <Provider store={store}>
            <Fragment>
                {/*<div style={{ float: 'right', marginRight: 50 }}>*/}
                    <Tabs defaultActiveKey="1" onChange={this.switchTab}>
                        <TabPane tab="全部的" key="1" />
                        <TabPane tab="优" key="2" />
                        <TabPane tab="中" key="3" />
                        <TabPane tab="良" key="4" />
                        <TabPane tab="差" key="5" />
                        <TabPane tab="已考核" key="6" />
                        <TabPane tab="未考核" key="7" />
                    </Tabs>
                {/*</div>*/}

                <div className="content">
                    <Table/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default MyAssess