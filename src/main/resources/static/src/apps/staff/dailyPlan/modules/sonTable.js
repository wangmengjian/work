import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Modal, Button, Table } from 'antd'

@inject('store')
@observer
class sonTable extends Component {

    render() {
        const store = this.props.store
        const { actions } = store
        const columns = [
            {   title: '#', dataIndex: 'index', width: 80, render: (text, record, index) => {
                    return index+1
                }},
            {   title: '名称', dataIndex: 'workName', width: 150},
            {   title: '内容', dataIndex: 'workContent', width: 200},
            {   title: '标准时间', dataIndex: 'workMinutes', width: 150, render: (text) => {
                    return <span>{text}&nbsp;分钟</span>
                }},
            {   title: '选项', dataIndex: 'operation', width: 200, render: (text, record) => {
                    if (record.workInstructor === null || record.workInstructor === '') {
                        return '暂无指导书'
                    } else {
                        return <a href={record.workInstructor}>查看指导书</a>
                    }
                }},
        ];

        const rowSelection = {
            onChange: (selectedRowKeys, selectedRows) => {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
                this.props.store.selectedRowKeys = selectedRowKeys
            },
        }

        return <Modal
            title="添加日计划"
            visible={store.visibleSonTable}
            onCancel={actions.hideModal}
            width={1000}
            style={{ height: 500 }}
            footer={[
                <Button key="cancel" onClick={actions.hideModal}>取消</Button>,
                <Button
                    key="submit"
                    type={'primary'}
                    onClick={() => actions.submit()}
                    loading={store.loadingNewPlanButton}
                    disabled={store.selectedRowKeys.length > 0 ? false : true}
                >
                    生成
                </Button>
            ]}
        >
            <Table
                dataSource={store.workPool}
                columns={columns}
                rowSelection={rowSelection}
                pagination={false}
                rowKey={'id'}
                loading={store.loadingButton}
                size={"middle"}
                scroll={{ y : 400 }}
                bordered
            />
        </Modal>
    }
}

export default sonTable