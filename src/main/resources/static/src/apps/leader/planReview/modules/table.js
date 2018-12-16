import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Row, Col, Select, Button, Form } from 'antd'
import Search from './search'

const FormItem = Form.Item
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
            {   title: '完成状态', dataIndex: 'finishStatus', width: 120},
            {   title: '类型', dataIndex: 'workFrom', width: 80, render: (text, record, index) => {
                    return text === '常规工作项' ? '常规' : '临时'
                }},
            {   title: '名称', dataIndex: 'workName', width: 180},
            {   title: '内容', dataIndex: 'workContent', width: 200},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120},
            {   title: '开始时间', dataIndex: 'beginTime', width: 200},
            {   title: '完成时间', dataIndex: 'finishTime', width: 200, render: (text) => {
                    return text === '' || text === null || text === undefined ? '无' : text
                }},
            {   title: '执行人', dataIndex: 'empName', width: 150},
            {   title: '评价', dataIndex: 'assessGrade', width: 80},
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
                <Col span={5}>
                    <FormItem>
                        <Button key="submit" type={"primary"} disabled={store.selectedRowKeys.length > 0 ? false : true} onClick={actions.showModal}>考核</Button>
                    </FormItem>
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