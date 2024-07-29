// src/router.js
import { createRouter, createWebHistory } from 'vue-router';


import Home from '../views/Home.vue';
import NotFound from "../views/NotFound.vue";
import routerSecure from '@/views/secure/router-secure';
import routerPages from '@/views/pages/router-pages';


const routes = [
    { name: "home", path: '/', component: Home },
    { name: "notfound-link", path: "/:pathMatch(.*)*", component: NotFound },
    ...routerSecure,
    ...routerPages,

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
