import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Row, Col, Button } from 'antd'
import Search from './search'

@inject('store')
@observer
class table extends Component {

    componentDidMount() {
        this.props.store.actions.search()
    }

    render() {
        const store = this.props.store
        const { actions } = store
        const columns = [
            {   title: '#', dataIndex: 'index', width: 100, render: (text, record, index) => {
                    return (store.current-1)*(store.pageSize)+index+1
                }},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120},
            {   title: '选项', dataIndex: 'operation', width: 150, render: (text, record) => {
                    if (record.workInstructor === null || record.workInstructor === '') {
                        return '暂无指导书'
                    } else {
                        return <a href={record.workInstructor}>查看指导书</a>
                    }
                }},
        ];

        const rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                this.props.store.selectedRowKeys = selectedRowKeys
            },
        }

        return <Fragment>
            <Row>
                <Col span={16}>
                    <Button type={'primary'} onClick={() => actions.submit()} loading={store.loadingButton}>添加</Button>
                </Col>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                rowSelection={rowSelection}
                pagination={{
                    defaultPageSize:8,
                    total: store.all,
                    current:store.current,
                    onChange: actions.search
                }}
                loading={store.loading}
                rowKey={'id'}
                // size={"middle"}
                // scroll={{ y : 460 }}
                // bordered
            />
        </Fragment>
    }
}

export default table