<template>
  <div class="video-box">
    <div :id="id"></div>
    <slot/>
  </div>

</template>
<script setup>
import {onMounted, watch} from "vue";
import Player from "xgplayer";
import {usePlayerStore} from "../store/player.js";
import {storeToRefs} from "pinia";

// 获取pinia里的数据

// 使pinia里state变成响应式数据
let playerStore = usePlayerStore();
let {xgPlayer} = storeToRefs(playerStore)

let player = null;

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
  videoUrl: {
    type: String,
    default: () =>
        "https://sf1-cdn-tos.huoshanstatic.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-360p.mp4",
  },
  poster: {
    type: String,
    default: () =>
        "http://lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/poster.jpg",
  },
  playsinline: {
    type: Boolean,
    default: true,
  },
  width: {
    type: String,
    default: '100%'
  },
  height: {
    type: String,
    default: '100%'
  }
});

watch(
    () => props.videoUrl,
    (newUrl) => {
      initPlayer();
    },
    {
      deep: true,
    }
);

onMounted(() => {
  initPlayer();
});

// 初始化西瓜视频
const initPlayer = () => {

  console.log('window.innerWidth', window)
  console.log('width', props.width.replace("%",''))
  let config = {
    "id": props.id,
    "url": props.videoUrl,
    "playsinline": true,
    "poster": props.poster,
    "plugins": [],
    "autoplay": false,
    "closeVideoClick": true,
    height: window.innerHeight-80,
    width: window.innerWidth
  }

  player = new Player(config);
  player.pause()
  //把播放器存入到store中去做播放暂停以及其他操作
  playerStore.addPlayer(props.id, player);
  //js去掉百分号
};
</script>