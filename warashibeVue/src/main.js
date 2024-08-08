import { createApp, ref } from 'vue';
import App from './App.vue';
import router from './router/router';
import FontAwesomeIcon from './plugins/fontawesome.js';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'

const isLogin = ref(false);

createApp(App)
    .use(router)
    .provide("isLogin",isLogin)
    .component('font-awesome-icon', FontAwesomeIcon)
    .mount('#app');

