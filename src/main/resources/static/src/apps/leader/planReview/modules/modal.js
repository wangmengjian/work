import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Form, Input, Button, Radio } from 'antd'

const FormItem = Form.Item
const { TextArea } = Input

@inject('store')
@observer
class modal extends Component {

    submit = () => {
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.props.store.actions.submit(this.props.form, values)
            }
        })
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        const { actions } = store

        return <Modal
            title="考核工作项"
            visible={store.visible}
            onCancel={actions.hideModal}
            width={400}
            style={{ top: 150 }}
            footer={[
                <Button key="cancel" onClick={actions.hideModal}>取消</Button>,
                <Button key="submit" type={"primary"} onClick={this.submit} loading={store.loadingButton}>提交</Button>,
            ]}
        >
            <Form>
                <FormItem label={"等级"} labelCol={{span: 6}}>
                    {getFieldDecorator('assessGrade', {
                        initialValue: '优'
                    })(
                        <Radio.Group
                            buttonStyle="solid"
                            onChange={e => store.accessGrade = e.target.value}
                        >
                            <Radio.Button value="优">优</Radio.Button>
                            <Radio.Button value="良">良</Radio.Button>
                            <Radio.Button value="中">中</Radio.Button>
                            <Radio.Button value="差">差</Radio.Button>
                        </Radio.Group>
                    )}
                </FormItem>
                <FormItem label={"评语"} labelCol={{span: 6}}>
                    {getFieldDecorator('assessDesc')(
                        <TextArea autosize={{ minRows: 3, maxRows: 6 }} style={{ width: 220 }}/>
                    )}
                </FormItem>
            </Form>
        </Modal>
    }
}

export default Form.create()(modal)