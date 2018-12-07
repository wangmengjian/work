import React, { Component, Fragment } from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Form, Button, Row, Col, Select } from 'antd'

const Option = Select.Option
const FormItem = Form.Item

@inject('store', 'form')
@observer
class filter extends Component {

    componentDidMount() {
        const { actions } = this.props.store
        actions.getWorkFromOption()
    }

    handleSearch = () => {
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.props.store.actions.search(values)
            }
        })
    }

    render () {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        return <Form hideRequiredMark={true}>
            <Row>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"工作项名称"} labelCol={{span: 7}}>
                        {getFieldDecorator('workName')(
                            <Input placeholder={"请输入内容"} style={{width: 180}}/>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"工作项类型"} labelCol={{span: 7}}>
                        {getFieldDecorator('workFrom')(
                            <Select
                                allowClear
                                style={{ width: 150 }}
                            >
                                {
                                    store.workFromOption.map(item => <Option key={item.code}>{item.name}</Option>)
                                }
                            </Select>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button type={"primary"} onClick={this.handleSearch}>查询</Button>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button onClick={() => this.props.form.resetFields()}>重置</Button>
                </Col>
            </Row>
        </Form>
    }
}

export default filter