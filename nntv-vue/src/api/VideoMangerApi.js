/**
 * 视频管理模块
 */
import * as http from "../util/http.js";

export function getPageList(param) {
    return http.post("/videoManager/getPageList", param)
}

export function saveVideo(param) {
    return http.post("/videoManager/saveVideo", param)
}

/**
 * 上传文件
 * @param file
 * @param uploadProgressCallback
 * @returns {Promise<unknown>}
 */

export function uploadFileToServer(file, uploadProgressCallback) {
    return http.uploadFile("/videoManager/upload", file, uploadProgressCallback)
}