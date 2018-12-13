import React, {Component, Fragment} from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Button, Input, Form, Radio, Upload, Icon, TimePicker } from 'antd'

const FormItem = Form.Item
const { TextArea } = Input

@inject('store', 'form')
@observer
class modal extends Component {

    pushToArray = () => {
        const { form, store } = this.props
        form.validateFields(['finishStatus','finishCondition','finishFeedback','finishTime'], (err, values) => {
                if (!err) {
                    store.actions.workEnd(values)
                    form.resetFields()
                }
            },
        );
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        const { visible, actions, fileData } = store
        const format = "HH:mm"
        const props = {
            action: '/api/work/schedule/employee/submitSchedule',
            beforeUpload: actions.beforeUploadHandle,
            fileList: fileData,
            onChange: actions.fileChange,
            onRemove: actions.fileRemove,
            listType: 'picture',
            className: 'upload-list-inline',
        }

        return <Modal
            title="填写完成信息"
            visible={visible}
            onOk={this.pushToArray}
            onCancel={actions.hideModal}
            width={400}
        >
            <Form hideRequiredMark={true}>
                <FormItem label={"完成状态"} labelCol={{span: 6}}>
                    {getFieldDecorator('finishStatus')(
                        <Radio.Group
                            buttonStyle="solid"
                            onChange={e => store.finish = e.target.value}
                        >
                            <Radio.Button value="未完成">未完成</Radio.Button>
                            <Radio.Button value="已完成">已完成</Radio.Button>
                        </Radio.Group>
                    )}
                </FormItem>
                {
                    store.finish === '未完成' ? null : (
                        <div id="modal_form">
                            <FormItem label={"完成情况"} labelCol={{span: 6}}>
                                {getFieldDecorator('finishCondition')(
                                    <Input style={{width: 220}}/>
                                )}
                            </FormItem>
                            <FormItem label={"心得"} labelCol={{span: 6}}>
                                {getFieldDecorator('finishFeedback')(
                                    <TextArea style={{width: 220, height: 100}}/>
                                )}
                            </FormItem>
                            <FormItem label={"完成时间"} labelCol={{span: 6}}>
                                {getFieldDecorator('finishTime', {
                                    rules: [{ required: true, message: '请选择时间' }]
                                })(
                                    <TimePicker format={format}/>
                                )}
                            </FormItem>
                            <FormItem labelCol={{span: 6}}>
                                {getFieldDecorator('file')(
                                    <Upload {...props}>
                                        {
                                            <Button><Icon type="upload" /> 上传工作照片</Button>
                                        }
                                    </Upload>
                                )}
                            </FormItem>
                        </div>
                    )
                }

            </Form>
        </Modal>
    }
}

export default modal