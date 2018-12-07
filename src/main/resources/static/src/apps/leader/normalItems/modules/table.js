import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination } from 'antd'

@inject('tableStore')
@observer
class table extends Component {
    render() {
        const { current, pageSize } = this.props.tableStore
        const columns = [
            {   title: '#', dataIndex: 'index', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '名称', dataIndex: 'name', width: 180},
            {   title: '内容', dataIndex: 'content', width: 180},
            {   title: '作业指导书', dataIndex: 'handBook', width: 150},
            {   title: '标准时间', dataIndex: 'minutes', width: 120},
            {   title: '执行人', dataIndex: 'staffName', width: 150},
            // {   title: '选项', dataIndex: 'operation', width: 80, render: (text, record) => {
            //         return (
            //             dataSource.length >= 1 ? (
            //                 <Popconfirm title="确认删除 ?" onConfirm={() => handleDelete(record.operId)}>
            //                     <a href="javascript:;">删除</a>
            //                 </Popconfirm>
            //             ) : null
            //         );
            //     }}
        ];

        return <Fragment>
            <Table
                // dataSource={}
                columns={columns}
                // Pagination={}
                // rowSelection={}
                // rowKey={}
                // size={"middle"}
            />
            <Pagination
                current={current}
                // onChange={ page => search(this.props.form, page, pageSize)}
                // total={all}
                // showTotal={total => `共有 ${total} 条记录`}
                pageSize={pageSize}
            />
        </Fragment>
    }
}

export default table