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
                "playerId": id,
                "player": player,
            }
            this.xgPlayer.push(obj)
        },

        playVideo(index) {
            for (let i = 0; i < this.xgPlayer.length; i++) {
                let player = this.xgPlayer[i].player;
                if (index === i) {
                    player.play()
                } else {
                    player.pause()
                    player.reset()
                }
            }

        },
        spacePlayVideo(index) {
            console.log('当前index', index)
            for (let i = 0; i < this.xgPlayer.length; i++) {
                let player = this.xgPlayer[i].player;
                if (index === i) {
                    if (player.paused) {
                        player.play()
                    } else {
                        player.pause()
                    }
                }
            }
        },
        playVideoId(indexId) {
            console.log('当前indexId', indexId)
            for (let i = 0; i < this.xgPlayer.length; i++) {
                let player = this.xgPlayer[i].player;
                let playerId = this.xgPlayer[i].playerId;

                if (playerId === indexId) {
                    console.log('当前playerId', playerId)
                    if (player.paused) {
                        player.play()
                    } else {
                        player.pause()
                    }
                }
            }
        }
    }
})
