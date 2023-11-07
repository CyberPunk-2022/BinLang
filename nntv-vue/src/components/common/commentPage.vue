<template>
  <el-drawer
      v-model="props.dialogVisible"
      title="评论页面"
      width="500px"
      center
      @close="loginCloseDialog"
  >

    <div style="margin-bottom: 200px">
      <div class="comment-box" v-for="item in commentList">
        <div class="comment-item comment-avatar" style="display: inline-block">{{ item.commentName }}</div>
        <label>:</label>
        <div class="comment-item comment-content" style="display: inline-block">
          <p style="word-wrap: break-word;">{{ item.content }}</p>
        </div>
        <div style="font-size: 10px;color:gray">发表时间 <label>:</label>{{ item.createTime }}</div>
        <!--        <div>-->
        <!--          <div style="margin-left: 10px;padding:10px">-->
        <!--            <div style="font-size: 14px" v-for="item in item.replayInfo">-->
        <!--              {{ item }}-->
        <!--            </div>-->
        <!--            <div style="margin-bottom: 30px">-->
        <!--              <el-button size="mini" type="primary" style="float:right;margin-left: 10px;margin-right: 10px">回复-->
        <!--              </el-button>-->

        <!--            </div>-->
        <!--          </div>-->

        <!--        </div>-->
      </div>
    </div>

    <div style="position: absolute;bottom:10px;right: 0">
      <el-input
          v-model="commentInfo.content"
          style="width:300px"
          :rows="3"
          type="textarea"
          placeholder="输入评论"
      />
      <el-button type="primary" style="margin-left: 10px;margin-right: 10px" @click="submit">发表评论</el-button>
    </div>
    <template #footer>

    </template>
  </el-drawer>
</template>

<script setup>
import {UploadFilled} from '@element-plus/icons-vue'


import {reactive, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {getPageList, submitComment} from "../../api/CommentApi.js";
import {isSuccess} from "../../util/http.js";

const props = defineProps({
  videoInfo: {
    type: Object,
    required: false
  },
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  }

})

onMounted(() => {
  console.log('评论视频', props.videoInfo)
})


let commentInfo = reactive({
  videoId: '', //评论的视频id
  content: '' //评论的内容
})


let videoId = ref('')

let commentList = reactive([])


let emits = defineEmits(["onCloseDialog"]);


const loginCloseDialog = () => {
  emits("onCloseDialog")
}

const submit = async () => {
  commentInfo.videoId = videoId.value
  console.log('评论内容', commentInfo)
  if (!commentInfo.content) {
    ElMessage({
      showClose: true,
      message: '请输入评论信息',
      center: true,
      type: 'waring'
    })
    return
  }
  let res = await submitComment(commentInfo)

  if (isSuccess(res)) {
    ElMessage({
      showClose: true,
      message: '发表成功',
      center: true,
      type: 'success'
    })
    commentInfo.content = ''
    await getCommentList()
  }
}


const getCommentList = async () => {

  let param = {
    videoId: videoId.value
  }

  let res = await getPageList(param)
  console.log('查询评论列表', res)
  console.log('查询评论列表', res)
  if (isSuccess(res)) {
    commentList = res.data.data.records
  }
}


const clickCommentPage = async (item) => {
  console.log('打开查询评论列表', item.videoId)
  videoId.value = item.videoId
  await getCommentList()
}

defineExpose({clickCommentPage})

</script>

<style scoped>

:deep(.el-upload-dragger) {
  width: 200px;
}

:deep(.el-upload-list__item-file-name) {
  text-wrap: wrap;
}

.comment-box {
  border: 1px solid #181c81;
  border-radius: 10px;
  padding: 10px;
  margin-top: 10px;

  .comment-avatar {
    height: 30px;
    line-height: 30px;
    text-align: center;
    border-radius: 30px;
    font-size: 15px;
  }

  .comment-item {
    color: black;
    margin: 2px;
  }

  .comment-content {
    font-size: 12px;
    word-wrap: break-word;
  }
}

</style>