import React, {Component, Fragment} from 'react'
import {observer, inject} from 'mobx-react'
import { Table, Pagination, Select, Col, Row, Form } from 'antd';

const Option = Select.Option;

@inject('store')
@observer
class detailStockTable extends Component {

    componentDidMount() {
        this.props.store.actions.search(this.props.form, 1, this.props.store.pageSize)
    }

    render() {
        const {current, all, pageSize, loading, dataSource} = this.props.store
        const {search, handlePageSizeChange} = this.props.store.actions
        const columns = [
            { title: '#', dataIndex: 'index', width: 70, render: (text, record, index) => {return (current-1)*(pageSize)+index+1}},
            { title: '商品编码', dataIndex: 'proNumber', width: 150 },
            { title: '商品名称', dataIndex: 'proName', width: 120 },
            { title: '销售量', dataIndex: 'proQuantity', width: 120 },
            { title: '售价', dataIndex: 'proPrice', width: 100 },
            { title: '规格', dataIndex: 'proSpec', width: 120 },
            { title: '类型', dataIndex: 'proType', width: 120 },
            { title: '操作', dataIndex: 'type', width: 120 },
            { title: '操作类型', dataIndex: 'operateType', width: 120 },
            { title: '操作时间', dataIndex: 'operateTime', width: 180 }
        ];

        return <Fragment>
            <Table
                pagination={false}
                scroll={{ y: 460 }}
                loading={loading}
                columns={columns}
                dataSource={dataSource}
                size={'middle'}
            /><br></br>
            <Row>
                <Col className="gutter-row" span={21}>
                    <Pagination
                        current={current}
                        onChange={ page => search(this.props.form, page, pageSize)}
                        total={all}
                        showTotal={total => `共有 ${total} 条记录`}
                        pageSize={pageSize}
                    />
                </Col>
                <Col className="gutter-row" span={3}>
                    <span>每页条数:&nbsp;&nbsp;&nbsp;</span>
                    <Select
                        defaultValue={pageSize}
                        onChange={value => handlePageSizeChange(value)}
                    >
                        <Option key={10}>10</Option>
                        <Option key={20}>20</Option>
                        <Option key={30}>30</Option>
                    </Select>
                </Col>
            </Row>
        </Fragment>
    }
}

export default Form.create()(detailStockTable)