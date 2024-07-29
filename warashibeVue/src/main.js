import { createApp } from 'vue';
import App from './App.vue';
import router from './router/router';
import FontAwesomeIcon from './plugins/fontawesome.js';

createApp(App)
    .component('font-awesome-icon', FontAwesomeIcon)
    .use(router)
    .mount('#app');