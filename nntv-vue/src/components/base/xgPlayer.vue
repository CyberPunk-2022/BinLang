<template>
  <div class="mask-filter"  style="position: absolute"/>
  <div>
    <div :id="id" class="v-xg-video"/>

    <div class="right-menu">
      <slot name="right-menu">
        右侧菜单
      </slot>
    </div>
    <div class="video-footer">
      <div>
        <slot name="video-footer">
          底部栏位
        </slot>
      </div>
      <div class="vide-progress">

        <div style="display: inline-block; margin-right: 10px">
          <el-button style="border-radius: 20px;width: 40px;height: 40px;background-color: #4d5856" type="primary"
                     v-on:indexId="props.id" @click="playOrPause">
            <van-icon name="play" v-if="isPaused" size="15"/>
            <van-icon name="pause" v-if="!isPaused" size="15"/>
          </el-button>
        </div>
        <div style="display: inline-block; margin-right: 20px;width: 20px;height: 30px;">
          {{ currentTime }}/{{ duration }}
        </div>
        <div style="width: 91%;display: inline-block;">
          <van-slider v-model="currentTime" :max="duration" @change="changeProgress" bar-height="4px"
                      active-color="#ee0a24">
          </van-slider>
        </div>


      </div>

    </div>
  </div>
</template>
<script setup>
import {onMounted, reactive, ref, watch} from "vue";
import Player from "xgplayer";
import {usePlayerStore} from "../store/player.js";
import {storeToRefs} from "pinia";


// 获取pinia里的数据

// 使pinia里state变成响应式数据
let playerStore = usePlayerStore();
let {xgPlayer} = storeToRefs(playerStore)
import {Events} from 'xgplayer'

let player = null;


let isPaused = ref(true)


let currentTime = ref(0)

let duration = ref(0)

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
    default: '100'
  },
  height: {
    type: String,
    default: '100'
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


/**
 * 鼠标时间上下监听
 * @param event
 */

// 初始化西瓜视频
const initPlayer = () => {
  let config = {
    "id": props.id,
    "url": props.videoUrl,
    "playsinline": true,
    "poster": props.poster,
    "plugins": [],
    "autoplay": false,
    "closeVideoClick": true,
    //todo 想让
     height: window.innerHeight - 300, //视频高度
     width:window.innerWidth-300,
    // fitVideoSize: 'auto',
    keyShortcut: false, //禁用所有快捷键
    ignores: ['definition', 'error', 'fullscreen', 'i18n', 'pause', 'loading', 'play', 'time', 'mobile', 'pc', 'poster', 'progress', 'replay', 'volume']
  }
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
  //把播放器存入到store中去做播放暂停以及其他操作
  playerStore.addPlayer(props.id, player);

};

/**
 * 进度位置
 * @param number
 */
const changeProgress = (number) => {
  console.log('当前进度' + number)
  player.seek(number)
}

const playOrPause = () => {
  if (player.paused) {
    player.play()
    isPaused.value = false
  } else {
    player.pause();
    isPaused.value = true
  }
}

</script>

<style scoped>
.mask-filter {
  position: relative;
  width: 100%;
  height: 100%;
  background-color: #282a2a;
  backdrop-filter: blur(40px);
  border: 1px solid #1d2322;
  border-radius: 20px;
}

.v-xg-video {
  position: absolute;
  top: 50%;
  left: 50%;
  right: 50%;
  transform: translate(-50%, -50%);
}

.right-menu, .video-footer {
  position: absolute;
  font-size: 14px;
}

.right-menu {
  right: 10%;
  bottom: 20%;
  width: 50px;
}

.video-footer {
  left: 1%;
  bottom: 10%;
  width: 100%;
}

.custom-button {
  background-color: #8d0000;
  border: 1px solid #1d2322;
  border-radius: 5px;
  color: #fff;
  padding: 2px;
}

.vide-progress {
  position: absolute;
  margin-top: 10px;
  width: 90%;
}
</style>