// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/secure/Login.vue';
import Home from '../views/Home.vue';
import SecondHand from '@/views/SecondHand.vue';
import infiniteTest from '@/views/infiniteTest.vue';

const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/secondHand', component: SecondHand},
    { path: '/infiniteTest', component: infiniteTest},
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;