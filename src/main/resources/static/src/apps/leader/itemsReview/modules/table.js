import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination, Button, Row, Col, Select, Form } from 'antd'
import Search from './search'

const Option = Select.Option
const FormItem = Form.Item

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
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120, render: (text) => {
                    return text + '分钟'
                }},
            {   title: '提交时间', dataIndex: 'auditSubmitTime', width: 180},
            {   title: '类型', dataIndex: 'workFrom', width: 150},
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
                    <FormItem>
                        <Button
                            type={'primary'}
                            onClick={actions.submit}
                            disabled={store.selectedRowKeys.length > 0 ? false : true}
                            loading={store.loadingArgButton}
                        >
                            通过
                        </Button>
                    </FormItem>
                </Col>
                <Col span={6}>
                    <FormItem>
                        <Button
                            type={'primary'}
                            onClick={actions.showModal}
                            disabled={store.selectedRowKeys.length > 0 ? false : true}
                        >
                            不通过
                        </Button>
                    </FormItem>
                </Col>
                <Search/>
            </Row>
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
            </Row>
        </Fragment>
    }
}

export default table