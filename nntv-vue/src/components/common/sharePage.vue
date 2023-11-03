<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="分享页面"
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
  dialogVisible: {
    type: Boolean,
    required: true,
    default: false
  }

})



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