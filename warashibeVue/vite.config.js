import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import rollupNodePolyFill from 'rollup-plugin-polyfill-node';

export default defineConfig({
  plugins: [
    vue(),
    rollupNodePolyFill(), // 添加这个插件来解决 global 变量问题
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      util: 'rollup-plugin-polyfill-node/polyfills/util', // 必要的 polyfill
    }
  },
  define: {
    global: 'window' // 解决 global is not defined
  }
})
