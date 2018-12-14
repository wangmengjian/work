import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/store'
import Table from './modules/table'
import Modal from './modules/modal'
import { Form } from 'antd'

class NewItems extends Component {

    render() {
        return <Provider store={store} form={this.props.form}>
            <Fragment>
                <div className="content">
                    <Modal/>
                    <Table/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default Form.create()(NewItems)