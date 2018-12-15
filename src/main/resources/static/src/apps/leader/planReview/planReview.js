import React, {Component, Fragment} from 'react';
import {Provider} from 'mobx-react'
import store from './stores/store'
import Table from './modules/table'
import Modal from './modules/modal'

class planReview extends Component {

    // 切换 div
    switchTab(currentIndex) {
        store.currentIndex = currentIndex
        store.actions.search(1, store.pageSize)
    }

    render() {
        const currentIndex = store.currentIndex
        return <Provider store={store}>
            <Fragment>
                <div className="head">
                    <ul className="tab">
                        <div style={{ float: 'right', marginRight: 50 }}>
                            <li className={currentIndex === 1 ? 'on':''} onClick={() => this.switchTab(1)}>全部的</li>
                            <li className={currentIndex === 2 ? 'on':''} onClick={() => this.switchTab(2)}>未完成</li>
                            <li className={currentIndex === 3 ? 'on':''} onClick={() => this.switchTab(3)}>已完成</li>
                            <li className={currentIndex === 4 ? 'on':''} onClick={() => this.switchTab(4)}>常规工作</li>
                            <li className={currentIndex === 5 ? 'on':''} onClick={() => this.switchTab(5)}>临时工作</li>
                        </div>
                    </ul>
                </div>
                <div className="content">
                    <Table/>
                    <Modal/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default planReview