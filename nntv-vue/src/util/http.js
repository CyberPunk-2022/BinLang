import {session} from '@/utils/storage.js'


this.$http.baseURL = "http://127.0.0.1:8808"
this.$http.interceptors.request.use(config => {
    // Do something before request is sent
    const token = session.get('X-ACCESS-TOKEN')
    config.headers.Authorization = token
    if (config.method.toUpperCase() === 'POST') {
        config.headers['Content-Type'] = 'application/json;charset=utf-8'
    }
    return config;
}, error => {
    // Do something with request error
    return Promise.reject(error);
});


this.$http.interceptors.response.use(response => {
    // Do something with response data
    if (response.status === 200) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(response)
    }
}, error => {

    return Promise.reject(error);
});


/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function get(url, params) {
    return new Promise((resolve, reject) => {
        this.$http.get(url, {
            params: params
        })
            .then(res => {
                resolve(res.data)
            })
            .catch(err => {
                reject(err.data)
            })
    })
}

/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function post(url, params) {
    return new Promise((resolve, reject) => {
        this.$http.post(url, QS.stringify(params))
            .then(res => {
                resolve(res.data)
            })
            .catch(err => {
                reject(err.data)
            })
    })
}

/**
 * get方法，对应get请求,直接在地址后面拼串的形式
 * @param {String} url [请求的url地址]
 * @param {String} params [请求时携带的参数]
 */
export function getDynamicynamic(url, params) {
    return new Promise((resolve, reject) => {
        const completeUrl = `${url}/${params}`
        this.$http.get(completeUrl, {})
            .then(res => {
                resolve(res.data)
            })
            .catch(err => {
                reject(err.data)
            })
    })
}

/**
 * post方法，导出文件
 * @param {String} url [请求的url地址]
 * @param {String} params [请求时携带的参数]
 */
export function getFileUseBlobByPost(url, params = {}) {
    return new promise((resolve, reject) => {
        this.$http({
            method: 'post',
            url,
            data: params,
            responseType: 'blob'
        })
            .then(res => {
                resolve(res)
            })
            .catch(err => {
                reject(err.data)
            })
    })
}

/**
 * 上传文件
 * @param url
 * @param file
 */
export function uploadFile(url, file) {
    this.$http.post({
        method: 'post',
        url,
        data: file,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
