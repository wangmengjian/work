import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/store'
import Table from './modules/table'
import Modal from './modules/modal'

class ItemsReview extends Component {

    render() {
        return <Provider store={store}>
            <Fragment>
                <div className="content">
                    <Table/>
                    <Modal/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default ItemsReview