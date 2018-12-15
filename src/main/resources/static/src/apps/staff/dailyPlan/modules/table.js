import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Button, message, Form, Row, Col } from 'antd'
import axios from "axios"
import Filter from './filter'
import util from '../../../../common/util'
import moment from 'moment'

const FormItem = Form.Item

@inject('store', 'form')
@observer
class table extends Component {

    componentDidMount () {
        this.props.store.actions.search()
        this.props.store.actions.queryWorkPool()
    }

    // 校验表单
    submit = () => {
        const store = this.props.store
        const { dataSource, actions } = this.props.store

        store.loadingSubmitButton = true

        const form = new FormData()
        let finishStatus = null
        form.append('id', store.id)
        for (let i=0; i < dataSource.length; i++) {
            if (dataSource[i].finishStatus === '已完成') {
                finishStatus = 'completed'
            } else {
                finishStatus = 'uncompleted'
            }
            // if (store.formData[i] !== 0) {
            //     for (let j=0; j < store.formData[i].length; j++) {
            //         form.append('workScheduleDetailDtoList['+i+'].pictures', store.formData[i][j])
            //     }
            // }
            form.append('workScheduleDetailDtoList['+i+'].id', dataSource[i].id)
            form.append('workScheduleDetailDtoList['+i+'].finishStatus', finishStatus)
            if (dataSource[i].finishTime !== null && dataSource[i].finishTime !== undefined) {
                form.append('workScheduleDetailDtoList['+i+'].finishTime', util.gmtToStr(dataSource[i].finishTime['_d']))
            }
            if (dataSource[i].finishFeedback !== null && dataSource[i].finishFeedback !== '') {
                form.append('workScheduleDetailDtoList[' + i + '].finishFeedback', dataSource[i].finishFeedback)
            }
            if (dataSource[i].finishCondition !== null && dataSource[i].finishCondition !== '') {
                form.append('workScheduleDetailDtoList['+i+'].finishCondition', dataSource[i].finishCondition)
            }
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
                    this.props.store.actions.search()
                } else {
                    message.error("提交失败: " + response.data.status.message)
                }
                store.loadingSubmitButton = false
            })
    }

    // 填写完成情况
    workEnd = (record, index) => {
        const { form, store } = this.props
        form.setFieldsValue({
            finishStatus: '已完成',
            finishCondition: record.finishCondition,
            finishFeedback: record.finishFeedback,
            finishTime: typeof record.finishTime === 'string' ? moment(record.finishTime) : record.finishTime
        })
        store.finish = '已完成'
        // if (store.formData.length > 0) {
        //     if (store.formData[record.key-1] !== null) {
        //         store.fileData.push(store.formData[record.key-1])
        //     }
        // }
        // store.fileData = []
        store.actions.showModal()
        store.isAlter = index
        // store.fileData = store.formData[store.isAlter]
    }

    render() {
        const store = this.props.store
        const date = new Date();

        const columns = [
            {   title: '#', dataIndex: 'key', width: 50, render: (text, record, index) => {
                    return index + 1
                }},
            {   title: '状态', dataIndex: 'finishStatus', width: 120},
            {   title: '类型', dataIndex: 'workFrom', width: 150},
            {   title: '名称', dataIndex: 'workName', width: 160},
            {   title: '内容', dataIndex: 'workContent', width: 200},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
            {   title: '选项', dataIndex: 'operation', width: 200, render: (text, record, index) => {
                    return (
                        // store.dataSource.length >= 1 && store.submitStatus === false ? (
                            <Fragment>
                                {
                                    record.workInstructor === null || record.workInstructor === '' ?
                                        '暂无指导书' : <a href={record.workInstructor}>查看指导书</a>
                                }&nbsp;&nbsp;
                                {
                                    !store.submitStatus ?
                                        <a href="javascript:;" onClick={() => this.workEnd(record, index)}>填写完成情况</a> : null
                                }
                            </Fragment>
                        // ) : '无'
                    );
                }}
        ];

        return <Fragment>
            <Row>
                <Col span={2}>
                    <Button
                        type={"primary"}
                        onClick={this.submit}
                        loading={store.loadingSubmitButton}
                        disabled={store.submitStatus}
                    >
                        提交
                    </Button>
                </Col>
                <Col span={15}>
                    <Button
                        type={"primary"}
                        onClick={() => store.actions.showSonTable()}
                    >
                        添加计划
                    </Button>
                </Col>
                <Filter/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                pagination={false}
                loading={store.loading}
                rowKey={"id"}
                scroll={{ y : 460 }}
                // size="middle"
                // bordered
            />
        </Fragment>
    }
}

export default table