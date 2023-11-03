<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="登录"
      width="500px"
      center
      @close="loginCloseDialog"
  >
    <el-form :model="props.loginForm" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="props.loginForm.username" placeholder="输入手机号"/>
      </el-form-item>
      <el-form-item label="用户名">
        <div style="display: inline-block">
          <el-input v-model="props.loginForm.password" placeholder="输入验证码"/>
        </div>
        <div style="display: inline-block">
          <el-button>获取验证码</el-button>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitLogin">登录</el-button>
      </el-form-item>
    </el-form>
    <template #footer>

    </template>
  </el-dialog>
</template>

<script setup>

import {closeDialog} from "vant";

const props = defineProps({
  loginForm: {
    type: Object,
    required: true,
  },
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  }

})

const submitLogin = () => {
  console.log('submitLogin', props.loginForm)
  console.log('loginDialogVisible', props.loginDialogVisible)
}


let emits = defineEmits(["onCloseDialog"]);


const loginCloseDialog = () => {
  emits("onCloseDialog")
}

//emit closeDialog方法


// const showLoginForm = () => {
//   console.log('props.loginDialogVisible',props.loginDialogVisible)
//   return props.loginDialogVisible;
// }
//
//
// defineExpose({
//   showLoginForm
// });

</script>

<style scoped>

</style>