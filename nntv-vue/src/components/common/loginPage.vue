<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="登录"
      width="500px"
      center
      @close="loginCloseDialog"
  >
    <el-form :model="loginForm" :rules="formDataRule" ref="formRef" label-width="120px">
      <el-form-item label="用户名" prop="phone">
        <el-input v-model="loginForm.phone" placeholder="输入手机号"/>
      </el-form-item>
      <el-form-item label="验证码" prop="verifyCode">
        <div style="display: inline-block">
          <el-input v-model="loginForm.verifyCode" placeholder="输入验证码"/>
        </div>
        <div style="display: inline-block">
          <el-button>获取验证码</el-button>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitLogin(formRef)">登录</el-button>
      </el-form-item>
    </el-form>
    <template #footer>

    </template>
  </el-dialog>
</template>

<script setup>

import {reactive, ref} from "vue";


const formRef = ref(null)


const props = defineProps({

  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  }
})


const loginForm = reactive({
  phone: '',
  verifyCode: ''
})


const formDataRule = {
  phone: [{required: true, message: '手机号不能为空', trigger: 'blur'}],
  verifyCode: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
}

const submitLogin = (ref) => {
  ref.validate((valid) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!')
      return false
    }
  })
  console.log('submitLogin', props.loginForm)
  console.log('loginDialogVisible', props.loginDialogVisible)
}


let emits = defineEmits(["onCloseDialog"]);


const loginCloseDialog = () => {
  emits("onCloseDialog")
}


</script>

<style scoped>

</style>