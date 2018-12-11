import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination, Button, Row, Col, Select } from 'antd'

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
            {   title: '名称', dataIndex: 'workName', width: 120},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '提交人', dataIndex: 'submitter', width: 100},
            {   title: '作业指导书', dataIndex: 'workInstructor', width: 200, render: (text) => {
                    if (text === null || text === '') { return '无' } else { return <a href={text}>{text}</a> }
                }},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120, render: (text) => {
                    return text + '分钟'
                }},
            {   title: '提交时间', dataIndex: 'auditSubmitTime', width: 180},
            {   title: '类型', dataIndex: 'workFrom', width: 150},
        ];

        const rowSelection = {
            selectedRowKeys,
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
                pagination={false}
                loading={store.loading}
                rowKey={'id'}
                size={"middle"}
                scroll={{ y : 460 }}
            /><br/>
            <Row>
                <Col span={2}>
                    <Button
                        type={'primary'}
                        onClick={actions.agree}
                        disabled={store.selectedRowKeys.length > 0 ? false : true}
                        loading={store.loadingArgButton}
                    >
                        通过
                    </Button>
                </Col>
                <Col span={11}>
                    <Button
                        type={'primary'}
                        onClick={actions.showModal}
                        disabled={store.selectedRowKeys.length > 0 ? false : true}
                    >
                        不通过
                    </Button>
                </Col>
                <Col span={8}>
                    <Pagination
                        current={store.current}
                        onChange={ page => actions.search(page, store.pageSize)}
                        total={store.all}
                        showTotal={total => `共有 ${total} 条记录`}
                        pageSize={store.pageSize}
                    />
                </Col>
                <Col span={3}>
                    <span>每页条数:&nbsp;&nbsp;&nbsp;</span>
                    <Select
                        defaultValue={store.pageSize}
                        onChange={value => actions.handlePageSizeChange(value)}
                    >
                        <Option key={10}>10</Option>
                        <Option key={20}>20</Option>
                        <Option key={30}>30</Option>
                    </Select>
                </Col>
            </Row>
        </Fragment>
    }
}

export default table