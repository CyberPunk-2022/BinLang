<template>

  <!-- 主窗口分两列显示-->
  <div class="windows-os window-container" style="flex-direction:row">

    <div style="color:white">
      <h1 style="margin-left: 35px;margin-bottom: 50px">logo</h1>
      <a href="#" @click="goCategoryVideo(null)">
        <div class="left-side-menu">
          推荐
        </div>
      </a>
      <a href="#" v-for="(item, i) in videoCategory.getVideoCategoryList()" @click="goCategoryVideo(item)">
        <div class="left-side-menu">
          {{ item.videoCategoryName }}
        </div>
      </a>
    </div>

    <div class="right-content-container">
      <div class="header-search">
        <div class="input-search">
          <div class="input-search-mask">
            <div v-if="!isClickSearch" @click="clickSearchFocus" style="height: 30px;width:200px;
             border-radius: 10px;;color: white">输入想看的视频
            </div>
            <input style="border-radius: 20px;height: 35px" v-if="isClickSearch" ref="searchInputRef"
                   v-model="queryParam.keyword" @blur="onblurInput"/>
          </div>
          <div class="search-submit">
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
          <div v-if="!userInfoStore.getUserInfo().id" class="left-side-menu" @click="clickLogin">
            登录
          </div>
          <div v-if="userInfoStore.getUserInfo().id" class="left-side-menu" @click="clickUserCenter">个人中心
          </div>
        </a>
      </div>
      <div style="margin:10px">
        <van-swipe :vertical="true" style="height: 90vh;"
                   ref="swipeRef"
                   @change="onSwipeChange"
                   :show-indicators="false"
                   :loop="false" lazy-render
        >
          <!-- 每一次都去创建一个视频播放器-->
          <van-swipe-item
              v-for="(item, i) in playDatas.videos"
              :key="playDatas.videos[i].videoUuid">
            <video-page
                :video-info="playDatas.videos[i]"
                :video-index="playDatas.videos[i].videoUuid"
                ref="videoInfo">
              <!--视频右边竖线icon区域-->
              <template v-slot:right-menu>
                <div class="right-item-icon">
                  <div>
                    <van-image
                        @click="clickAuthorFunc(playDatas.videos[i])"
                        round
                        width="40px"
                        height="40px"
                        :src="playDatas.videos[i].userInfo.avatar"
                    >
                    </van-image>
                    <div style="position: absolute;right: 0px;top:50px">
                      <van-icon name="add" size="20"/>
                    </div>
                  </div>
                </div>
                <div class="right-item-icon">
                  <!-- 喜欢-->
                  <van-icon name="like" size="40" :badge="playDatas.videos[i].stars"
                            @click="clickLike(playDatas.videos[i])"/>
                </div>
                <!-- 评论-->
                <div class="right-item-icon">
                  <van-icon name="chat" size="40" :badge="playDatas.videos[i].commentNum"
                            @click="clickCommentFunc(playDatas.videos[i])"/>
                </div>
                <!-- 收藏-->
                <div class="right-item-icon">
                  <van-icon name="star" size="40" badge="9"/>
                </div>
                <!-- 分享-->
                <div class="right-item-icon">
                  <van-icon name="share" size="40" badge="9" @click="clickShareFunc(playDatas.videos[i])"/>
                </div>
              </template>
              <template v-slot:video-footer>
                <div class="video-title">
                  @{{ playDatas.videos[i].authorName }}
                </div>
                <div class="video-desc">
                  {{ playDatas.videos[i].title }}
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
  }"/>

  <!-- 上传视频-->
  <upload-page :dialog-visible="uploadDialogVisible" @onCloseDialog="()=>{
     uploadDialogVisible = false
  }"/>

  <!--  记着把作品视频Id传过去-->
  <comment-page ref="commentPageRef" :dialog-visible="commentDrawerVisible" :video-Info="videoItemInfo" @onCloseDialog="()=>{
     commentDrawerVisible = false
  }"/>


  <!--  记着把作品视频Id传过去-->
  <author-page ref="authorPageRef"
               :dialog-visible="authorDrawerVisible"
               :extra-info="videoItemInfo"
               :open="()=>{
                  authorDrawerVisible = true
               }"
               @onCloseDialog="()=>{
     authorDrawerVisible = false
  }"/>

  <!-- 分享页面-->
  <share-page
      :dialog-visible="shareModalVisible"
      @onCloseDialog="()=>{
    shareModalVisible = false
  }"/>

  <search-page :dialog-visible="visibleModalSearch" ref="searchPageRef" @onCloseDialog="()=>{
    visibleModalSearch =false
  }"/>

</template>


<script setup>

import {getCurrentInstance, onMounted, reactive, ref, watch} from "vue";
import VideoPage from "../common/videoPage.vue";
import {usePlayerStore} from "../store/player.js";
import {storeToRefs} from "pinia";
import LoginPage from "../common/loginPage.vue";
import UploadPage from "../common/uploadPage.vue";
import CommentPage from "../common/commentPage.vue";
import AuthorPage from "../common/authorPage.vue";
import SharePage from "../common/sharePage.vue";
import SearchPage from "../common/searchPage.vue";
import {useVideoCategoryStore} from "../store/videoCategory.js";
import DialogLogin from "../base/LoginDialog.js";
import {getPageList, like} from "../../api/VideoMangerApi.js";
import {ElLoading, ElMessage} from "element-plus";
import Cookies from "js-cookie";
import {useUserInfoStore} from "../store/userInfo.js";


import UserInfoPage from "../base/UserInfoCenter.js";

