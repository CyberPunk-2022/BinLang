<template>


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
        <div class="left-side-menu" @click="clickLogin">
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
                <van-icon name="chat" size="40" badge="9" @click="clickCommentFunc(playDatas.videos[i])"/>
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

  <!--登录-->
  <login-page :dialog-visible="loginDialogVisible" @onCloseDialog="()=>{
     loginDialogVisible = false
  }"/>

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
import {getPageList} from "../../api/VideoMangerApi.js";
import {ElMessage} from "element-plus";

let playerStore = usePlayerStore();
let videoCategory = useVideoCategoryStore();
storeToRefs(playerStore)
storeToRefs(videoCategory)





let isClickSearch = ref(false);

const searchInputRef = ref(null);

//todo 这些都可以改造成函数式 调用，让代码变得更简洁
// 评论抽屉是否展示
let commentDrawerVisible = ref(false);


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
  "keyword": ""
})


// 上线滑动refs
let swipeRef = ref(null);

//todo 后端获取的数据
let playDatas = reactive({videos: []})


onMounted(async () => {
  // window.addEventListener("keydown", handleKeyUp, true);
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
  //切换完毕需要将索引重置为0
  playDatas.videos = [];
  playDatas.videos = records
  console.log('开始渲染', playDatas.videos)
}

const isPrev = ref(false);


const currentChangeIndex = ref(0);


const onSwipeChange = (index) => {
  console.log('onSwipeChange', index)
  playerStore.playVideo(index,isFirstRender.value)
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

/**
 * 点击搜索
 */
const clickSearch = () => {
  //todo 执行搜索执行的是searchPage子页面的方法，不要在这里写，这个页面要干净利索
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