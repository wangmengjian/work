import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Button, message, Form } from 'antd'
import axios from "axios";

@inject('store', 'form')
@observer
class table extends Component {

    componentDidMount () {
        this.props.store.actions.search()
    }

    // 校验表单
    submit = () => {
        const store = this.props.store
        const { dataSource, actions } = this.props.store
        const form = new FormData()
        let finishStatus = null

        form.append('id', store.id)
        for (let i=0; i < dataSource.length; i++) {
            if (dataSource[i].finishStatus === '已完成') {
                finishStatus = 'completed'
            } else {
                finishStatus = 'uncompleted'
            }
            if (store.formData[i] !== 0) {
                for (let j=0; j < store.formData[i].length; j++) {
                    form.append('workScheduleDetailDtoList['+i+'].pictures', store.formData[i][j])
                }
            }
            form.append('workScheduleDetailDtoList['+i+'].finishCondition', dataSource[i].finishCondition)
            form.append('workScheduleDetailDtoList['+i+'].id', dataSource[i].id)
            form.append('workScheduleDetailDtoList['+i+'].finishFeedback', dataSource[i].finishFeedback)
            form.append('workScheduleDetailDtoList['+i+'].finishStatus', finishStatus)
        }

        axios({
            method: 'post',
            url: '/api/work/schedule/employee/submitSchedule',
            data: form
        })
            .then(response => {
                if (response.data.status.code === 1){
                    actions.resetTable()
                    message.success("提交成功: " + response.data.status.message)
                } else {
                    message.error("提交失败: " + response.data.status.message)
                }
            })
    }

    // 填写完成情况
    workEnd = (record, index) => {
        const { form, store } = this.props
        form.setFieldsValue({
            // finishStatus: record.finishStatus,
            finishStatus: '已完成',
            finishCondition: record.finishCondition,
            finishFeedback: record.finishFeedback
        })
        store.finish = '已完成'
        // store.fileData = []
        // if (store.formData.length > 0) {
        //     if (store.formData[record.key-1] !== null) {
        //         store.fileData.push(store.formData[record.key-1])
        //     }
        // }
        store.isAlter = index
        store.fileData = store.formData[store.isAlter]
        store.actions.showModal()
    }

    render() {
        const store = this.props.store
        const columns = [
            {   title: '#', dataIndex: 'key', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '状态', dataIndex: 'finishStatus', width: 120},
            {   title: '类型', dataIndex: 'workFrom', width: 150},
            {   title: '名称', dataIndex: 'workName', width: 160},
            {   title: '内容', dataIndex: 'workContent', width: 200},
            {   title: '作业指导书', dataIndex: 'workInstructor', width: 200, render: (text, record) => {
                    // console.log(text.lastIndexOf('\\'))
                    return <a href={text}>{text}</a>
                }},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
            {   title: '选项', dataIndex: 'operation', width: 120, render: (text, record, index) => {
                    return (
                        store.dataSource.length >= 1 ? (
                            <a href="javascript:;" onClick={() => this.workEnd(record, index)}>填写完成情况</a>
                        ) : null
                    );
                }}
        ];

        return <Fragment>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                pagination={false}
                scroll={{ y: 460 }}
                loading={store.loading}
                rowKey={"id"}
            />
            <Button type={"primary"} onClick={this.submit}>提交</Button>
        </Fragment>
    }
}

export default table