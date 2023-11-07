import {createApp} from 'vue'
import DialogVideoPlay from './DialogVideoPlay.vue'

/**
 * 关键函数挂在整个页面上去
 */
const createDialog = (show, videoUrl, videoUuid, poster, item) => {
    const mountNode = document.createElement('div')
    mountNode.setAttribute('id', 'xg-video-dialog-mask-dialog')
    const Instance = createApp(DialogVideoPlay, {
        show: show,
        videoUri: videoUrl,
        videoUuid: videoUuid,
        poster: poster,
        extraInfo: item,
        clickClose: () => {
            Instance.unmount(mountNode);
            document.body.removeChild(mountNode);
        }
    })

    document.body.appendChild(mountNode)
    Instance.mount(mountNode)
}

export default createDialog
