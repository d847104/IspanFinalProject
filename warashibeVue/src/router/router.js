// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/secure/Login.vue';
import Member from '../views/secure/Member.vue';
import Home from '../views/Home.vue';

const routes = [
    { name: "home", path: '/', component: Home },
    { name: "login", path: '/secure/login', component: Login },
    { name: "member", path: '/secure/member', component: Member },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
