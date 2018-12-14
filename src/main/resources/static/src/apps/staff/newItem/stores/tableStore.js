import {observable, action } from 'mobx';
import { message } from 'antd'

class tableStore {
    @observable workFrom = []   // 工作项来源选项拼接
    @observable fileData = []   // 上传列表源
    @observable fileName = undefined    // 暂存上传文件名
    @observable loading = false

    actions = {

        // 监听文件变化
        fileChange: action(info => {
            const { file, fileList } = info
            if (file.status !== 'uploading') {
                console.log(file, fileList);
                this.fileName = fileList[0].name
            }
        }),

        // 拦截文件上传
        beforeUploadHandle: action(file => {
            // 允许的类型
            const type = [
                'application/pdf',
                'application/vnd.ms-powerpoint',
                'application/msword',
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
            ]
            if (type.indexOf(file.type) > -1) {
                this.fileData = [...this.fileData, file]
                return true
            } else {
                message.error("请上传正确的格式")
                return false
            }
        }),

        // 文件列表的删除
        fileRemove: action(file => {
            const index = this.fileData.indexOf(file)
            let newFileList = this.fileData.slice()
            newFileList = newFileList.filter((value, inde_x) => inde_x != index);
            this.fileData = newFileList
            this.fileName = undefined
            if (this.isAlter !== undefined) {
                this.formData[this.isAlter-1] = null
            }
        }),

        handleAdd: action( values => {
            const { count, dataSource, isAlter } = this;
            this.workFrom = [...this.workFrom, values['workFrom']]
            const newData = {
                key: count,
                workName: values['workName'],
                workContent: values['workContent'],
                workMinutes: values['workMinutes'],
                workFrom: this.workFrom[count-1].substring(2)
            };

            // 如果当前工作项上传了文件
            if (this.fileData.length === 1) {
                // 是否经由修改按钮再重新上传文件
                if (this.isAlter === undefined) {
                    this.formData = [...this.formData, this.fileData[0]]
                } else {
                    this.formData[isAlter-1] = this.fileData[0]
                }
            } else {
                if (this.isAlter === undefined) {
                    this.formData = [...this.formData, null]
                } else {
                    this.formData[isAlter-1] = null
                }
            }

            // 上传了文件
            if (this.fileData.length > 0) {
                newData.file = this.fileData[0].name
            }

            this.fileData = []
            console.log(newData)
            // isAlter 有值，意味着是通过修改表格触发的该函数
            if (isAlter === undefined) {
                this.dataSource = [...dataSource, newData]
                this.count = count + 1
            } else {
                newData.key = isAlter
                dataSource[isAlter-1] = newData
                this.isAlter = undefined
            }
            this.actions.hideModal()
        }),
    }
}

export default new tableStore()