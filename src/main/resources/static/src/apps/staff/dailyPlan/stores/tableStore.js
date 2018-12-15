import {observable, action } from 'mobx';
import { message, Modal } from 'antd'
import axios from 'axios'
import util from "../../../../common/util";

class tableStore {
    // @observable fileData = []       // 上传列表源
    // @observable formData = []       // 总上传列表

    @observable currentIndex = 1    // 当前所在分页

    @observable id = undefined      // 日计划 id
    @observable date = undefined    // 查询工作计划的日期
    @observable dataSource = []
    @observable workPool = []       // 可选择工作池
    @observable workFromOption = []     // 来源选项
    @observable count = 1
    @observable finish = undefined   // 对话框完成选项
    @observable isAlter = undefined

    @observable visible = false
    @observable visibleSonTable = false

    @observable loading = false
    @observable loadingSubmitButton = false     // 提交日计划按钮
    @observable loadingNewPlanButton = false    // 新增日计划按钮

    // 选择
    @observable selectedRowKeys = []         // 选择框数据

    @observable submitStatus = true    // 是否已提交过一次, 禁用按钮, 默认禁用

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
        search: action( (values) => {

            this.loading = true

            let url = "/api/work/schedule/employee/querySchedule?";
            if (values !== undefined) {
                if (values.date !== undefined && values.date !== null) {
                    url = url + (values.date['_d'] === undefined ? '': "date="+util.gmtToStr(values.date["_d"]).substring(0, 10)+"&")
                }
                url = url + (values.workName === undefined ? '': "workName="+values.workName+"&")
            }

            switch (this.currentIndex) {
                case 2:
                    url = url + "finishStatus=uncompleted"
                    break
                case 3:
                    url = url + "finishStatus=completed"
                    break
                case 4:
                    url = url + "workFrom=w3"
                    break
                case 5:
                    url = url + "workFrom=w2"
                    break
            }

            axios({
                method: 'get',
                url: url
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        if (response.data.result.data !== null) {
                            this.dataSource = response.data.result.data.workScheduleDetailDtoList
                            this.date = response.data.result.data.date
                            this.submitStatus = response.data.result.data.submitStatus === 'submitted' ? true : false
                            this.id = response.data.result.data.id
                            // for (let i=0; i<this.dataSource.length; i++) {
                            //     this.formData.push(0)
                            // }
                        } else {
                            message.success("今天没有待办的工作项")
                            this.date = null
                            this.dataSource = []
                            this.submitStatus = true
                        }
                        this.loading = false
                    } else {
                        message.error("获取表格数据失败: " + response.data.status.message)
                        this.date = null
                        this.dataSource = []
                        this.submitStatus = true
                    }
                })
        }),

        // 获取可选择工作池
        queryWorkPool: action(() => {
            let url = '/api/work/schedule/employee/queryWorkPool?'
            // url = url + (( this.workName === undefined || this.workName === '' ) ? '' : "workName="+ this.workName + "&")

            axios({
                method: 'get',
                url: url,
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        this.workPool = response.data.result.data
                    } else {
                        message.error("获取工作池数据失败: " + response.data.status.message)
                    }
                })
        }),

        submit: action(() => {
            this.loadingNewPlanButton = true
            axios({
                method: 'post',
                url: '/api/work/schedule/employee/newSchedule',
                data: JSON.parse(JSON.stringify(this.selectedRowKeys))
            })
                .then(response => {
                    if (response.data.status.code === 1){
                        Modal.success({
                            title: '成功',
                            content: '生成计划成功',
                        });
                        this.selectedRowKeys = []
                        this.actions.search()
                        this.visibleSonTable = false
                    } else {
                        message.error("生成计划失败: " + response.data.status.message)
                    }
                    this.loadingNewPlanButton = false
                })
        }),

        // 拦截文件上传
        // beforeUploadHandle: action(file => {
        //     // 允许的类型
        //     const type = [ 'image/jpeg', 'image/png' ]
        //     console.log(file)
        //     if (type.indexOf(file.type) > -1) {
        //         this.fileData = [...this.fileData, file]
        //         return true
        //     } else {
        //         message.error("只能上传 jpg / png 格式的图片")
        //         return false
        //     }
        // }),

        // 监听文件变化
        // fileChange: action(info => {
        //     const { file, fileList } = info
        //     if (file.status !== 'uploading') {
        //         console.log(fileList);
        //         this.fileName = fileList[0].name
        //     }
        // }),

        // 文件列表的删除
        // fileRemove: action(file => {
        //     const index = this.fileData.indexOf(file)
        //     let newFileList = this.fileData.slice()
        //     newFileList = newFileList.filter((value, inde_x) => inde_x != index);
        //     this.fileData = newFileList
        // }),

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
            // if (this.fileData.length > 0) {
            //     this.formData[isAlter] = this.fileData
            // }
            // this.fileData = []

            dataSource[isAlter].finishStatus = values['finishStatus']
            dataSource[isAlter].finishTime = values['finishTime']
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
            // this.formData.splice(key-1, 1)
        }),

        // 清空表格
        resetTable: action(() => {
            this.dataSource = []
            this.count = 1
            // this.formData = []
        }),

        // 显示对话框
        showModal: action(() => {
            this.visible = true
        }),

        // 显示添加计划对话框
        showSonTable: action(() => {
            this.visibleSonTable = true
        }),

        // 隐藏对话框，清除数据
        hideModal: action(() => {
            this.visible = false
            this.visibleSonTable = false
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