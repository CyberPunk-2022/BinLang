<template>

  <van-swipe :vertical="true" style="height: 100vh"
             @drag-start="dragStart"
             @drag-end="dragEnd"
             ref="swipe"
             :loop="false"
  >

    <van-swipe-item
        v-for="(item, i) in props.playsData.videos"
        :key="i"
        class="vide-item"
    >
      <video-page
          :video-info="item"
          :video-index="guid()"
          ref="videoInfo"/>
    </van-swipe-item>

  </van-swipe>

</template>

<script setup>

import VideoPage from "./videoPage.vue";
import {onMounted, ref} from "vue";
import {storeToRefs} from "pinia";
import {usePlayerStore} from "../store/player.js";

let playerStore = usePlayerStore();
let {xgPlayer} = storeToRefs(playerStore)


let swipe = ref(null)


let props = defineProps({
  playsData: {
    type: Object,
    required: true
  }
})

onMounted(() => {
  console.log(props.playsData)
})


const dragStart = (e) => {
  console.log("dragStart", e)
}


const dragEnd = (e) => {
  playerStore.playVideo(e.index)
  console.log("dragEnd", e)
}

// 生成uuid
const guid = () => {
  function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  }

  let s = S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4();

  return s;
}

</script>

<style scoped>
.vide-item {
  height: 100vh;
}
</style>