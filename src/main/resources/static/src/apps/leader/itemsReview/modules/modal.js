import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Input, Form, Select } from 'antd'

const Option = Select.Option
const FormItem = Form.Item

@inject('store')
@observer
class modal extends Component {

    pushToTable = () => {
        const { form, store } = this.props
        form.validateFields(['workFrom','workName','workContent','workMinutes'], (err, values) => {
                if (!err) {
                    store.actions.handleAdd(values)
                    form.resetFields()
                }
            },
        );
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const { visible, actions } = this.props.store

        return <Modal
            title="填写不通过原因"
            visible={visible}
            onOk={this.pushToTable}
            onCancel={actions.hideModal}
            style={{width: 360, height: 300}}
        >
            <Form hideRequiredMark={true}>
                <FormItem label={"原因"} labelCol={{span: 6}}>
                    {getFieldDecorator('reason')(
                        <Select
                            showSearch
                            filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                            style={{ width: 180 }}
                        >
                            <Option key={'张三'}>张三</Option>
                            <Option key={'李四'}>李四</Option>
                        </Select>
                    )}
                </FormItem>
                <FormItem label={"备注"} labelCol={{span: 6}}>
                    {getFieldDecorator('other')(
                        <Input style={{ width: 180 }}/>
                    )}
                </FormItem>
            </Form>
        </Modal>
    }
}

export default Form.create()(modal)