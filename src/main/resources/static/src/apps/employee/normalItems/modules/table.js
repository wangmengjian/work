import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Select, Row, Col, Button, Divider } from 'antd'
import Search from './search'

const Option = Select.Option

@inject('store', 'form')
@observer
class table extends Component {

    componentDidMount() {
        this.props.store.actions.search(1)
    }

    handleAlter = (record) => {
        const store = this.props.store
        const form = this.props.form
        store.originWorkId = record.id

        form.setFieldsValue({
            workContent: record.workContent,
            workFrom: record.workFrom === "常规工作项" ? "w3常规工作项" : "w2临时工作项",
            workMinutes: record.workMinutes,
            workName: record.workName
        })
        store.actions.showModal(1)
    }

    render() {
        const store = this.props.store
        const { actions } = store
        const columns = [
            {   title: '状态', dataIndex: 'auditStatus', width: 80},
            {   title: '类型', dataIndex: 'workFrom', width: 120},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
            {   title: '审核时间', dataIndex: 'time', width: 180, render: (text) => {
                    if (text === null || text === '') { return '无' } else { return text }
                }},
            {   title: '未通过原因', dataIndex: 'auditFailReason', width: 180, render: (text) => {
                    if (text === null || text === '') { return '无' } else { return text }
                }},
            {   title: '选项', dataIndex: 'operation', width: 250, render: (text, record, index) => {
                    return (
                        <Fragment>
                            {
                                record.workInstructor === null || record.workInstructor === '' ?
                                    '暂无指导书' : <a href={record.workInstructor}>查看指导书</a>
                            }
                            <Divider type="vertical" />
                            <a href={"javascript:;"} onClick={() => this.handleAlter(record)}>修改</a>
                            {
                                record.auditStatus === '待审核' ? (
                                    <Fragment>
                                        <Divider type="vertical" />
                                        <a href={"javascript:;"} onClick={() => actions.rollback(record.id)}>撤回</a>
                                    </Fragment>
                                ) : null
                            }
                            <Divider type="vertical" />
                            <a href={"javascript:;"} onClick={() => actions.delete(record.id)}>删除</a>
                        </Fragment>
                    )
            }}
        ];

        return <Fragment>
            <Row>
                <Col span={17}>
                    <Button
                        type="primary"
                        onClick={() => actions.showModal(0)}
                    >
                        新增工作项
                    </Button>
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
                loading={store.loading}
                rowKey={'id'}
                // size="middle"
                // bordered
            />
        </Fragment>
    }
}

export default table