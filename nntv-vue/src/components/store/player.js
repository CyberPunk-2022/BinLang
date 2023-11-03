import {defineStore} from "pinia";

export const usePlayerStore = defineStore("xgPlayer", {
    state() {
        return {
            xgPlayer: []
        }
    },

    actions: {
        addPlayer(id, player) {
            let obj = {
                "playid": id,
                "player": player
            }
            this.xgPlayer.push(obj)
        },

        //键盘切换到最后一个
        canPlay(index) {
            if (index === this.xgPlayer.length - 1) {
                return false;
            }
            return true;
        },

        playVideo(index) {
            for (let i = 0; i < this.xgPlayer.length; i++) {
                let player = this.xgPlayer[i].player;
                console.log('this.xgPlayer[i]', player)
                if (index === i) {
                    player.play()
                } else {
                    player.pause()
                    player.reset()
                }
            }

        }
    }
})
