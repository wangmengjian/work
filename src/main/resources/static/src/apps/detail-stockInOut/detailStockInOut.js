import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/detailStockInOutStore'
import DetailStockSearch from './modules/detailStockSearch'
import DetailStockTable from './modules/detailStockTable'

class DetailStockInOut extends Component {

    render() {
        return <Provider store={store}>
            <Fragment>
                <DetailStockSearch/>
                <DetailStockTable/>
            </Fragment>
        </Provider>
    }
}
export default DetailStockInOut