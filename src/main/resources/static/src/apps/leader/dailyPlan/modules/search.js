import React, { Component, Fragment} from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Select, Button, Row, Col, Form, DatePicker } from 'antd'
import axios from "axios/index";
import {message} from "antd/lib/index";

const Option = Select.Option
const FormItem = Form.Item

@inject('store')
@observer
class search extends Component {

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

    handleSearch = () => {
        const store = this.props.store
        this.props.form.validateFields((err, values) => {
            if (!err) {
                store.date = values.date
                store.employeeId = values.employee
                store.actions.search(1, 10)
            }
        })
    }

    reset = () => {
        this.props.form.resetFields()
        this.props.store.date = undefined
        this.props.store.employeeId = undefined
    }

    render() {
        const store = this.props.store
        const { actions } = store
        const { getFieldDecorator } = this.props.form
        return <Fragment>
            {/*<Row>*/}
                <Col className="gutter-row" span={6}>
                    <FormItem label={"员工姓名"} labelCol={{span: 6}}>
                        {getFieldDecorator('employee')(
                            <Select
                                showSearch
                                filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                                style={{ width: 180 }}
                            >
                                {
                                    store.employeeList.map(item => {
                                        return <Option key={item.id}>{item.empName}</Option>
                                    })
                                }
                            </Select>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"日期"} labelCol={{span: 6}}>
                        {getFieldDecorator('date')(
                            <DatePicker/>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={2}>
                    <FormItem>
                        <Button type={"primary"} onClick={this.handleSearch}>
                            查询
                        </Button>
                    </FormItem>
                </Col>
            {/*</Row>*/}
        </Fragment>
    }
}

export default Form.create()(search)