import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination, Popconfirm, Row, Col, Select, Button } from 'antd'
import Search from './search'

const Option = Select.Option

@inject('store')
@observer
class table extends Component {

    componentDidMount() {
        this.props.store.actions.search(1, 10)
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
                <Col span={8}>
                    <Button type={'primary'} disabled={store.selectedRowKeys.length > 0 ? false : true}>删除</Button>
                </Col>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                rowSelection={rowSelection}
                pagination={false}
                rowKey={'id'}
                loading={store.loading}
                size={"middle"}
                scroll={{ y : 460 }}
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