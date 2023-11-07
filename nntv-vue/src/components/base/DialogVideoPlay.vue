<template>
  <Teleport to="body">

    <el-dialog
        v-model="props.show"
        :title="'正在播放-'+extraInfo.title+'视频'"
        :close-on-click-modal="false"
        @close="props.clickClose"
        center
    >
      <div>
        <div style="width: 100%;margin:0 auto" :id="props.videoUuid" class="video-player"></div>
      </div>

      <div style="margin: 10px;font-weight: bolder;font-size: 20px;color: black">视频标题:{{ extraInfo.title }}</div>
      <div style="margin: 10px;font-size: 12px">作者:{{ extraInfo.authorName }}</div>
      <div style="margin: 10px;font-size: 12px">发布时间:{{ extraInfo.createTime }}</div>
      <div style="margin: 10px;font-size: 12px">视频描述信息:{{ extraInfo.description }}</div>

    </el-dialog>
  </Teleport>
</template>

<script setup>
import {ref, onMounted, onUpdated} from "vue";
import {useUserInfoStore} from "../store/userInfo.js";
import Player, {Events} from "xgplayer";


const userInfoStore = useUserInfoStore()

onMounted(() => {
  console.log('初始化播放器中')
  console.log('props', props)
  initPlayer()
})

const props = defineProps({
  show: {
    type: Boolean,
    default: false
  },
  videoUri: {
    type: String,
    default: true
  },
  poster: {
    type: String,
    default: false
  },
  videoUuid: {
    type: String,
    default: false
  },
  extraInfo: {
    type: Object,
    default: false
  },
  clickClose: {
    type: Function,
    default: () => {
    }
  }
})

let player;
const initPlayer = () => {
  let config = {
    "id": props.videoUuid,
    "url": props.videoUri,
    "playsinline": true,
    "poster": props.poster,
    "plugins": [],
    "autoplay": true,
    "closeVideoClick": true,
    height: 300, //视频高度
    fitVideoSize: 'fixWidth',
    keyShortcut: false
  } //禁用所有快捷键}

  setTimeout(() => {
    player = new Player(config);
  }, 1)


  // //js去掉百分号
  // player.on(Events.TIME_UPDATE, () => {
  //   //转成整数
  //   currentTime.value = parseInt(player.currentTime)
  //   duration.value = parseInt(player.duration)
  //   // console.log('百分比' + currentTime.value)
  // })
  //
  // /**
  //  * 监听播放
  //  */
  // player.on(Events.PLAYING, () => {
  //   //正在播放回调// playerStore.currentPlayer = player
  //   isPaused.value = false
  // })
  //
  // player.on(Events.PAUSE, () => {
  //   //正在播放回调
  //   console.log('暂停')
  //   isPaused.value = true
  // })
  // console.log('生成播放器', config)
};


</script>

<style scoped>

</style>
