import * as http from "../util/http.js";
import {postFromData} from "../util/http.js";

export function loginOrRegister(param) {
    return http.postFromData("/account/loginOrRegister", param)
}