<template>
  <div>
    <div id="videoUuid" class="video-player"></div>
  </div>
</template>
<script setup>
import Player, {Events} from "xgplayer";
import {onMounted} from "vue";
import {useUserInfoStore} from "../store/userInfo.js";

const userInfoStore = useUserInfoStore()


onMounted(() => {
  console.log('初始化播放器中')
  console.log('props', props)
  console.log('history.state', history.state)
  initPlayer(history.state.videoUri,history.state.poster)
})

const props = defineProps({
  videoUri: {
    type: String,
    required: true
  },
  poster: {
    type: String,
    required: false
  },
  videoUuid: {
    type: String,
    required: true
  },
  clickClose: {
    type: Function,
    default: () => {
    }
  }
})
let player;
const initPlayer = (videoUri,poster) => {
  let config = {
    "id": "videoUuid",
    "url": videoUri,
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

  console.log('config', config)
  player = new Player(config);

  //js去掉百分号
  player.on(Events.TIME_UPDATE, () => {
    //转成整数
    currentTime.value = parseInt(player.currentTime)
    duration.value = parseInt(player.duration)
    // console.log('百分比' + currentTime.value)
  })

  /**
   * 监听播放
   */
  player.on(Events.PLAYING, () => {
    //正在播放回调// playerStore.currentPlayer = player
    isPaused.value = false
  })

  player.on(Events.PAUSE, () => {
    //正在播放回调
    console.log('暂停')
    isPaused.value = true
  })
  console.log('生成播放器', config)
};

</script>

<style scoped>

</style>