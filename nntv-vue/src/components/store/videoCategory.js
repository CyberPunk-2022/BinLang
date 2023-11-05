import {defineStore} from "pinia";

//todo 这个一般是放在后台管理的
//视频分类的数据
export const useVideoCategoryStore = defineStore('videoCategory', {

    state() {
        return {
            videoCategoryList: [{
                videoCategoryId: "123111",
                videoCategoryName: "本地视频"
            }, {
                videoCategoryId: "12311122",
                videoCategoryName: "体育视频"
            }, {
                videoCategoryId: "1231112233",
                videoCategoryName: "搞笑娱乐"
            }, {
                videoCategoryId: "123111223344",
                videoCategoryName: "音乐舞蹈"
            }]
        }
    },
    actions: {
        getVideoCategoryList() {
            return this.videoCategoryList
        }
    }
})

