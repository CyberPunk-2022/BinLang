<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="发布视频"
      width="500px"
      center
      @close="loginCloseDialog"
  >
    <el-form :model="formData" label-width="120px">
      <el-form-item label="视频标题">
        <el-input v-model="formData.title" placeholder="输入视频"/>
      </el-form-item>
      <el-form-item label="视频类型">
        <el-input v-model="formData.category" placeholder="输入视频类型"/>
      </el-form-item>
      <el-form-item label="上传视频">
        <el-upload
            class="upload-demo"
            drag
            :limit="1"
            action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
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
          </template>
        </el-upload>
      </el-form-item>
      <div style="position:absolute;left:39%;">
        <el-button type="primary" @click="submit" siz="large">发布视频</el-button>

      </div>
    </el-form>
    <template #footer>

    </template>
  </el-dialog>
</template>

<script setup>
import {UploadFilled} from '@element-plus/icons-vue'


import {reactive} from "vue";

const props = defineProps({
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  }

})


const formData = reactive({})


const submitLogin = () => {
  console.log('submitLogin', formData)
  console.log('loginDialogVisible', props.loginDialogVisible)
}


let emits = defineEmits(["onCloseDialog"]);


const loginCloseDialog = () => {
  emits("onCloseDialog")
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