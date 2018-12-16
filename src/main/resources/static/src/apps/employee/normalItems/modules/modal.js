import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Button, InputNumber, Input , Form, Radio, Upload, Icon } from 'antd'

const FormItem = Form.Item
const { TextArea } = Input

@inject('store')
@observer
class modal extends Component {

    beforeSubmit = () => {
        const { form, store } = this.props
        form.validateFields(['workFrom','workName','workContent','workMinutes','file'], (err, values) => {
                if (!err) {
                    store.actions.submit(values, form)
                }
            },
        );
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        const { visible, actions, fileData } = store
        const props = {
            name: 'file',
            action: '/api/work/schedule/employee/addWork',
            beforeUpload: actions.beforeUploadHandle,
            fileList: fileData,
            onRemove: actions.fileRemove
        }

        return <Modal
            title="新建工作项"
            visible={visible}
            onCancel={actions.hideModal}
            style={{height: 300}}
            footer={[
                <Button key="cancel" onClick={actions.hideModal}>取消</Button>,
                <Button
                    key="submit"
                    type={'primary'}
                    onClick={this.beforeSubmit}
                    loading={store.loadingNewItem}
                >
                    添加
                </Button>
            ]}
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
                        rules: [{ required: true, message: '请选择时间' }]
                    })(
                        <InputNumber min={1} max={300}/>
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

export default Form.create()(modal)