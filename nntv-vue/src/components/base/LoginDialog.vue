<template>
  <Teleport to="body">

    <el-dialog
        v-model="props.show"
        title="登录"
        width="500px"
        @close="props.clickClose"
        center
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
    </el-dialog>
  </Teleport>
</template>

<script setup>
import {reactive, ref} from "vue";
import {getUserInfo, loginOrRegister} from "../../api/Account.js";
import Cookies from "js-cookie";
import {useUserInfoStore} from "../store/userInfo.js";

const userInfoStore = useUserInfoStore()

const formRef = ref(null)

const loginForm = reactive({
  phone: '',
  verifyCode: ''
})


const formDataRule = {
  phone: [{required: true, message: '手机号不能为空', trigger: 'blur'}],
  verifyCode: [{required: true, message: '验证码不能为空', trigger: 'blur'}]
}

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


const submitLogin = (ref) => {
  ref.validate(async (valid) => {
    if (valid) {
      console.log('submit!', loginForm)
      let res = await loginOrRegister(loginForm);
      console.log('res登录', res)
      let token = res.data.data.token;
      // localStorage.setItem("X-Access-Token", token)
      //把值存到cookie中，过期7天
      Cookies.set('X-Access-Token', token, {expires: 7})
      //关闭弹窗
      props.clickClose()
      let userInfoRes = await getUserInfo({})
      console.log('userInfo', userInfoRes)
      userInfoStore.addUserInfo(userInfoRes.data.data)
      console.log('userInfoStore', userInfoStore.getUserInfo())
    } else {
      console.log('error submit!')
      return false
    }
  })
}


</script>

<style scoped>

</style>
