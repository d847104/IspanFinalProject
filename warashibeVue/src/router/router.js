// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/secure/Login.vue';
import Home from '../views/Home.vue';
import SecondHand from '@/views/SecondHand.vue';

const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/secondHand', component: SecondHand},
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;