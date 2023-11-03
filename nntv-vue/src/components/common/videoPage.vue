<template>

  <div>
    <xg-player :id="prop.videoIndex"
               :video-url="videoInfo.url"
               :poster="videoInfo.poster">

      <template v-for="(item, key, i) in slots"
                :key="i" v-slot:[key]="{ record, rowIndex, column }">
        <slot  :name="key"/>
      </template>
    </xg-player>
  </div>


</template>

<script setup>

import { useSlots} from "vue";
import 'xgplayer/dist/index.min.css';
import XgPlayer from "../base/xgPlayer.vue";


let prop = defineProps({
  videoInfo: {
    type: Object,
    required: true
  },
  videoIndex: {
    type: String,
    required: true
  },
  slots: []
})
const slots = useSlots();


</script>

<style scoped>


</style>