import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { withRouter } from 'react-router-dom'
import { Table, Pagination, Select, Row, Col, Button } from 'antd'
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
            {   title: '#', dataIndex: 'index', width: 70, render: (text, record, index) => {
                    return (store.current-1)*(store.pageSize)+index+1
                }},
            {   title: '员工姓名', dataIndex: 'employeeName', width: 80},
            {   title: '类型', dataIndex: 'workFrom', width: 120},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
            {   title: '选项', dataIndex: 'operation', width: 150, render: (text, record) => {
                    return (
                        record.workInstructor === null || record.workInstructor === '' ?
                        '暂无指导书' : <a href={record.workInstructor}>查看指导书</a>
                    )
            }}
        ];

        return <Fragment>
            <Row>
                <Col span={8}>
                    <Button
                        type="primary"
                        // onClick={actions.showModal}
                    >
                        新增工作项
                    </Button>
                </Col>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                pagination={false}
                rowKey={'id'}
                loading={store.loading}
                size="middle"
                scroll={{ y : 470 }}
                bordered
            /><br/>
            <Row type="flex" justify="end">
                <Col>
                    <Pagination
                        current={store.current}
                        onChange={ page => actions.search(page, store.pageSize)}
                        total={store.all}
                        showTotal={total => `共有 ${total} 条记录`}
                        pageSize={store.pageSize}
                    />
                </Col>
            </Row>
        </Fragment>
    }
}

export default withRouter(table)