import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Button, Row, Col, message } from 'antd'
import axios from "axios";

@inject('store', 'form')
@observer
class table extends Component {

    // 校验表单
    submit = () => {
        const { dataSource, actions } = this.props.store
        const form = new FormData()

        for (let i=0; i < dataSource.length; i++) {
            if (this.props.store.formData[i] !== undefined && this.props.store.formData[i] !== null) {
                form.append('workAuditDetails['+i+'].file', this.props.store.formData[i])
            }
            form.append('workAuditDetails['+i+'].workFrom', this.props.store.workFrom[i].substring(0,2))
            form.append('workAuditDetails['+i+'].workName', dataSource[i].workName)
            form.append('workAuditDetails['+i+'].workContent', dataSource[i].workContent)
            form.append('workAuditDetails['+i+'].workMinutes', dataSource[i].workMinutes * 60000)
        }
        axios({
            method: 'post',
            url: '/api/work/schedule/employee/work',
            data: form
        })
            .then(response => {
                if (response.data.status.code === 1){
                    actions.resetTable()
                    message.success("提交成功: " + response.data.status.message)
                } else {
                    message.error("提交失败: " + response.data.status.message)
                }
            })
    }

    // 修改条目
    handleAlter = record => {
        const { form, store } = this.props
        form.setFieldsValue({
            workFrom: record.workFrom === '常规工作项' ? 'w3常规工作项':'w2临时工作项',
            workName: record.workName,
            workContent: record.workContent,
            workMinutes: record.workMinutes
        })
        store.fileData = []
        if (store.formData.length > 0) {
            if (store.formData[record.key-1] !== null) {
                store.fileData.push(store.formData[record.key-1])
            }
        }
        store.isAlter = record.key
        store.actions.showModal()
    }

    // 新增工作项对话框
    newItem = () => {
        this.props.store.fileData = []
        this.props.form.resetFields()
        this.props.store.actions.showModal()
    }

    render() {
        const { actions, dataSource } = this.props.store
        const columns = [
            {   title: '#', dataIndex: 'key', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '类型', dataIndex: 'workFrom', width: 180},
            {   title: '名称', dataIndex: 'workName', width: 180},
            {   title: '内容', dataIndex: 'workContent', width: 150},
            {   title: '作业指导书', dataIndex: 'file', width: 120},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120},
            {   title: '选项', dataIndex: 'operation', width: 80, render: (text, record) => {
                    return (
                        dataSource.length >= 1 ? (
                            <Fragment>
                                <a href="javascript:;" onClick={() => actions.handleDelete(record.key)}>删除</a>&nbsp;&nbsp;
                                <a href="javascript:;" onClick={() => this.handleAlter(record)}>修改</a>
                            </Fragment>
                        ) : null
                    );
                }}
        ];

        return <Fragment>
            <Row>
                <Col className="gutter-row" span={2}>
                    <Button type={"primary"} onClick={this.newItem}>新增</Button>
                </Col>
            </Row>
            <Table
                dataSource={dataSource}
                columns={columns}
                pagination={false}
                scroll={{ y: 460 }}
                // rowSelection={}
                // rowKey={"key"}
                // size={"middle"}
            />
            <Button type={"primary"} onClick={this.submit}>提交</Button>
            <Button style={{float: 'right'}} onClick={actions.resetTable}>清空</Button>
        </Fragment>
    }
}

export default table