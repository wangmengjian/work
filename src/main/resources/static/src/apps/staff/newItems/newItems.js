import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'
import Modal from './modules/modal'
import { Form } from 'antd'

class NewItems extends Component {

    render() {
        return <Provider store={store} form={this.props.form}>
            <Fragment>
                <Modal/>
                <Table/>
            </Fragment>
        </Provider>
    }
}
export default Form.create()(NewItems)