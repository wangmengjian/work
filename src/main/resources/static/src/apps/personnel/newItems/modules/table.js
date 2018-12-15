import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Button, Row, Col, message, Modal } from 'antd'
import axios from "axios";

@inject('store', 'form')
@observer
class table extends Component {

    // 校验表单
    submit = () => {
        const store = this.props.store
        const { dataSource, actions } = store
        const form = new FormData()
        store.loading = true

        for (let i=0; i < dataSource.length; i++) {
            if (store.formData[i] !== undefined && store.formData[i] !== null) {
                form.append('workPoolList['+i+'].file', store.formData[i])
            }
            form.append('workPoolList['+i+'].workFrom', store.workFrom[i])
            form.append('workPoolList['+i+'].workName', dataSource[i].workName)
            form.append('workPoolList['+i+'].workContent', dataSource[i].workContent)
            form.append('workPoolList['+i+'].workMinutes', dataSource[i].workMinutes)
            form.append('workPoolList['+i+'].userId', dataSource[i].userId)
        }

        axios({
            method: 'post',
            url: '/api/work/schedule/personnel/addWork',
            data: form
        })
            .then(response => {
                if (response.data.status.code === 1){
                    actions.resetTable()
                    Modal.success({
                        title: '提交成功',
                        content: response.data.status.message,
                    });
                } else {
                    message.error("提交失败: " + response.data.status.message)
                }
                store.loading = false
            })
    }

    // 修改条目
    handleAlter = record => {
        const { form, store } = this.props
        form.setFieldsValue({
            workFrom: record.workFrom === 'w3' ? '常规工作项' : '临时工作项',
            workName: record.workName,
            workContent: record.workContent,
            workMinutes: record.workMinutes,
            employeeId: record.userId,
            file: record.file
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
        const store = this.props.store
        const { actions, dataSource } = store
        const columns = [
            {   title: '#', dataIndex: 'key', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '员工', dataIndex: 'employee', width: 120},
            {   title: '类型', dataIndex: 'workFrom', width: 150, render: (text, record, index) => {
                    return text === 'w3' ? '常规工作项' : '临时工作项'
                }},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '作业指导书', dataIndex: 'file', width: 200},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
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
                <Col className="gutter-row" span={20}>
                    <Button
                        type={"primary"}
                        onClick={this.submit}
                        loading={store.loading}
                        disabled={dataSource.length > 0 ? false : true}
                    >
                        提交
                    </Button>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button style={{float: 'right'}} onClick={actions.resetTable}>清空</Button>
                </Col>
            </Row><br/>
            <Table
                dataSource={dataSource}
                columns={columns}
                pagination={false}
                scroll={{ y: 460 }}
                // rowSelection={}
                rowKey={"key"}
                size={"middle"}
            /><br/>
        </Fragment>
    }
}

export default table