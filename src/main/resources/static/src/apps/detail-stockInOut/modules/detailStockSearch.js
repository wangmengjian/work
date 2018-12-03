import React, {Component} from 'react'
import {observer, inject} from 'mobx-react'
import { Form, Row, Col, Button, Select, Input, DatePicker } from 'antd'
import locale from "antd/lib/date-picker/locale/zh_CN";
import util from "../../../common/util";
import axios from "axios/index";
import {action} from "mobx/lib/mobx";

const FormItem = Form.Item
const Option = Select.Option
const { RangePicker } = DatePicker

@inject('store')
@observer
class detailStockSearch extends Component {

    // 筛选前表单验证
    search = () => {
        this.props.form.validateFields((err, values) => {
            if (!err) {
                this.props.store.actions.search(values, 1, this.props.store.pageSize)
            }
        })
    }

    // 重置搜索
    resetTable = () => {
        this.props.form.resetFields()
        this.search()
    }

    render() {
        const { getFieldDecorator } = this.props.form

        return <Form>
            <Row>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"明细类别"} labelCol={{ span: 6 }}>
                        {getFieldDecorator('type')(
                            <Select
                                allowClear
                                style={{ width: 180 }}
                            >
                                <Option key={"001"}> 入库 </Option>
                                <Option key={"002"}> 出库 </Option>
                            </Select>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={5}>
                    <FormItem label={"商品编码"} labelCol={{ span: 6 }}>
                        {getFieldDecorator('proNumber')(
                            <Input style={{ width: 180 }}/>
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={9}>
                    <FormItem label={"时间"} labelCol={{ span: 6 }}>
                        {getFieldDecorator('time')(
                            <RangePicker
                                style={{ width: 280 }}
                                allowClear
                                format="YYYY-MM-DD"
                                locale={locale}
                            />
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button type="primary" onClick={this.search}>
                        查询
                    </Button>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button onClick={this.resetTable}>
                        重置
                    </Button>
                </Col>
            </Row>
        </Form>
    }
}

export default Form.create()(detailStockSearch)