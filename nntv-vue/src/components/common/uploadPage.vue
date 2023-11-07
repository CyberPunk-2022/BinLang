<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="发布视频"
      width="500px"
      center
      :close-on-click-modal="false"
      @close="closeDialog"
  >
    <el-form :model="formData" :rules="formDataRule" ref="formRef" label-width="120px">
      <el-form-item label="视频标题" prop="title">
        <el-input v-model="formData.title" placeholder="输入视频"/>
      </el-form-item>
      <el-form-item label="视频类型" prop="categoryId">
        <el-select v-model="formData.categoryId" placeholder="请选择">
          <el-option
              v-for="(item,index) in videoCategoryStore.getVideoCategoryList()"
              :key="item.videoCategoryId"
              :label="item.videoCategoryName"
              :value="item.videoCategoryId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上传视频" prop="videoUrl">
        <el-upload
            class="upload-demo"
            drag
            action="#"
            :auto-upload="true"
            :limit="1"
            :show-file-list="true"
            :before-upload="fileBeforeUpload"
            :multiple="false"
            multiple
        >
          <el-icon class="el-icon--upload">
            <upload-filled/>
          </el-icon>
          <div class="el-upload__text">
            拖拽文件至此 <em>点击上传</em>
          </div>
          <template #tip>
            <div class="el-upload__tip">
              支持MP4/avi/flv格式视频
            </div>
            <div>
              <el-progress v-if="uploadProgress > 0" :percentage="uploadProgress" :stroke-width="15" striped/>
            </div>
          </template>
        </el-upload>
      </el-form-item>

      <el-form-item label="视频简介" prop="videoDesc">
        <el-input
            v-model="formData.videoDesc"
            :rows="3"
            type="textarea"
            placeholder="视频简介"
        />
      </el-form-item>
      <div style="position:absolute;left:39%;">
        <el-button type="primary" @click="submit(formRef)" siz="large">发布视频</el-button>
      </div>
    </el-form>
    <template #footer>

    </template>
  </el-dialog>
</template>

<script setup>
import {UploadFilled} from '@element-plus/icons-vue'


import {reactive, ref} from "vue";
import {useVideoCategoryStore} from "../store/videoCategory.js";
import {storeToRefs} from "pinia";
import * as VideoMangerApi from "../../api/VideoMangerApi.js";
import {isSuccess} from "../../util/http.js";
import {ElLoading, ElMessage} from "element-plus";

let videoCategoryStore = useVideoCategoryStore();

storeToRefs(videoCategoryStore)

const formRef = ref(null)

const uploadProgress = ref(0)


const props = defineProps({
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  }

})


const formData = reactive({
  title: '',//视频title
  videoUrl: '',//视频的上传的URL地址
  videoDesc: '',//视频描述
  categoryId: '', //视频分类
  videoPostUrl: '',
  uploading: 0, //待上传，
})

const formDataRule = {
  title: [{required: true, message: '请输入视频标题', trigger: 'blur'}],
  categoryId: [{required: true, message: '分类必选', trigger: 'blur'}],
  videoUrl: [{required: true, message: '视频未上传成功地址为空', trigger: 'blur'}]
}

/**
 * 提交发布视频
 * @param ref
 */
const submit = (ref) => {
  ref.validate(async (valid) => {
    if (valid) {
      let saveReq = {
        url: formData.videoUrl,
        postUrl: '',
        title: formData.title,
        categoryId: formData.categoryId,
        videoDesc: formData.videoDesc,
        //todo 其他的继续加
      }

      let res = await VideoMangerApi.saveVideo(saveReq);
      if (isSuccess(res)) {
        closeDialog()
      }

    } else {
      return false
    }
  })
}


let emits = defineEmits(["onCloseDialog"]);

const closeDialog = () => {
  emits("onCloseDialog")
}

/**
 * 添加视频的时候自动上传
 * @param file
 * @returns {Promise<void>}
 */
const fileBeforeUpload = async (file) => {

  //检查文件类型，如果file不是avi,mp4,flv不允许上传
  if (!file.type.includes('video')) {
    ElMessage({
      showClose: true,
      message: '请上传正确的视频格式(MP4/avi/flv)',
      center: true,
      type: 'error'
    })
    return;
  }
  console.log('文件类型：', file.type)

  let res = await VideoMangerApi.uploadFileToServer(file, uploadProgressCallback);
  if (isSuccess(res)) {
    formData.videoUrl = res.data.data
    console.log('formData', formData)
  }
  console.log('res', res)
}

/**
 * 上产进度回调
 * @param progressEvent
 */
const uploadProgressCallback = (progressEvent) => {
  const percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
  uploadProgress.value = percentCompleted
  console.log('percentCompleted', percentCompleted)
}

const onPreview = (file) => {
  console.log('onPreview', file)
}

</script>

<style scoped>

:deep(.el-upload-dragger) {
  width: 200px;
}

:deep(.el-upload-list__item-file-name) {
  text-wrap: wrap;
}

</style>