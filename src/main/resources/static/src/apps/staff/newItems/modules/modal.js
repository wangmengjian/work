import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Button, Input, Form, Radio, Upload, Icon } from 'antd'

const FormItem = Form.Item
const { TextArea } = Input

@inject('store', 'form')
@observer
class modal extends Component {

    pushToTable = () => {
        const { form, store } = this.props
        form.validateFields(['workFrom','workName','workContent','workMinutes','file'], (err, values) => {
                if (!err) {
                    store.actions.handleAdd(values)
                    form.resetFields()
                }
            },
        );
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const { visible, actions, fileData } = this.props.store
        const props = {
            name: 'file',
            action: '/api/work/schedule/employee/addWork',
            beforeUpload: actions.beforeUploadHandle,
            fileList: fileData,
            onChange: actions.fileChange,
            onRemove: actions.fileRemove
        }

        return <Modal
            title="新建工作项"
            visible={visible}
            onOk={this.pushToTable}
            onCancel={actions.hideModal}
            style={{height: 300}}
        >
            <Form>
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
                <FormItem label={"工作项名称"} labelCol={{span: 6}}>
                    {getFieldDecorator('workName', {
                        rules: [{ required: true, message: '请输入名称' }],
                    })(
                        <Input style={{width: 203}}/>
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
                        validateFirst: true,    // 规则校验不通过后停止校验后来的规则
                        rules: [{
                            required: true,
                            message: '请输入时间'
                        }, {
                            type: 'number',
                            message: '只能输入数字',
                            transform(value) {
                                return Number(value)?Number(value):'';
                            }
                        }],
                    })(
                        <Input style={{width: 160}}/>
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
        </Modal>
    }
}

export default modal