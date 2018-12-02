import axios from 'axios'
import qs from 'qs'

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8'
axios.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8'

// 请求(request)拦截器
axios.interceptors.request.use(config => {
    // 每个请求都在url末端加上时间参数
    if (config.url.indexOf('?') !== -1) {
        config.url += `&t=${new Date().getTime()}`
    } else {
        config.url += `?t=${new Date().getTime()}`
    }
    // 在请求发送到服务器之前修改该请求，此方法只适用于PUT、POST和PATCH方法中，此方法须返回一个string、ArrayBuffer或者Stream。
    config.transformRequest = [function (data, headers) {
        return qs.stringify(data, { allowDots: true })
    }]
    config.paramsSerializer = params => {
        return qs.stringify(params, { arrayFormat: 'repeat' })
    }
    return config
}, error => {
    return Promise.reject(error)
})

// 响应(response)拦截器
axios.interceptors.response.use(resp => {
    const { data } = resp
    if (data !=  '' && data.status != undefined && data.status.code != undefined && data.status.code === -2) {
        window.location.href="/sign/login?from="+window.location.href
    }
    return resp
}, error => {
    return Promise.reject(error)
})

export default axios