const userInfoStore = useUserInfoStore()
let playerStore = usePlayerStore();
let videoCategory = useVideoCategoryStore();
storeToRefs(playerStore)
storeToRefs(videoCategory)


let isClickSearch = ref(false);

const searchInputRef = ref(null);

const searchPageRef = ref(null);

const commentPageRef = ref(null);

const authorPageRef = ref(null);

//todo 这些都可以改造成函数式 调用，让代码变得更简洁
// 评论抽屉是否展示
let commentDrawerVisible = ref(false);

// 评论的视频信息
let videoItemInfo = reactive({});


// 作者抽屉是否展示
let authorDrawerVisible = ref(false);


// 分享的模态框展示
let shareModalVisible = ref(false);


// 展示搜索框
let visibleModalSearch = ref(false);


const loginDialogVisible = ref(false)

const uploadDialogVisible = ref(false)

/**
 * 非常重要，因为首次顺序渲染，第二次逆序渲染导致空格播放最后一个（首次进入页面）
 * @type {Ref<UnwrapRef<boolean>>}
 */
const isFirstRender = ref(true)


//查询参数
let queryParam = reactive({
  keyword: ""
})


// 上线滑动refs
let swipeRef = ref(null);

//todo 后端获取的数据
let playDatas = reactive({videos: []})


onMounted(async () => {
  window.addEventListener("keydown", handleKeyUp, true);
  await handleSearch({categoryId: null, keyword: null})
})


/**
 *  查询视频列表
 * @param option
 * @returns {Promise<void>}
 */
const handleSearch = async (option = {}) => {
  let {categoryId, keyword} = option;

  let param = {
    categoryId: categoryId,
    keyword: keyword
  }
  let res = await getPageList(param)
  let records = res.data.data.records;
  //todo 无视频的时候不渲染页面
  if (records.length === 0) {
    ElMessage({
      showClose: true,
      message: '无相关类型视频',
      center: true,
      type: 'warning'
    })
    return;
  }
  playerStore.clearPlayer()
  //切换完毕需要将索引重置为0
  playDatas.videos = [];
  playDatas.videos = records
  console.log('开始渲染', playDatas.videos)
}

const isPrev = ref(false);


const currentChangeIndex = ref(0);


const onSwipeChange = (index) => {
  console.log('onSwipeChange', index)
  playerStore.playVideo(index, isFirstRender.value)
  currentChangeIndex.value = index
}


/**
 * 鼠标时间上下监听
 * @param event
 */
const handleKeyUp = (event) => {
  let swipeRefValue = swipeRef.value;
  if (event.key === 'ArrowUp') {// 按下箭头向上键时的处理逻辑
    swipeRefValue.prev()
    isPrev.value = false
  } else if (event.key === 'ArrowDown') { // 按下箭头向下键时的处理逻辑
    swipeRefValue.next()
    isPrev.value = true
  }
  //如果空格
  if (event.key === ' ') {
    console.log('空格', currentChangeIndex.value)
    playerStore.playVideo(currentChangeIndex.value, isFirstRender.value)
  }
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
const clickCommentFunc = async (item) => {
  console.log('item', item)
  await commentPageRef.value.clickCommentPage(item)
  commentDrawerVisible.value = true
}

/**
 * 点击作者页面
 * @param item
 */
const clickAuthorFunc = async (item) => {
  let service = ElLoading.service({fullscreen: false, text: '正在加载作者主页', background: 'rgba(0, 0, 0, 0)'});

  console.log(item)
  await authorPageRef.value.openAuthorPage(item)
  authorDrawerVisible.value = true
  service.close()
}

/**
 * 点击分享页面
 * @param item
 */
const clickShareFunc = (item) => {
  console.log(item)
  shareModalVisible.value = true
}

/**
 * 点击搜索
 */
const clickSearch = async () => {
  //todo 执行搜索执行的是searchPage子页面的方法，不要在这里写，这个页面要干净利索
  let searchRes = await searchPageRef.value.clickSearch(queryParam);
  if (searchRes.length === 0) {
    ElMessage({
      showClose: true,
      message: '没搜索到结果',
      center: true,
      type: 'warning'
    })
    return
  }
  visibleModalSearch.value = true

}

/**
 * 点击右侧分类
 * @param item
 */
const goCategoryVideo = (item) => {
  let option = {
    categoryId: item ? item.videoCategoryId : ''
  }
  handleSearch(option)
  isFirstRender.value = false;
  console.log('切换到第一个')
  swipeRef.value.swipeTo(0)
  console.log('goCategoryVideo', playerStore.xgPlayer)
}

const clickSearchFocus = () => {
  isClickSearch.value = true
  console.log('点击了搜索框', searchInputRef)
}

/**
 * 函数式弹出登录框
 */
const clickLogin = () => {
  DialogLogin(true);
}
/**
 * 函数式弹出登录框
 */
const clickUserCenter = () => {
  UserInfoPage(true);
}


const clickLike = async (item) => {

  let param = {
    videoId: item.videoId,
  }

  let res = await like(param);
  item.stars = res.data.data.stars
  console.log('resresres', res)
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

  .input-search {
    position: absolute;
    border: 2px solid lightgray;
    padding: 5px;
    border-radius: 10px;
    width: 280px;
    left: 50%;
    right: 50%;
    transform: translate(-50%, -50%);
    top: 40px
  }

  .input-search-mask {
    color: black;
    border-radius: 10px;
    display: inline-block
  }

  .search-submit {
    display: inline-block
  }
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