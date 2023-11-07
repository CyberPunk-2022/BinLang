import {defineStore} from "pinia";
export const useUserInfoStore = defineStore("userInfoStore", {

    state() {
        return {
            userInfo: {}
        }
    },
    actions: {

        addUserInfo(userInfo) {
            this.userInfo = userInfo;
        },

        removeUserInfo() {
            this.userInfo = {};
        },
        getUserInfo() {
            return this.userInfo;
        }
    }
})
