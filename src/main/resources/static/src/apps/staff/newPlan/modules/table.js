import React, { Fragment, Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Table, Pagination, Select, Row, Col, Button } from 'antd'

// const Option = Select.Option

@inject('store')
@observer
class table extends Component {

    componentDidMount() {
        this.props.store.actions.search()
    }

    render() {
        const store = this.props.store
        const { actions } = store
        const columns = [
            {   title: '#', dataIndex: 'index', width: 100, render: (text, record, index) => {
                    return (store.current-1)*(store.pageSize)+index+1
                }},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 180},
            {   title: '作业指导书', dataIndex: 'workInstructor', width: 200, render: (text) => {
                    if (text === null || text === '') { return '无' } else { return <a href={text}>{text}</a> }
                }},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 120},
        ];

        const rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                this.props.store.selectedRowKeys = selectedRowKeys
            },
        }

        return <Fragment>
            <Table
                dataSource={store.dataSource}
                columns={columns}
                rowSelection={rowSelection}
                pagination={false}
                rowKey={'id'}
                loading={store.loading}
                size={"middle"}
                scroll={{ y : 460 }}
            /><br/>
            <Button type={'primary'} onClick={() => actions.submit()}>生成</Button>
            {/*<Row>*/}
                {/*<Col className="gutter-row" span={21}>*/}
                    {/*<Pagination*/}
                        {/*current={store.current}*/}
                        {/*onChange={ page => actions.search(page, store.pageSize)}*/}
                        {/*total={store.all}*/}
                        {/*showTotal={total => `共有 ${total} 条记录`}*/}
                        {/*pageSize={store.pageSize}*/}
                    {/*/>*/}
                {/*</Col>*/}
                {/*<Col className="gutter-row" span={3}>*/}
                    {/*<span>每页条数:&nbsp;&nbsp;&nbsp;</span>*/}
                    {/*<Select*/}
                        {/*defaultValue={store.pageSize}*/}
                        {/*onChange={value => actions.handlePageSizeChange(value)}*/}
                    {/*>*/}
                        {/*<Option key={10}>10</Option>*/}
                        {/*<Option key={20}>20</Option>*/}
                        {/*<Option key={30}>30</Option>*/}
                    {/*</Select>*/}
                {/*</Col>*/}
            {/*</Row>*/}
        </Fragment>
    }
}

export default table