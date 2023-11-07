<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="搜索结果页面"
      width="700px"
      height="500px"
      :close-on-click-modal="false"
      center
      @close="loginCloseDialog"
  >
    <div class="infinite-list-wrapper" style="overflow: auto;">

      <div class="video-list" v-for="(item,index) in records " style="margin: 10px">
        <div @click="goPlay(item)">
          <el-image :src="item.postUrl"> {{ item.title }}</el-image>
          <div style="padding: 10px;background-color: #f3f0f0;color: #1d2322;font-size: 12px"> {{ item.title }}</div>
          <div style="padding: 10px;background-color: #1d2322;color: white;font-size: 12px">作者：{{ item.authorName }}</div>
        </div>
      </div>
    </div>

    <template #footer>

    </template>
  </el-dialog>
</template>

<script setup>
import {reactive} from "vue";
import Player, {Events} from "xgplayer";

import {getPageList} from "../../api/VideoMangerApi.js";
import {useRouter} from "vue-router";
import DialogVideoPlay from "../base/DialogVideoPlay.js";


const props = defineProps({
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  }

})

const router = useRouter()

const goPlay = (item) => {
  DialogVideoPlay(true,item.url,item.videoUuid,item.poster,item)
}


let emits = defineEmits(["onCloseDialog"]);


const loginCloseDialog = () => {
  emits("onCloseDialog")
}


let records = reactive([])

const clickSearch = async (param) => {
  console.log('clickSearch', param)
  let res = await getPageList(param);
  console.log('res', res.data.data.records)
  records = res.data.data.records

  return records;
}


//
defineExpose({
  clickSearch
});

let player

const initPlayer = (id, videoUrl, poster) => {
  console.log('id', id)
  let config = {
    "id": id,
    "url": videoUrl,
    "playsinline": true,
    "poster": poster,
    "plugins": [],
    "autoplay": false,
    "closeVideoClick": true,
    height: window.innerHeight - 200, //视频高度
    fitVideoSize: 'fixWidth',
    keyShortcut: false, //禁用所有快捷键
    ignores: ['definition', 'error', 'fullscreen', 'i18n', 'pause', 'loading', 'play', 'time', 'mobile', 'pc', 'poster', 'progress', 'replay', 'volume']
  }
  player = new Player(config);

};
</script>

<style scoped>
.infinite-list-wrapper {
  margin: 0 auto; /* 使元素水平居中 */
}

.video-list {

  border: 0.2px solid #4d5856;
  width: 25.33%;
  padding: 10px;
   border-radius: 10px;
  float: left;
}
</style>