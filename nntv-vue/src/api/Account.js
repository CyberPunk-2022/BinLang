import * as http from "../util/http.js";
import {postFromData} from "../util/http.js";

export function loginOrRegister(param) {
    return http.postFromData("/account/loginOrRegister", param)
}


/**
 * 获取用户信息
 * @param param
 * @returns {Promise<unknown>}
 */
export function getUserInfo(param) {
    return http.get("/account/getUserInfo", param)
}



/**
 * 更新用户信息
 * @param param
 * @returns {Promise<unknown>}
 */
export function updateUserInfo(param) {
    return http.post("/account/updateUserInfo", param)
}


