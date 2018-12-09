import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination, Popconfirm } from 'antd'

@inject('store')
@observer
class table extends Component {
    render() {
        const store = this.props.store
        const columns = [
            {   title: '#', dataIndex: 'index', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '名称', dataIndex: 'workName', width: 180},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '作业指导书', dataIndex: 'workInstructor', width: 150},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120},
            {   title: '执行人', dataIndex: 'staffName', width: 150},
            {   title: '选项', dataIndex: 'operation', width: 80, render: (text, record) => {
                    return (
                        // store.dataSource.length >= 1 ? (
                        //     {/*<Popconfirm title="确认删除 ?" onConfirm={() => handleDelete(record.operId)}>*/}
                        //         <a href="javascript:;">删除</a>
                        //     // </Popconfirm>
                        // ) : null
                        <a href="javascript:;">删除</a>
                    );
                }}
        ];

        return <Fragment>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                Pagination={false}
                // rowSelection={}
                // rowKey={}
                // size={"middle"}
            />
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