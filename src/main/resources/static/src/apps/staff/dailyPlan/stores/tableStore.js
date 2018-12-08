import {observable, action } from 'mobx';
import { message } from 'antd'
import axios from 'axios'

class tableStore {
    @observable fileData = []   // 上传列表源
    @observable formData = []   // 总上传列表

    @observable id = undefined  // 日计划 id
    @observable dataSource = []
    @observable workFromOption = []     // 来源选项
    @observable count = 1
    @observable finish = undefined   // 对话框完成选项
    @observable isAlter = undefined
    @observable visible = false
    @observable loading = false
    @observable loadingButton = false

    actions = {

        // 获取工作项来源选择框数据
        getWorkFromOption: action(() => {
            axios({
                method: 'get',
                url: '/api/work/sysConfig/workFrom',
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.workFromOption = response.data.result.data
                    } else {
                        message.error("获取选择框数据失败: " + response.data.status.message)
                    }
                })
        }),

        // 筛选数据
        search: action( values => {

            let url = "/api/work/schedule/employee/querySchedule?";
            if (values !== undefined) {
                url = url + (values.workFrom === undefined ? '': "workFrom="+values.workFrom+"&")
                url = url + (values.workName === undefined ? '': "workName="+values.workName)
            }
            this.loading = true

            axios({
                method: 'get',
                url: url
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.dataSource = response.data.result.data.workScheduleDetailDtoList
                        this.id = response.data.result.data.id
                        this.loading = false
                        for (let i=0; i<this.dataSource.length; i++) {
                            this.formData.push(0)
                        }
                    } else {
                        message.error("获取表格数据失败: " + response.data.status.message)
                    }
                })
        }),

        // 拦截文件上传
        beforeUploadHandle: action(file => {
            // 允许的类型
            const type = [ 'image/jpeg', 'image/png' ]
            console.log(file)
            if (type.indexOf(file.type) > -1) {
                this.fileData = [...this.fileData, file]
                return true
            } else {
                message.error("只能上传 jpg / png 格式的图片")
                return false
            }
        }),

        // 监听文件变化
        // fileChange: action(info => {
        //     const { file, fileList } = info
        //     if (file.status !== 'uploading') {
        //         console.log(fileList);
        //         this.fileName = fileList[0].name
        //     }
        // }),

        // 文件列表的删除
        fileRemove: action(file => {
            const index = this.fileData.indexOf(file)
            let newFileList = this.fileData.slice()
            newFileList = newFileList.filter((value, inde_x) => inde_x != index);
            this.fileData = newFileList
        }),

        workEnd: action( values => {
            const { dataSource, isAlter } = this;

            // const newData = {
            //     finishStatus: values['finishStatus'],
            //     finishCondition: values['finishCondition'],
            //     finishFeedback: values['finishFeedback'],
            // };
            // if (this.fileName !== undefined) {
            //     newData.file = this.fileName
            //     this.fileName = undefined
            // }
            // 如果上传了文件
            if (this.fileData.length > 0) {
                this.formData[isAlter] = this.fileData
            }
            this.fileData = []
            console.log(this.formData)

            // isAlter 有值，意味着是通过修改表格触发的该函数
            dataSource[isAlter].finishStatus = values['finishStatus']
            dataSource[isAlter].finishCondition = values['finishCondition']
            dataSource[isAlter].finishFeedback = values['finishFeedback']
            this.isAlter = undefined
            this.finish = undefined
            this.actions.hideModal()
        }),

        // 删除行
        handleDelete: action((key) => {
            let dataSource = JSON.parse(JSON.stringify(this.dataSource));
            dataSource = dataSource.filter(item => item.key !== key)

            // 重新排列 key
            for (let i=0; i < dataSource.length; i++) {
                dataSource[i].key = i+1
            }
            this.dataSource = dataSource,
            this.count = dataSource.length + 1
            this.formData.splice(key-1, 1)
        }),

        // 清空表格
        resetTable: action(() => {
            this.dataSource = []
            this.count = 1
            this.formData = []
        }),

        // 显示对话框
        showModal: action(() => {
            this.visible = true
        }),

        // 隐藏对话框，清除数据
        hideModal: action(() => {
            this.visible = false
        }),

        // 隐藏 / 显示表单内容
        // changeModalStatus: action(() => {
        //     if (this.finish === '已完成') {
        //         document.getElementById("modal_form").style.display = 'block'
        //     } else {
        //         document.getElementById("modal_form").style.display = 'none'
        //     }
        // })
    }
}

export default new tableStore()