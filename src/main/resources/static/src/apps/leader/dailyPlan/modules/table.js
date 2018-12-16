import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Button, Row, Col, Select, Form } from 'antd'
import Search from './search'

const FormItem = Form.Item
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
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100},
            {   title: '类型', dataIndex: 'workFrom', width: 120},
            {   title: '状态', dataIndex: 'finishStatus', width: 120},
            {   title: '图片', dataIndex: 'finishPicture', width: 100},
            {   title: '完成情况', dataIndex: 'finishCondition', width: 180},
            {   title: '反馈', dataIndex: 'finishFeedback', width: 180},
            {   title: '完成时间', dataIndex: 'finishTime', width: 180},
            {   title: '选项', dataIndex: 'operation', width: 180, render: (text, record) => {
                    return (
                        // store.dataSource.length >= 1 ? (
                        //     {/*<Popconfirm title="确认删除 ?" onConfirm={() => handleDelete(record.operId)}>*/}
                        //         <a href="javascript:;">删除</a>
                        //     // </Popconfirm>
                        // ) : null
                        <Fragment>
                            {
                                record.workInstructor === null || record.workInstructor === '' ?
                                    '暂无指导书' : <a href={record.workInstructor}>查看指导书</a>
                            }&nbsp;&nbsp;
                            <a href="javascript:;">回退</a>
                        </Fragment>
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
                    <FormItem>
                        <Button type={'primary'} disabled={store.selectedRowKeys.length > 0 ? false : true}>删除</Button>
                    </FormItem>
                </Col>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                pagination={{
                    pageSize: store.pageSize,
                    total: store.all,
                    current: store.current,
                    onChange: actions.search
                }}
                rowSelection={rowSelection}
                loading={store.loading}
                rowKey={'id'}
                // scroll={{ y : 460 }}
                // size={"middle"}
                // bordered
            /><br/>
        </Fragment>
    }
}

export default table