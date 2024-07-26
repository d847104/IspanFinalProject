// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import NotFound from '@/views/NotFound.vue';
import routerProduct from '@/views/product/router-product';
import routerSecure from '@/views/secure/router-secure';
import routerBuyer from '@/views/buyer/router-buyer';
import routerSeller from '@/views/seller/router-seller';
import routerOther from '@/views/other/router-other';

const routes = [
    { name: "home", path: '/', component: Home },
    { name: "notfound-link", path: "/:pathMatch(.*)*", component: NotFound },
    ...routerSecure,
    ...routerProduct,
    ...routerBuyer,
    ...routerSeller,
    ...routerOther
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
