<template>
  <Teleport to="body">

    <el-drawer
        v-model="props.show"
        title="个人中心"
        width="300px"
        :close-on-click-modal="false"
        @close="props.clickClose"
        center
    >
      <el-form ref="formRef" :model="formData" label-width="80px">
        <el-form-item label="昵称">
          <el-input v-model="formData.nick" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <!--          <el-avatar shape="square" :size="100" :url="formData.avatar"/>-->
          <el-image style="width: 100px; height: 100px;border: 0.5px solid black;border-radius: 50%;"
                    :src="formData.avatar"/>
          <div style="margin-left: 10px">
            <el-upload
                class="upload-demo"
                action="#"
                :auto-upload="true"
                :limit="1"
                :show-file-list="true"
                :before-upload="fileBeforeUpload"
                :multiple="false"
            >
              <template #trigger>
                <el-button type="primary">选择头像</el-button>
              </template>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker
              v-model="formData.birth"
              type="date"
              placeholder="选择生日"
              :size="size"
          />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="formData.gender">
            <el-radio :label="0">男</el-radio>
            <el-radio :label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">保存</el-button>
        </el-form-item>
      </el-form>
      <div><el-button @click="loginOut">注销登录</el-button></div>
    </el-drawer>
  </Teleport>
</template>

<script setup>
import {reactive, ref, onMounted} from "vue";
import {useUserInfoStore} from "../store/userInfo.js";
import {ElMessage} from "element-plus";
import {uploadFileToServer} from "../../api/VideoMangerApi.js";
import {isSuccess} from "../../util/http.js";
import {updateUserInfo} from "../../api/Account.js";
import Cookies from "js-cookie";
import {useRouter} from "vue-router";

const userInfoStore = useUserInfoStore()

const formRef = ref(null)

const router = useRouter()

const formData = reactive({
  nick: '',
  avatar: '',
  gender: '',
  birth: ''
})


onMounted(() => {
  formData.nick = userInfoStore.getUserInfo().nick
  formData.avatar = userInfoStore.getUserInfo().avatar
  formData.gender = parseInt(userInfoStore.getUserInfo().gender)
  formData.birth = userInfoStore.getUserInfo().birth
})

const props = defineProps({
  show: {
    type: Boolean,
    default: false
  },
  clickClose: {
    type: Function,
    default: () => {
    }
  }
})


/**
 * 添加视频的时候自动上传
 * @param file
 * @returns {Promise<void>}
 */
const fileBeforeUpload = async (file) => {

  console.log('file', file)
  //检查文件类型，如果file不是jpg,png不允许上传
  if (!file.type.includes('image')) {
    ElMessage({
      showClose: true,
      message: '请上传正确的视频格式(jpg,png)',
      center: true,
      type: 'error'
    })
  }
  console.log('文件类型：', file.type)

  let res = await uploadFileToServer(file, uploadProgressCallback);
  if (isSuccess(res)) {
    formData.avatar = res.data.data
    console.log('formData', formData)
  }
  console.log('res', res)
}

/**
 * 上产进度回调
 * @param progressEvent
 */
const uploadProgressCallback = (progressEvent) => {
  console.log('progressEvent', progressEvent)
}


/**
 * 更新用户信息
 * @returns {Promise<void>}
 */
const onSubmit = async () => {

  console.log('formData', new Date(formData.birth))
  if (new Date(formData.birth).getTime() > new Date().getTime()) {
    ElMessage({
      showClose: true,
      message: '日期不能大于当前日期',
      center: true,
      type: 'warning'
    })
    return
  }

  let res = await updateUserInfo(formData)
  if (isSuccess(res)) {
    ElMessage({
      showClose: true,
      message: '修改成功！',
      center: true,
      type: 'success'
    })
  }
}
const loginOut =  () => {
  Cookies.remove('X-Access-Token')
  userInfoStore.removeUserInfo();
  props.clickClose()
}
</script>

<style scoped>

</style>
