import { createApp } from 'vue';
import App from './App.vue';
import router from './router/router';
import FontAwesomeIcon from './plugins/fontawesome.js';
import Uploader from 'vue-media-upload';

createApp(App)
    .use(router)
    .component('font-awesome-icon', FontAwesomeIcon)
    .component('Uploader', Uploader)
    .mount('#app');
