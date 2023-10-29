import { createApp } from 'vue'
import vant from 'vant'
import './style.css'
import App from './App.vue'
import 'vant/lib/index.css';
import { Swipe, SwipeItem } from 'vant';
import '@vant/touch-emulator'
import router from "./components/router/index";
import pinia from "./components/store/index";

import { Popup, Toast, setToastDefaultOptions } from "vant";
setToastDefaultOptions({ duration: 800 }); //修改轻提示默认配置
import "vant/es/toast/style"; //轻提示样式
let app = createApp(App)


import { I18N } from 'xgplayer'
import ZH from 'xgplayer/es/lang/zh-cn'

// 启用中文
I18N.use(ZH)

app.use(vant)
app.use(Swipe)
app.use(SwipeItem)
app.use(pinia)
app.use(Popup)
app.use(Toast)
app.use(router)

app.mount('#app')
