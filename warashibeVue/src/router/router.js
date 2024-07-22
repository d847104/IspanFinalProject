// src/router.js
import { createRouter, createWebHistory } from 'vue-router';

import Login from '../views/secure/Login.vue';
import Home from '../views/Home.vue';
import ProductList from '../components/ProductList.vue';
import ProductListPage from '../views/pages/ProductListPage.vue';

const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { name: "product-list-link", path: '/productList', component: ProductList },
    { name: "product-list-page-link", path: '/productListPage', component: ProductListPage },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
