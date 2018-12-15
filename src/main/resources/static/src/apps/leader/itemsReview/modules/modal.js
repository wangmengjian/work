import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Form, Select, Input, Button } from 'antd'
import axios from "axios/index";
import {message} from "antd/lib/index";

const Option = Select.Option
const FormItem = Form.Item

@inject('store')
@observer
class modal extends Component {

    componentDidMount() {
        const store = this.props.store

        axios({
            method: 'get',
            url: '/api/work/sysConfig/failReason',
        })
            .then(response => {
                if (response.data.status.code === 1){
                    store.reasonChoices = response.data.result.data
                    store.reasonChoices.push({name: '其他'})
                } else {
                    message.error("获取下拉框数据失败: " + response.data.status.message)
                }
            })
    }

    pushToTable = () => {
        const { form, store } = this.props

        form.validateFields((err, values) => {
                if (!err) {
                    store.reason = values.reason
                    store.actions.submit(form)
                }
            },
        );
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        const { actions } = store

        return <Modal
            title="填写不通过原因"
            visible={store.visible}
            onCancel={actions.hideModal}
            width={400}
            footer={[
                <Button key="cancel" onClick={actions.hideModal}>取消</Button>,
                <Button key="submit" type={"primary"} onClick={this.pushToTable} loading={store.loadingDisButton}>提交</Button>,
            ]}
        >
            <Form>
                <FormItem label={"原因"} labelCol={{span: 6}}>
                    {getFieldDecorator('reason', {
                        rules: [{ required: true, message: '请选择原因' }]
                    })(
                        <Select
                            showSearch
                            filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                            style={{ width: 220 }}
                            onChange={value => store.reason = value}
                        >
                            {
                                store.reasonChoices.map(item => {
                                    return <Option key={item.name}>{item.name}</Option>
                                })
                            }
                        </Select>
                    )}
                </FormItem>
                {
                    store.reason === '其他' ? (
                        <FormItem label={"其他原因"} labelCol={{span: 6}}>
                            {getFieldDecorator('otherReason', {
                                rules: [{ required: true, message: '请填写原因' }]
                            })(
                                <Input style={{ width: 220 }} onChange={value => store.otherReason = value}/>
                            )}
                        </FormItem>
                    ) : null
                }
            </Form>
        </Modal>
    }
}

export default Form.create()(modal)