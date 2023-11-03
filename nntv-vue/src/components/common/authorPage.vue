<!--主页视频点击头像进入作者创作的信息-->
<template>
  <el-drawer
      v-model="props.dialogVisible"
      title="作者页面"
      width="500px"
      center
      size="40%"
      @close="loginCloseDialog"
  >
    <template #footer>

    </template>
  </el-drawer>
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