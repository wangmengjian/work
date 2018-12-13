import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/store'
import Table from './modules/table'
import Modal from './modules/modal'
import Search from './modules/search'

class ItemsReview extends Component {

    render() {
        return <Provider store={store}>
            <Fragment>
                {/*<Search/>*/}
                <Table/>
                <Modal/>
            </Fragment>
        </Provider>
    }
}
export default ItemsReview