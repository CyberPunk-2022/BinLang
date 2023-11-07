import * as http from "../util/http.js";

export function getPageList(param) {
    return http.post("/comment/getPageList", param)
}

export function submitComment(param) {
    return http.post("/comment/submitComment", param)
}
