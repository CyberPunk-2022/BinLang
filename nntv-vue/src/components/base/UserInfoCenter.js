import {createApp} from 'vue'
import userInfoPage from './UserInfoCenter.vue'

/**
 * 关键函数挂在整个页面上去
 */
const createDialog = (show) => {
    const mountNode = document.createElement('div')
    mountNode.setAttribute('id', 'userInfo-mask-dialog')
    const Instance = createApp(userInfoPage, {
        show: show,
        clickClose: () => {
            Instance.unmount(mountNode);
            document.body.removeChild(mountNode);
        }
    })

    document.body.appendChild(mountNode)
    Instance.mount(mountNode)
}

export default createDialog
