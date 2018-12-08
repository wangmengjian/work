import React, { Component, Fragment} from 'react'
import { inject, observer } from 'mobx-react'
import { Input, Button, Row, Col, Form } from 'antd'

const FormItem = Form.Item

@inject('store')
@observer
class search extends Component {

    handleSearch = () => {
        const { form, store } = this.props
        form.validateFields((err, values) => {
            if (!err) {
                store.workName = values.workName
                this.props.store.actions.search(1, store.pageSize)
            }
        })
    }

    render() {
        const { getFieldDecorator } = this.props.form
        return <Fragment>
            <Row>
                <Col className="gutter-row" span={6}>
                    <FormItem label={"工作项名称"} labelCol={{span: 6}}>
                        {getFieldDecorator('workName')(
                            <Input style={{ width: 180 }} />
                        )}
                    </FormItem>
                </Col>
                <Col className="gutter-row" span={2}>
                    <Button type={"primary"} onClick={this.handleSearch}>
                        查询
                    </Button>
                </Col>
            </Row>

        </Fragment>
    }
}

export default Form.create()(search)