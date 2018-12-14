import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/store'
import Table from './modules/table'

class DailyUnfinished extends Component {

    render() {
        return <Provider store={store}>
            <Fragment>
                <div className="content">
                    <Table/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default DailyUnfinished