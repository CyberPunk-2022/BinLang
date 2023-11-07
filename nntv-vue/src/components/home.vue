<!--程序首页-->
<template>
  <div class="main-content">

    <lay-main-page/>

  </div>

</template>

<script setup>

import {defineComponent, onMounted} from "vue";
import LayMainPage from "./index/LayMainPage.vue";
import {getUserInfo} from "../api/Account.js";
import {useUserInfoStore} from "./store/userInfo.js";
const userInfoStore = useUserInfoStore()
defineComponent({
  name: "index"
})

onMounted(async () => {
  let userInfoRes = await getUserInfo({})
  console.log('userInfo', userInfoRes)
  userInfoStore.addUserInfo(userInfoRes.data.data)
})

</script>

<style scoped>

</style>