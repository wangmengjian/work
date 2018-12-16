import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Row, Col, Select, Button } from 'antd'
import Search from './search'

const Option = Select.Option

@inject('store')
@observer
class table extends Component {

    componentDidMount() {
        this.props.store.actions.search(1, this.props.store.pageSize)
    }

    render() {
        const store = this.props.store
        const { actions, selectedRowKeys } = store
        const columns = [
            {   title: '#', dataIndex: 'index', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '名称', dataIndex: 'workName', width: 180},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120},
            {   title: '执行人', dataIndex: 'employeeName', width: 150},
            {   title: '选项', dataIndex: 'operation', width: 200, render: (text, record, index) => {
                    return (
                        record.workInstructor === null || record.workInstructor === '' ?
                            '暂无指导书' : <a href={record.workInstructor}>查看指导书</a>
                    );
                }}
        ];

        const rowSelection = {
            selectedRowKeys,
            onChange: (selectedRowKeys, selectedRows) => {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                this.props.store.selectedRowKeys = selectedRowKeys
            },
        }

        return <Fragment>
            <Row>
                <Col span={2}>
                    <Button type={'primary'} disabled={store.selectedRowKeys.length > 0 ? false : true}>删除</Button>
                </Col>
                <Col span={6}>
                    <Button type={'primary'} disabled={store.selectedRowKeys.length > 0 ? false : true}>安排工作</Button>
                </Col>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                rowSelection={rowSelection}
                pagination={{
                    pageSize: store.pageSize,
                    total: store.all,
                    current: store.current,
                    onChange: actions.search
                }}
                loading={store.loading}
                rowKey={'id'}
                // size={"middle"}
                // scroll={{ y : 460 }}
                // bordered
            /><br/>
        </Fragment>
    }
}

export default table