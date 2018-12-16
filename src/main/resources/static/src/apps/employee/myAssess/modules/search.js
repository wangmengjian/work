import React, { Component, Fragment} from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Button, Row, Col, Form, DatePicker } from 'antd'
import locale from "antd/lib/date-picker/locale/zh_CN";
import util from "../../../../common/util";

const { RangePicker } = DatePicker
const FormItem = Form.Item

@inject('store')
@observer
class search extends Component {

    handleSearch = () => {
        const { form, store } = this.props
        form.validateFields((err, values) => {
            if (!err) {
                store.workName = values['workName']
                if (values.time.length > 0) {
                    store.beginTime = util.gmtToStr(values.time[0]["_d"])
                    store.endTime = util.gmtToStr(values.time[1]["_d"])
                } else {
                    store.beginTime = undefined
                    store.endTime = undefined
                }
            }
        })
        this.props.store.actions.search(1)
    }

    render() {
        const { getFieldDecorator } = this.props.form
        return <Fragment>
                <Col span={6}>
                    <FormItem label={"工作项名称"} labelCol={{span: 6}}>
                        {getFieldDecorator('workName')(
                            <Input style={{ width: 180 }} />
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
                <Col span={2}>
                    <FormItem>
                        <Button style={{ float: 'right' }} type={"primary"} onClick={this.handleSearch}>
                            查询
                        </Button>
                    </FormItem>
                </Col>
        </Fragment>
    }
}

export default Form.create()(search)