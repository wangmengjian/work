import React, {Component, Fragment} from 'react';
import {Provider, observer} from 'mobx-react'
import store from './stores/tableStore'
import Table from './modules/table'

@observer
class MyAssess extends Component {

    // 切换 div
    switchTab(currentIndex) {
        store.currentIndex = currentIndex
        store.actions.search(1)
    }

    render() {
        const currentIndex = store.currentIndex

        return <Provider store={store}>
            <Fragment>
                <div className="head">
                    <ul className="tab">
                        <div style={{ float: 'right', marginRight: 50 }}>
                            <li className={currentIndex === 1 ? 'on':''} onClick={() => this.switchTab(1)}>全部的</li>
                            <li className={currentIndex === 2 ? 'on':''} onClick={() => this.switchTab(2)}>优</li>
                            <li className={currentIndex === 3 ? 'on':''} onClick={() => this.switchTab(3)}>中</li>
                            <li className={currentIndex === 4 ? 'on':''} onClick={() => this.switchTab(4)}>良</li>
                            <li className={currentIndex === 5 ? 'on':''} onClick={() => this.switchTab(5)}>差</li>
                            <li className={currentIndex === 6 ? 'on':''} onClick={() => this.switchTab(6)}>已完成</li>
                            <li className={currentIndex === 7 ? 'on':''} onClick={() => this.switchTab(7)}>未完成</li>
                            <li className={currentIndex === 8 ? 'on':''} onClick={() => this.switchTab(8)}>常规工作</li>
                            <li className={currentIndex === 9 ? 'on':''} onClick={() => this.switchTab(9)}>临时工作</li>
                        </div>
                    </ul>
                </div>

                <div className="content">
                    <Table currentIndex={currentIndex}/>
                </div>
            </Fragment>
        </Provider>
    }
}
export default MyAssess