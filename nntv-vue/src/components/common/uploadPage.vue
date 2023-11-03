<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="登录"
      width="500px"
      center
      @close="loginCloseDialog"
  >

    <template #footer>

    </template>
  </el-dialog>
</template>

<script setup>


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


</script>

<style scoped>

</style>