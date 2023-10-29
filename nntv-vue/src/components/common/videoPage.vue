<template>
  <div>
    <xg-player :id="prop.videoIndex"
               :video-url="videoInfo.url"
               :poster="videoInfo.poster">
      <div class="right-info">
        <div>头像</div>
        <div>喜欢</div>
        <div>评论</div>
        <div>收藏</div>
        <div>分享</div>
      </div>
    </xg-player>
  </div>

</template>

<script setup>

import {onMounted} from "vue";
import 'xgplayer/dist/index.min.css';
import XgPlayer from "../base/xgPlayer.vue";


let prop = defineProps({
  videoInfo: {
    type: Object,
    required: true
  },
  videoIndex: {
    type: String,
    required: true
  }
})


onMounted(() => {
  console.log("创建播放器", prop)
})


</script>

<style scoped>
.right-info {
  position: absolute;
  bottom: 150px;
  right: 0px;
  color: red;
}

.right-info > div {
  margin-top: 10px;
  height: 40px;
  width: 60px;
  align-content: center;
  background-color: #0bc15f;
}
</style>