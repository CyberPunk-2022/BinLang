import {defineStore} from "pinia";

export const usePlayerStore = defineStore("xgPlayer", {
    state() {
        return {
            xgPlayer: []
        }
    },

    actions: {
        addPlayer(index, player) {
            let obj = {
                playerId: index,
                player: player,
            }
            this.xgPlayer.push(obj)
        },


        clearPlayer: function () {
            // console.log('销毁播放器', this.xgPlayer)
            // for (let i = 0; i < this.xgPlayer.length; i++) {
            //     let player = this.xgPlayer[i].player;
            //     player.destroy()
            //     let id = player.id;
            //     //移除所有 div id
            //     document.getElementById(id)
            //     var element = document.getElementById(id);
            //     console.log('element',element)
            //     element.parentNode.removeChild(element);
            // }
            //
            if (this.xgPlayer.length > 0) {
                let player = this.xgPlayer[0].player;
                player.destroy(false)
            }
            this.xgPlayer = []
            console.log('销毁播放器111', this.xgPlayer)

        },

        /**
         *
         * @param index 视频索引
         * @param isFirstRender
         * @param space 是否按空格 默认没按
         */
        playVideo(index, isFirstRender, space = false) {
            for (let i = 0; i < this.xgPlayer.length; i++) {
                let player = this.xgPlayer[i].player;

                /**
                 * 解决点击分类之后滑动视频播放顺序混乱的问题
                 */
                if (!isFirstRender && (index === 1 || index === 0) && !space) {
                    console.log('isFirstRender', isFirstRender)
                    if (index === 0) {

                        this.xgPlayer[1].player.pause()
                        this.xgPlayer[0].player.pause()
                        this.xgPlayer[1].player.play()
                    }
                    if (index === 1) {

                        this.xgPlayer[1].player.pause()
                        this.xgPlayer[0].player.pause()
                        this.xgPlayer[0].player.play()
                    }
                    return
                }

                if (i === index) {
                    if (player.paused) {
                        console.log('playId', player)
                        player.play()
                    } else {
                        player.pause()
                    }
                } else {
                    player.pause();
                }
            }
        }
    }
})
