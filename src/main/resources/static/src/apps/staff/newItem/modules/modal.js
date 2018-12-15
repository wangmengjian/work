import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Button, Input, Form, Radio, Upload, Icon } from 'antd'

const FormItem = Form.Item
const { TextArea } = Input

@inject('store', 'form')
@observer
class modal extends Component {

    render() {
        const { getFieldDecorator } = this.props.form
        const { visible, actions, fileData } = this.props.store


        return <Modal
            title="新建工作项"
            visible={visible}
            onOk={this.pushToTable}
            onCancel={actions.hideModal}
            style={{height: 300}}
        >

        </Modal>
    }
}

export default modal