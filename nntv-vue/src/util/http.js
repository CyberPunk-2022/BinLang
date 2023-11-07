import axios from 'axios'
import {ElMessage} from "element-plus";
import DialogLogin from "../components/base/LoginDialog.js";
import Cookies from "js-cookie";


console.log('VITE_APP_BASE_API', import.meta.env.VITE_APP_BASE_API)

const service = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_API, timeout: 300000 // request timeout
})
service.interceptors.request.use(config => {
    //从cookie中获取X-Access-Token
    config.headers['X-Access-Token'] = Cookies.get('X-Access-Token')
    //放入token即可,请求头
    return config;
}, error => {
    // Do something with request error
    return Promise.reject(error);
});


service.interceptors.response.use(response => {
    // Do something with response data
    if (response.status === 200) {
        if (response.data.code === '555555') {
            ElMessage({
                showClose: true,
                message: '登录才可以使用！',
                center: true,
                type: 'error'
            })

            console.log('response.config.url', response.config.url)

            DialogLogin(true);

        } else if (response.data.code === '000000') {
            // ElMessage({
            //     showClose: true,
            //     message: '请求成功',
            //     center: true,
            //     type: 'success'
            // })
        } else {
            ElMessage({
                showClose: true,
                message: '请求异常',
                center: true,
                type: 'error'
            })
        }
        return Promise.resolve(response)
    } else {
        ElMessage({
            showClose: true,
            message: '系统异常',
            center: true,
            type: 'error'
        })
        return Promise.reject(response)
    }
}, error => {

    return Promise.reject(error);
});


export function post(url, data) {
    return new Promise((resolve, reject) => {

        service.post(url, data).then(res => {
            resolve(res)

        }).catch((error => {
            reject(error)
        }))
    })

}

export function postFromData(url, data) {
    return new Promise((resolve, reject) => {

        service.post(url, data, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        }).then(res => {
            resolve(res)

        }).catch((error => {
            reject(error)
        }))
    })

}

export function get(url, params) {
    return new Promise((resolve, reject) => {
        service.get(url, {params}).then(res => {
            resolve(res)

        }).catch(error => {
            reject(error)
        });
    })

}

/**
 * 上传文件
 * @param url
 * @param file
 * @param uploadProgressCallback
 */
export function uploadFile(url, file, uploadProgressCallback) {
    return new Promise((resolve, reject) => {
        const formData = new FormData();
        formData.append('file', file);
        service.post(url, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            onUploadProgress: uploadProgressCallback
        }).then(res => {
            resolve(res)
        }).catch(error => {
            reject(error)
        })
    })

}

/**
 * 表示结果返回成功
 * @param res
 * @returns {boolean}
 */
export function isSuccess(res) {
    return res.data.code === '000000'
}
