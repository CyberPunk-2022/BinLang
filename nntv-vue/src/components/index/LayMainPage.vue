<template>

  <!-- 主窗口分两列显示-->
  <div class="windows-os window-container" style="flex-direction:row">

    <div style="color:white">
      <h1 style="margin-left: 35px;margin-bottom: 50px">logo</h1>
      <a href="#">
        <div class="left-side-menu">
          首页视频
        </div>
      </a>
      <a href="#">
        <div class="left-side-menu">
          体育视频
        </div>
      </a>
      <a href="#">
        <div class="left-side-menu">
          本地视频
        </div>
      </a>
      <a href="#">
        <div class="left-side-menu">
          娱乐视频
        </div>
      </a>
      <a href="#">
        <div class="left-side-menu">
          搞笑视频
        </div>
      </a>
      <a href="#">
        <div class="left-side-menu">
          校园视频
        </div>
      </a>
    </div>

    <div class="right-content-container">
      <div class="header-search">
        <div
            style="margin-right: 20%;border: 2px solid lightgray;padding-left: 10px;padding-right: 10px;border-radius: 10px">
          <div style="color: black;
          border-radius: 10px;display: inline-block">
            <div v-if="!isClickSearch" @click="isClickSearch=true" style="height: 30px;
             width:200px; margin-top: 10px;
             border-radius: 10px;line-height: 40px;color: white">输入想看的视频
            </div>
            <input v-if="isClickSearch" v-model="queryParam.keyword" @blur="onblurInput" style="height: 30px;
             width:200px; margin-top: 10px;
             border-radius: 10px;line-height: 40px;color: black"/>
          </div>
          <div style="display: inline-block">
            <button class="search-btn" @click="clickSearch">搜索
            </button>
          </div>
        </div>
        <a href="#">
          <div class="left-side-menu" @click="()=>{
            uploadDialogVisible = !uploadDialogVisible
          }">
            上传视频
          </div>
        </a>
        <a href="#">
          <div class="left-side-menu" @click="()=>{
             loginDialogVisible = !loginDialogVisible
          }">
            登录
          </div>
        </a>
      </div>
      <div style="margin:10px">
        <van-swipe :vertical="true" style="height: 90vh;"
                   ref="swipeRef"
                   @change="onSwipeChange"
                   :show-indicators="false"
                   :loop="false"
        >
          <!-- 每一次都去创建一个视频播放器-->
          <van-swipe-item
              v-for="(item, i) in playDatas.videos"
              :key="i"
          >
            <video-page
                :video-info="item"
                :video-index="createXiGuaVideoPlayerIndex()"
                ref="videoInfo">
              <!--视频右边竖线icon区域-->
              <template v-slot:right-menu>
                <div class="right-item-icon">
                  <div>
                    <van-image
                        @click="clickAuthorFunc(item)"
                        round
                        width="40px"
                        height="40px"
                        src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
                    >
                    </van-image>
                    <div style="position: absolute;right: 0px;top:50px">
                      <van-icon name="add" size="20"/>
                    </div>
                  </div>
                </div>
                <div class="right-item-icon">
                  <!-- 喜欢-->
                  <van-icon name="like" size="40" badge="9"/>
                </div>
                <!-- 评论-->
                <div class="right-item-icon">
                  <van-icon name="chat" size="40" badge="9" @click="clickCommentFunc(item)"/>
                </div>
                <!-- 收藏-->
                <div class="right-item-icon">
                  <van-icon name="star" size="40" badge="9"/>
                </div>
                <!-- 分享-->
                <div class="right-item-icon">
                  <van-icon name="share" size="40" badge="9" @click="clickShareFunc(item)"/>
                </div>
              </template>
              <template v-slot:video-footer>
                <div class="video-title">
                  @老王视频
                </div>
                <div class="video-desc">
                  阿里云发布通义千问2.0，性能超GPT-3.5，加速追赶GPT-4
                </div>
              </template>
            </video-page>
          </van-swipe-item>

        </van-swipe>
      </div>
    </div>

  </div>
  <!--登录-->
  <login-page :dialog-visible="loginDialogVisible" @onCloseDialog="()=>{
     loginDialogVisible = false
  }" :login-form="loginForm"/>

  <!-- 上传视频-->
  <upload-page :dialog-visible="uploadDialogVisible" @onCloseDialog="()=>{
     uploadDialogVisible = false
  }"/>

  <!--  记着把作品视频Id传过去-->
  <comment-page :dialog-visible="commentDrawerVisible" @onCloseDialog="()=>{
     commentDrawerVisible = false
  }"/>


  <!--  记着把作品视频Id传过去-->
  <author-page :dialog-visible="authorDrawerVisible" @onCloseDialog="()=>{
     authorDrawerVisible = false
  }"/>

  <!-- 分享页面-->
  <share-page :dialog-visible="shareModalVisible" @onCloseDialog="()=>{
    shareModalVisible = false
  }"/>

  <search-page :dialog-visible="visibleModalSearch" @onCloseDialog="()=>{
    visibleModalSearch =false
  }"/>

</template>


<script setup>

