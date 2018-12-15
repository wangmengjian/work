import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Button, Input, Form, Upload, Icon, InputNumber, message, Select } from 'antd'
import axios from "axios/index";

const Option = Select.Option
const FormItem = Form.Item
const { TextArea } = Input

@inject('store', 'form')
@observer
class modal extends Component {

    componentDidMount() {
        axios({
            method: 'get',
            url: '/api/work/user/deptEmployees',
        })
            .then(response => {
                if (response.data.status.code === 1){
                    this.props.store.employeeList = response.data.result.data
                } else {
                    message.error("获取下拉框数据失败: " + response.data.status.message)
                }
            })
    }

    pushToTable = () => {
        const { form, store } = this.props
        form.validateFields(['workName','workContent','workMinutes','file'], (err, values) => {
                if (!err) {
                    store.actions.alter(values, form)
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
            title="修改工作项"
            visible={visible}
            onCancel={actions.hideModal}
            style={{height: 300}}
            footer={[
                <Button key="cancel" onClick={actions.hideModal}>取消</Button>,
                <Button
                    key="submit"
                    type={'primary'}
                    onClick={this.pushToTable}
                    loading={store.loadingButton}
                >
                    修改
                </Button>
            ]}
        >
            <Form>
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
                        rules: [{ required: true, message: '请输入时间' }]
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

export default modal