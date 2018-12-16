import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Form, Button, Row, Col, DatePicker } from 'antd'

const FormItem = Form.Item

@inject('store', 'form')
@observer
class filter extends Component {

    handleSearch = () => {
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.props.store.actions.search(values)
            }
        })
    }

    render () {
        const { getFieldDecorator } = this.props.form
        return <Form>
            <Row>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"工作项名称"} labelCol={{span: 7}}>
                        {getFieldDecorator('workName')(
                            <Input placeholder={"请输入内容"} style={{width: 180}}/>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"选择日期"} labelCol={{span: 7}}>
                        {getFieldDecorator('date')(
                            <DatePicker  style={{ width: 150 }}/>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button type={"primary"} onClick={this.handleSearch}>查询</Button>
                </Col>
            </Row>
        </Form>
    }
}

export default filter