import {onMounted, reactive, ref} from "vue";
import VideoPage from "../common/videoPage.vue";
import {usePlayerStore} from "../store/player.js";
import {storeToRefs} from "pinia";
import LoginPage from "../common/loginPage.vue";
import UploadPage from "../common/uploadPage.vue";
import CommentPage from "../common/commentPage.vue";
import AuthorPage from "../common/authorPage.vue";
import SharePage from "../common/sharePage.vue";
import SearchPage from "../common/searchPage.vue";

let playerStore = usePlayerStore();
let {xgPlayer} = storeToRefs(playerStore)


let isClickSearch = ref(false);

// 评论抽屉是否展示
let commentDrawerVisible = ref(false);


// 作者抽屉是否展示
let authorDrawerVisible = ref(false);


// 分享的模态框展示
let shareModalVisible = ref(false);


// 展示搜索框
let visibleModalSearch = ref(false);


let currentSwipeIndex = reactive({
  index: 0
});

const loginDialogVisible = ref(false)

const uploadDialogVisible = ref(false)


const loginForm = ref({
  username: '',
  password: '',
  verifyCode: ''
})


//查询参数
let queryParam = reactive({
  "keyword": ""
})


// 上线滑动refs
let swipeRef = ref(null);

let playDatas = reactive({
  "videos": [
    {
      "url": "//lf3-static.bytednsdoc.com/obj/eden-cn/nupenuvpxnuvo/xgplayer_doc/xgplayer-demo.mp4",
      "poster": "//lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/poster.jpg",
    },
    {
      "url": "https://cesium.com/public/SandcastleSampleData/big-buck-bunny_trailer.mp4",
      "poster": "//lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/poster.jpg",
    },
    {
      "url": "https://media.w3.org/2010/05/sintel/trailer.mp4",
      "poster": "//lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/poster.jpg",
    },
    {
      "url": "http://vjs.zencdn.net/v/oceans.mp4",
      "poster": "//lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/poster.jpg",
    }
  ]
})


onMounted(() => {
  window.addEventListener("keydown", handleKeyUp, true);
})


const onSwipeChange = (index) => {
  console.log('e', index)
  playerStore.playVideo(index)
  currentSwipeIndex.index = index
}


/**
 * 鼠标时间上下监听
 * @param event
 */
const handleKeyUp = (event) => {


  let index = currentSwipeIndex.index;

  let nowIndex = index;

  if (event.key === 'ArrowUp') {// 按下箭头向上键时的处理逻辑
    nowIndex = index - 1;
    if (nowIndex < 0) {
      return
    } else {
      currentSwipeIndex.index = nowIndex;
    }
    swipeRef.value.prev()
    //开始播放
    playerStore.playVideo(nowIndex)
  } else if (event.key === 'ArrowDown') { // 按下箭头向下键时的处理逻辑
    nowIndex = index + 1;
    //获取store中存在的视频播放器数量
    if (nowIndex > playerStore.xgPlayer.length - 1) {
      return
    } else {
      currentSwipeIndex.index = nowIndex;
    }
    swipeRef.value.next()
    //开始播放
    playerStore.playVideo(nowIndex)
  }
  //如果空格
  if (event.key === ' ') {
    console.log('space')
    playerStore.spacePlayVideo(nowIndex)
  }
}

// 每个视频生成一个视频Id
const createXiGuaVideoPlayerIndex = () => {
  function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  }

  return S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4();
}


/**
 * 搜索框聚焦，存在值就一直聚焦
 * 否则失去焦点
 */
const onblurInput = () => {
  if (queryParam.keyword) {
    isClickSearch.value = true;
  } else {
    isClickSearch.value = false;
  }
}

/**
 * 点击评论页面
 * @param item
 */
const clickCommentFunc = (item) => {
  console.log(item)
  commentDrawerVisible.value = true
}

/**
 * 点击作者页面
 * @param item
 */
const clickAuthorFunc = (item) => {
  console.log(item)
  authorDrawerVisible.value = true
}

/**
 * 点击分享页面
 * @param item
 */
const clickShareFunc = (item) => {
  console.log(item)
  shareModalVisible.value = true
}

const clickSearch = () => {
  //todo 执行搜索执行的是searchPage子页面的方法，不要在这里写，这个页面要干净利索
  visibleModalSearch.value = true
}

</script>

<style scoped>
.window-container {
  background-color: #2b2c34;
  background-position: top;
  background-size: cover;
  display: flex;
  flex-direction: row;
  height: 100vh;
  width: 100%;
}


.right-content-container {
  width: 92%;
  color: white;
  display: flex;
  flex-direction: column;
}

.left-side-menu {
  margin-left: 10px;
  margin-top: 5px;
  width: 120px;
  text-align: center;
  height: 50px;
  line-height: 50px;
  color: white;
  padding: 2px;
  border-radius: 5px;
  border: 1px solid #444;
}

.left-side-menu:hover {
  background-color: rebeccapurple;
  box-shadow: 0 0 10px #181c81;
}

.header-search {
  display: flex;
  justify-content: end;
  margin-top: 5px;
  align-content: center;
}

.right-item-icon {
  margin: 20px;
  height: 50px;
  font-size: 10px;
}

.video-title {
  font-size: 20px;
}

.video-desc {
  margin-top: 10px;
  font-size: 15px;
  margin-left: 5px;
}

.search-btn {
  background-color: #1d231e;
  color: white;
  width: 70px;
  border-radius: 10px;
  height: 35px;
  line-height: 25px
}
</style>