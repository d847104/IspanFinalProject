import { createApp } from 'vue';
import App from './App.vue';
import router from './router/router';
import vueInfiniteScroll from 'vue-infinite-scroll';

createApp(App)
    .use(router)
    .use(vueInfiniteScroll)
    .mount('#app');
