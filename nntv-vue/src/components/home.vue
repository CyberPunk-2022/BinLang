<!--程序首页-->
<template>
  <div class="main-content">
    <!--右侧视频展示区域-->
<!--    <swipe-page :plays-data="playData"/>-->
    <main-page/>
  </div>

</template>

<script setup>

import {defineComponent, reactive, ref} from "vue";
import SwipePage from "./common/swipePage.vue";
import MainPage from "./common/mainPage.vue";

defineComponent({
  name: "index"
})


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


@media screen and (min-width: 768px) {
  .tabbar {
    margin: 0 auto;
    width: 400px;
    height: 100%;
  }
}
</style>