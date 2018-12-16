import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'
import Filter from './modules/filter'
import { Form } from 'antd'

class PlanHistory extends Component {

    state= {
        currentIndex: 1
    };

    // 切换 div
    switchTab(currentIndex) {
        this.setState({
            currentIndex
        });
    }

    render() {
        const currentIndex = this.state.currentIndex

        return <Provider store={store} form={this.props.form}>
            <Fragment>
                <ul className="tab">
                    <li className={currentIndex === 1 ? 'on':''} onClick={() => this.switchTab(1)}>全部的</li>
                    <li className={currentIndex === 2 ? 'on':''} onClick={() => this.switchTab(2)}>已通过</li>
                    <li className={currentIndex === 3 ? 'on':''} onClick={() => this.switchTab(3)}>未通过</li>
                </ul>

                <Filter/>

                <Table currentIndex={this.state.currentIndex}/>

            </Fragment>
        </Provider>
    }
}
export default Form.create()(PlanHistory)