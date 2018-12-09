import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination, Button } from 'antd'

@inject('store')
@observer
class table extends Component {
    render() {
        const store = this.props.store
        const { actions } = store
        const columns = [
            {   title: '#', dataIndex: 'index', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '名称', dataIndex: 'workName', width: 180},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '提交人', dataIndex: 'staffName', width: 180},
            {   title: '作业指导书', dataIndex: 'workInstructor', width: 150},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120},
            {   title: '类型', dataIndex: 'type', width: 150},
        ];

        const rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                this.props.store.selectedRowKeys = selectedRowKeys
            },
        }

        return <Fragment>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                rowSelection={rowSelection}
                Pagination={false}
                // rowKey={}
                // size={"middle"}
            />
            <Button type={'primary'} onClick={actions.showModal}>通过</Button>
            <Button type={'primary'}>不通过</Button>
            <Pagination
                current={store.current}
                // onChange={ page => search(this.props.form, page, pageSize)}
                // total={all}
                // showTotal={total => `共有 ${total} 条记录`}
                pageSize={store.pageSize}
            />
        </Fragment>
    }
}

export default table