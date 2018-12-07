import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'
import Modal from './modules/modal'
import Filter from './modules/filter'
import { Form } from 'antd'

class DailyPlan extends Component {

    render() {
        return <Provider store={store} form={this.props.form}>
            <Fragment>
                <Filter/>
                <Table/>
                <Modal/>
            </Fragment>
        </Provider>
    }
}
export default Form.create()(DailyPlan)