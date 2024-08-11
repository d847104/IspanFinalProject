import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {chunkSizeWarningLimit: 20000},
  base: '/warashibe/',
  server: {
    proxy: {
      "/expressmap": "https://logistics-stage.ecpay.com.tw/Express/map"
    }
  }
})
