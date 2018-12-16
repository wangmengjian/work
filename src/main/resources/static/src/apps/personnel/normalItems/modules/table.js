import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { withRouter } from 'react-router-dom'
import { Table, Select, Row, Col } from 'antd'
import Search from './search'

const Option = Select.Option

@inject('store', 'form')
@observer
class table extends Component {

    componentDidMount() {
        this.props.store.actions.search(1)
    }

    handleAlter = (record, index) => {
        const { form, store } = this.props
        console.log(record)
        form.setFieldsValue({
            workFrom: record.workFrom === 'w3' ? '常规工作项' : '临时工作项',
            workName: record.workName,
            workContent: record.workContent,
            workMinutes: record.workMinutes,
            // file: record.workInstructor
        })
        store.fileData = []
        store.id = store.dataSource[index].id
        // if (store.formData.length > 0) {
        //     if (store.formData[record.key-1] !== null) {
        //         store.fileData.push(store.formData[record.key-1])
        //     }
        // }
        store.actions.showModal()
    }

    render() {
        const store = this.props.store
        const { actions } = store
        const columns = [
            {   title: '#', dataIndex: 'index', width: 70, render: (text, record, index) => {
                    return (store.current-1)*(store.pageSize)+index+1
                }},
            {   title: '员工姓名', dataIndex: 'employeeName', width: 80},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 100, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
            {   title: '选项', dataIndex: 'operation', width: 150, render: (text, record, index) => {
                    return (
                        <Fragment>
                            {
                                record.workInstructor === null || record.workInstructor === '' ?
                                    null : <a href={record.workInstructor}>查看指导书&nbsp;&nbsp;</a>
                            }
                            <a href="javascript:;" onClick={() => this.handleAlter(record, index)}>修改</a>
                        </Fragment>
                    )
            }}
        ];

        return <Fragment>
            <Row>
                <Col span={8}>
                    {/*<Button*/}
                        {/*type="primary"*/}
                        {/*// onClick={actions.showModal}*/}
                    {/*>*/}
                        {/*新增工作项*/}
                    {/*</Button>*/}
                </Col>
                <Search/>
            </Row>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                pagination={{
                    pageSize: store.pageSize,
                    total: store.all,
                    current: store.current,
                    onChange: actions.search
                }}
                loading={store.loading}
                rowKey={'id'}
                // size="middle"
                // scroll={{ y : 470 }}
                // bordered
            /><br/>
        </Fragment>
    }
}

export default withRouter(table)