import React, {Component, Fragment} from 'react';
import {Provider, observer} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'
import Modal from './modules/modal'
import { Form } from 'antd'

@observer
class NormalItems extends Component {

    // 切换 div
    switchTab(currentIndex) {
        store.currentIndex = currentIndex
        store.actions.search(1)
    }

    render() {
        const currentIndex = store.currentIndex

        return <Provider store={store} form={this.props.form}>
            <Fragment>
                <div className="content">
                    <Table currentIndex={currentIndex}/>
                    <Modal />
                </div>
            </Fragment>
        </Provider>
    }
}
export default Form.create()(NormalItems)