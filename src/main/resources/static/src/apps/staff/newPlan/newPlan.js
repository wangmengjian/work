import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/store'
import Table from './modules/table'
import Search from './modules/search'

class NewPlan extends Component {

    render() {
        return <Provider store={store}>
            <Fragment>
                <Search/>
                <Table/>
            </Fragment>
        </Provider>
    }
}
export default NewPlan