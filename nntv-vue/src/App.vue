<template>
  <index/>
</template>


<script setup>

import Index from "./components/home.vue";
import {onMounted} from "vue";


onMounted(() => {
  if (document.body.clientWidth > 768) {
    console.log("PC");
    //显示flex显示
    document.querySelector(".nav-bar").style.display = "flex";
    //分散显示
    document.querySelector(".nav-bar").style.justifyContent = "space-around";
    //背景是蓝色
    document.querySelector(".nav-bar").style.backgroundColor = "blue";
    //展示在中间距离两边距离各自是
    document.querySelector(".nav-bar").style.marginLeft = "15%";
    document.querySelector(".nav-bar").style.marginRight = "15%";
    //距离上边是10像素
    document.querySelector(".nav-bar").style.marginTop = "10px";
    //每个元素加上ICON
    document.querySelector(".nav-bar-item").style.textAlign = "center";
    document.querySelector(".nav-bar-item").style.padding = "10px";
    document.querySelector(".nav-bar-item").style.borderRadius = "10px";
    document.querySelector(".nav-bar-item").style.backgroundColor = "white";

  }
})
</script>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
