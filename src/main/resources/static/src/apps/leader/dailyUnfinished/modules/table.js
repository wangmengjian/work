import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination, Popconfirm, Row, Col } from 'antd'
import Search from './search'

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
            <Row>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                Pagination={false}
                // rowSelection={}
                // rowKey={}
                size={"middle"}
            />
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
                {/*<Col className="gutter-row" span={3}>*/}
                {/*<span>每页条数:&nbsp;&nbsp;&nbsp;</span>*/}
                {/*<Select*/}
                {/*defaultValue={store.pageSize}*/}
                {/*onChange={value => actions.handlePageSizeChange(value)}*/}
                {/*>*/}
                {/*<Option key={10}>10</Option>*/}
                {/*<Option key={20}>20</Option>*/}
                {/*<Option key={30}>30</Option>*/}
                {/*</Select>*/}
                {/*</Col>*/}
            </Row>
        </Fragment>
    }
}

export default table