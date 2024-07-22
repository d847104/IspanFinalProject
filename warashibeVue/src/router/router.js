// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/secure/Login.vue';
import Home from '../views/Home.vue';

const routes = [
    { name: "home", path: '/', component: Home },
    { name: "login", path: '/secure', component: Login },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
