// index.js
import {createRouter, createWebHashHistory} from "vue-router";

const routes = [
    {
        path: "/",
        name: "home",
        component: () => import('../home.vue'),
    },
    {
        path: "/play",
        name: "play",
        component: () => import('../common/SinglePlayerPage.vue'),
        props: true
    }
];
const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
export default router;
