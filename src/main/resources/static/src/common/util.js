const util = {

    // GMT 转普通时间格式
    gmtToStr: (time) => {
        let date = new Date(time)
        let Str=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' '
        return Str
    },

}

export default util