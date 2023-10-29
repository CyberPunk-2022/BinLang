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
