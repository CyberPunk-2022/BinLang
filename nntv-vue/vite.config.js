import vue from '@vitejs/plugin-vue';
import Components from 'unplugin-vue-components/vite';
import {ElementPlusResolver, VantResolver} from 'unplugin-vue-components/resolvers';
import AutoImport from 'unplugin-auto-import/vite'

export default {
    plugins: [
        vue(),
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [VantResolver(), ElementPlusResolver()],
        }),
    ],
    server: {
        host: '0.0.0.0',
        port: 3000,
        // 是否开启 https
        https: false,
        proxy: {
            '/api': {
                target: 'http://localhost:8808',
                changeOrigin: true,
                secure: false,
                rewrite: path => path.replace(/^\/api/, ''),
            }
        }
    },
};
