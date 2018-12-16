import React, { Component, Fragment } from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Form, Button, Row, Col, Select, DatePicker } from 'antd'

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
        this.props.form.validateFields(['date'], (err, values) => {
            if (!err) {
                this.props.store.actions.search(values)
            }
        })
    }

    render () {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        return <Form>
            {/*<Col className="gutter-row" span={5}>*/}
                {/*<FormItem label={"工作项名称"} labelCol={{span: 7}}>*/}
                    {/*{getFieldDecorator('workName')(*/}
                        {/*<Input placeholder={"请输入内容"} style={{width: 180}}/>*/}
                    {/*)}*/}
                {/*</FormItem>*/}
            {/*</Col>*/}
            {/*<Col className="gutter-row" span={5}>*/}
                {/*<FormItem label={"工作项类型"} labelCol={{span: 7}}>*/}
                    {/*{getFieldDecorator('workFrom')(*/}
                        {/*<Select*/}
                            {/*allowClear*/}
                            {/*style={{ width: 150 }}*/}
                        {/*>*/}
                            {/*{*/}
                                {/*store.workFromOption.map(item => <Option key={item.code}>{item.name}</Option>)*/}
                            {/*}*/}
                        {/*</Select>*/}
                    {/*)}*/}
                {/*</FormItem>*/}
            {/*</Col>*/}
            <Col className="gutter-row" span={5}>
                <FormItem label={"选择日期"} labelCol={{span: 7}}>
                    {getFieldDecorator('date')(
                        <DatePicker style={{ width: 150 }}/>
                    )}
                </FormItem>
            </Col>
            <Col className="gutter-row" span={2}>
                <FormItem>
                    <Button type={"primary"} onClick={this.handleSearch}>查询</Button>
                </FormItem>
            </Col>
        </Form>
    }
}

export default filter