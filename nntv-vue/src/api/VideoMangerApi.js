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


export function uploadFile(file) {
    return http.post("/videoManager/upload", file)
}