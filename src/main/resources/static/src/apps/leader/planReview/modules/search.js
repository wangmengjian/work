import React, { Component, Fragment} from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Select, Button, Row, Col, Form, DatePicker } from 'antd'
import axios from "axios/index";
import {message} from "antd/lib/index";
import locale from "antd/lib/date-picker/locale/zh_CN";
import util from '../../../../common/util'

const { RangePicker } = DatePicker
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
                store.workName = values.workName
                store.employeeId = values.employee
                if (values.time.length > 0) {
                    store.beginTime = util.gmtToStr(values.time[0]["_d"])
                    store.endTime = util.gmtToStr(values.time[1]["_d"])
                } else {
                    store.beginTime = undefined
                    store.endTime = undefined
                }

                store.actions.search(1, store.pageSize)
            }
        })
    }

    reset = () => {
        this.props.form.resetFields()
        this.props.store.workName = undefined
        this.props.store.employeeId = undefined
    }

    render() {
        const { getFieldDecorator } = this.props.form
        const store = this.props.store
        return <Fragment>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"工作项名称"} labelCol={{span: 6}}>
                        {getFieldDecorator('workName')(
                            <Input style={{ width: 150 }} />
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={5}>
                    <FormItem label={"员工姓名"} labelCol={{span: 6}}>
                        {getFieldDecorator('employee')(
                            <Select
                                allowClear
                                showSearch
                                filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                                style={{ width: 150 }}
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
                    <FormItem label={"时间"} labelCol={{span: 4}}>
                        {getFieldDecorator('time')(
                            <RangePicker
                                style={{ width: 250 }}
                                allowClear
                                format="YYYY-MM-DD"
                                locale={locale}
                            />
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

        </Fragment>
    }
}

export default Form.create()(search)