import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import tableStore from './stores/tableStore'
import searchStore from './stores/searchStore'
import Table from './modules/table'
import Search from './modules/search'

class NormalItems extends Component {

    render() {
        return <Provider tableStore={tableStore} searchStore={searchStore}>
            <Fragment>
                <Search/>
                <Table/>
            </Fragment>
        </Provider>
    }
}
export default NormalItems