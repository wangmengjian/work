import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { withRouter } from 'react-router-dom'
import { Table, Select, Row, Col } from 'antd'
import Search from './search'

const Option = Select.Option

@inject('store')
@observer
class table extends Component {

    componentDidMount() {
        this.props.store.actions.search(1)
    }

    render() {
        const store = this.props.store
        const { actions } = store
        const columns = [
            {   title: '完成状态', dataIndex: 'finishStatus', width: 110},
            {   title: '类型', dataIndex: 'workFrom', width: 80, render: (text, record, index) => {
                    return text === '常规工作项' ? '常规' : '临时'
                }},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 200},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
            {   title: '开始时间', dataIndex: 'beginTime', width: 200},
            {   title: '完成时间', dataIndex: 'finishTime', width: 200, render: (text) => {
                    return text === '' || text === null || text === undefined ? '无' : text
                }},
            {   title: '等级', dataIndex: 'assessGrade', width: 80, render: (text) => {
                    return text === '' || text === null || text === undefined ? '无' : text
                }},
            {   title: '评价', dataIndex: 'assessDesc', width: 200, render: (text) => {
                    return text === '' || text === null || text === undefined ? '无' : text
                }},
            {   title: '考核状态', dataIndex: 'assessStatus', width: 120, render: (text) => {
                    return text === 1 ? '已考核' : '未考核'
                }},
        ];

        return <Fragment>
            <Row>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                pagination={{
                    pageSize: store.pageSize,
                    total: store.all,
                    current: store.current,
                    onChange: actions.search
                }}
                loading={store.loading}
                rowKey={'id'}
                // size="middle"
                // bordered
            />
        </Fragment>
    }
}

export default withRouter(table)