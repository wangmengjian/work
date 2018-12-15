import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table } from 'antd'

@inject('store', 'form')
@observer
class table extends Component {

    componentDidMount () {
        this.props.store.actions.search()
    }


    render() {
        const store = this.props.store
        const columns = [
            // {   title: '#', dataIndex: 'key', width: 50, render: (text, record, index) => {
            //         return index + 1
            //     }},
            {   title: '状态', dataIndex: 'finishStatus', width: 100},
            {   title: '类型', dataIndex: 'workFrom', width: 120},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '完成情况', dataIndex: 'finishCondition', width: 180, render: (text) => {
                    if (text === null || text === '') { return '无' } else { return text }
                }},
            {   title: '心得', dataIndex: 'finishFeedback', width: 180, render: (text) => {
                    if (text === null || text === '') { return '无' } else { return text }
                }},
            {   title: '完成时间', dataIndex: 'finishTime', width: 150, render: (text) => {
                    if (text === null || text === '') { return '无' } else { return text }
                }},
            {   title: '作业指导书', dataIndex: 'workInstructor', width: 200, render: (text, record) => {
                    // console.log(text.lastIndexOf('\\'))
                    if (text === null || text === '') { return '无' } else { return <a href={text}>{text}</a> }
                }},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
        ];

        return <Fragment>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                pagination={false}
                scroll={{ y: 460 }}
                loading={store.loading}
                rowKey={"id"}
                size={'middle'}
            />
        </Fragment>
    }
}

export default table