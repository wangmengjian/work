import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Button, Row, Col, message, Form, InputNumber, Input, Upload, Radio, Icon } from 'antd'
import { withRouter } from 'react-router-dom'
import axios from "axios";

const FormItem = Form.Item
const { TextArea } = Input

@inject('store', 'form')
@observer
class table extends Component {

    // 校验表单
    submit = () => {
        const store = this.props.store
        const { actions } = store
        const form = new FormData()
        store.loading = true

        // if (store.formData[0] !== undefined && store.formData[0] !== null) {
        //     form.append('workAuditDetails[0].file', store.formData[i])
        // }
        // form.append('workAudits[0].workFrom', store.workFrom[i].substring(0,2))
        // form.append('workAudits[0].workName', dataSource[i].workName)
        // form.append('workAudits[0].workContent', dataSource[i].workContent)
        // form.append('workAudits[0].workMinutes', dataSource[i].workMinutes)

        axios({
            method: 'post',
            url: '/api/work/schedule/employee/addWork',
            data: form
        })
            .then(response => {
                if (response.data.status.code === 1){
                    actions.resetTable()
                    message.success("提交成功: " + response.data.status.message)
                } else {
                    message.error("提交失败: " + response.data.status.message)
                }
                store.loading = false
            })
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        const { actions } = store
        const props = {
            name: 'file',
            action: '/api/work/schedule/employee/addWork',
            beforeUpload: actions.beforeUploadHandle,
            fileList: store.fileData,
            onChange: actions.fileChange,
            onRemove: actions.fileRemove
        }
        const form = (
            <Form>
                <FormItem label={"工作项名称"} labelCol={{span: 6}}>
                    {getFieldDecorator('workName', {
                        rules: [{ required: true, message: '请输入名称' }],
                    })(
                        <Input style={{width: 203}}/>
                    )}
                </FormItem>
                <FormItem label={"工作项类型"} labelCol={{span: 6}}>
                    {getFieldDecorator('workFrom', {
                        rules: [{ required: true, message: '请输入名称' }],
                        initialValue: 'w3常规工作项'
                    })(
                        <Radio.Group buttonStyle="solid">
                            <Radio.Button value="w3常规工作项">常规工作项</Radio.Button>
                            <Radio.Button value="w2临时工作项">临时工作项</Radio.Button>
                        </Radio.Group>
                    )}
                </FormItem>
                <FormItem label={"工作项内容"} labelCol={{span: 6}}>
                    {getFieldDecorator('workContent', {
                        rules: [{ required: true, message: '请输入内容' }],
                    })(
                        <TextArea autosize={{ minRows: 2, maxRows: 6 }} style={{ width: 280 }}/>
                    )}
                </FormItem>
                <FormItem label={"标准工作时间"} labelCol={{span: 6}}>
                    {getFieldDecorator('workMinutes', {
                        rules: [{ required: true, message: '请选择时间' }],
                    })(
                        <InputNumber min={1} max={10} defaultValue={3}/>
                    )}
                    &nbsp;&nbsp;分钟
                </FormItem>
                <FormItem labelCol={{span: 6}}>
                    {getFieldDecorator('file')(
                        <Upload {...props}>
                            {
                                this.props.store.fileData.length === 1 ? null : <Button><Icon type="upload" /> 上传指导书</Button>
                            }
                        </Upload>
                    )}
                </FormItem>
            </Form>
        )

        return <Fragment>
            {form}
            <Row>
                <Col className="gutter-row" span={2}>
                    <Button
                        type={"primary"}
                        onClick={this.submit}
                        loading={store.loading}
                    >
                        确定
                    </Button>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button
                        onClick={() => this.props.history.push('/work/employee/normalItems')}
                    >
                        返回
                    </Button>
                </Col>
            </Row>
        </Fragment>
    }
}

export default withRouter(table)