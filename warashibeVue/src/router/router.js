// src/router.js
import { createRouter, createWebHistory } from 'vue-router';


import Home from '../views/Home.vue';
import NotFound from "../views/NotFound.vue";
import ProductList from '../components/ProductList.vue';
import ProductListPage from '../views/pages/ProductListPage.vue';
import routerSecure from '@/views/secure/router-secure';


const routes = [
    { name: "home", path: '/', component: Home },
    { name: "notfound-link", path: "/:pathMatch(.*)*", component: NotFound },
    { name: "product-list-link", path: '/productList', component: ProductList },
    { name: "product-list-page-link", path: '/productListPage', component: ProductListPage },
    ...routerSecure,

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
