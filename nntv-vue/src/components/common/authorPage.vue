<!--主页视频点击头像进入作者创作的信息-->
<template>
  <el-drawer
      v-model="props.dialogVisible"
      :title="'作者页面-'"
      width="500px"
      center
      size="40%"
      @close="loginCloseDialog"
  >
    <div class="infinite-list-wrapper" style="overflow: auto;">
      <div style="margin: 10px;font-size: 12px">
        <div style="margin: 10px;font-size: 20px;font-weight: bold;color: black">头像</div>
        <el-divider />
        <el-image :src=" records[0].userInfo.avatar"
                  style="height: 100px;width:100px;border: 1px solid black;border-radius: 20px"/>
      </div>
      <el-divider />
      <div style="margin: 10px;font-size: 20px;font-weight: bold;color: black">创作作者:{{ records[0].userInfo.nick }}</div>
      <el-divider />
      <div class="video-list" v-for="(item,index) in records " style="margin: 10px">
        <div @click="goPlay(item)">
          <el-image :src="item.postUrl"> {{ item.title }}</el-image>
          <div style="padding: 10px;background-color: #f3f0f0;color: #1d2322;font-size: 12px"> {{ item.title }}</div>
          <div style="padding: 10px;background-color: #1d2322;color: white;font-size: 12px">作者：{{
              item.authorName
            }}
          </div>
        </div>
      </div>
    </div>


    <template #footer>

    </template>
  </el-drawer>
</template>

<script setup>


import {reactive, onMounted} from "vue";
import {useUserInfoStore} from "../store/userInfo.js";
import {getAuthorPageList} from "../../api/VideoMangerApi.js";
import DialogVideoPlay from "../base/DialogVideoPlay.js";

const userInfoStore = useUserInfoStore()


let records = reactive([])

const props = defineProps({
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  },
  extraInfo: {
    type: Object,
    required: true
  },
  open: {
    type: Function,
    default: () => {
    }
  }
})


let openAuthorPageInfo = reactive({})

onMounted(() => {
  console.log('extraInfo', props.extraInfo)
})





const goPlay = (item) => {
  DialogVideoPlay(true,item.url,item.videoUuid,item.poster,item)
}



let emits = defineEmits(["onCloseDialog"]);


const loginCloseDialog = () => {
  emits("onCloseDialog")
}


const openAuthorPage = async (item) => {
  console.log('openAuthorPage', item)
  openAuthorPageInfo = item
  let param = {
    authorId: item.authorId
  }
  let res = await getAuthorPageList(param)
  records = res.data.data.records
  console.log('getAuthorPageListres', res.data.data.records)
  props.open()
}

defineExpose({
  openAuthorPage
})


</script>

<style scoped>

:deep(.el-upload-dragger) {
  width: 200px;
}

:deep(.el-upload-list__item-file-name) {
  text-wrap: wrap;
}

.infinite-list-wrapper {
  margin: 0 auto; /* 使元素水平居中 */
}

.video-list {

  border: 0.2px solid #4d5856;
  width: 25.33%;
  padding: 10px;
  border-radius: 10px;
  float: left;
}

</style>