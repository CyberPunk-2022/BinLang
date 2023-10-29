<template>
  <!-- 最外层容器-->
  <div class="v-main-box">
    <!-- 顶部搜索栏位-->
    <div class="v-search-bar">
      <div class="v-search-bar-logo">
        <h1>logo</h1>
      </div>
      <div>
        <van-search
            v-model="value"
            show-action
            shape="round"
            placeholder="请输入想看的视频"
            background="#c9c8c8"
            @search="onSearch"
        >
          <template #action>
            <div @click="onClickButton">搜索</div>
          </template>
        </van-search>
      </div>
      <div class="v-search-bar-other">
        登录信息，其他菜单
      </div>
    </div>
    <div class="v-main-box-video">
      <!--左侧菜单栏位-->
      <div class="v-main-box-video-left">
        <div class="v-main-box-video-left-item" style="margin-top: 70px">
          <div type="default">首页1</div>
        </div>
        <div class="v-main-box-video-left-item" style="margin-top: 20px">
          <div type="default">首页2</div>
        </div>
        <div class="v-main-box-video-left-item" style="margin-top: 20px">
          <div type="default">首页3</div>
        </div>
        <div class="v-main-box-video-left-item" style="margin-top: 20px">
          <div type="default">首页4</div>
        </div>
        <div class="v-main-box-video-left-item" style="margin-top: 20px">
          <div type="default">首页5</div>
        </div>
      </div>
      <!--右侧侧菜单栏位-->
      <div class="v-main-box-video-right">
        <swipe-page :plays-data="playData"/>
      </div>
    </div>
  </div>
</template>


<script setup>

import SwipePage from "./swipePage.vue";
import {reactive} from "vue";


let playData = reactive({
  "videos": [
    {
      "url": "//lf3-static.bytednsdoc.com/obj/eden-cn/nupenuvpxnuvo/xgplayer_doc/xgplayer-demo.mp4",
      "poster": "//lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/poster.jpg",
    },
    {
      "url": "https://cesium.com/public/SandcastleSampleData/big-buck-bunny_trailer.mp4",
      "poster": "//lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/poster.jpg",
    }
  ]
})


</script>


<style scoped>


.v-search-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  width: 100%;
  background-color: #a27b7b;
}

.v-main-box-video {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  width: 100%;
  background-color: #f5f5f5;
}

.v-main-box-video-left {
  width: 10%;
  height: 100vh;
  background-color: #c9c8c8;
}

.v-main-box-video-right {
  width: 90%;
  border-radius: 100px;
}

.v-search-bar-logo {
  margin-left: 20px;
}

.v-search-bar-other {
  margin-right: 20px;
}

.v-main-box-video-left > div {
  margin-left: 20px;
}

.v-main-box-video-left-item > div {
  margin-left: 20px;
}
</style>