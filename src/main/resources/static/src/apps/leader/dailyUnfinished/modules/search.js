import React, { Component, Fragment} from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Select, Button, Row, Col, Form } from 'antd'

const Option = Select.Option
const FormItem = Form.Item

@inject('store')
@observer
class search extends Component {
    render() {
        const { getFieldDecorator } = this.props.form
        return <Fragment>
            <Row>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"工作项名称"} labelCol={{span: 6}}>
                        {getFieldDecorator('itemsName')(
                            <Input style={{ width: 180 }} />
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"执行人"} labelCol={{span: 6}}>
                        {getFieldDecorator('staffName')(
                            <Select
                                showSearch
                                filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                                style={{ width: 180 }}
                            >
                                <Option key={'1'}>张三</Option>
                                <Option key={'2'}>李四</Option>
                            </Select>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button type={"primary"}>
                        查询
                    </Button>
                </Col>
            </Row>

        </Fragment>
    }
}

export default Form.create()(search)