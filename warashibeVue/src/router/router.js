// src/router.js
import { createRouter, createWebHistory } from 'vue-router';
import Swal from 'sweetalert2';
import Home from '@/views/Home.vue';
import Login from '@/views/secure/Login.vue';
import Cart from '@/views/orders/Cart.vue';
import Messenger from '@/views/pages/Messenger.vue';
import Checkout from '@/views/orders/Checkout.vue';

//1. 引用元件
import NotFound from "@/views/NotFound.vue";
import routerBuyer from '@/views/buyer/router-buyer.js';
import routerMembers from '@/views/members/router-members.js';
import routerSecure from '@/views/secure/router-secure.js';
import routerProduct from '@/views/product/router-product';
import routerSeller from '@/views/seller/router-seller';
import routerOther from '@/views/other/router-other';
import routerPages from '@/views/pages/router-pages';
import ChatRoom from '@/components/ChatRoom.vue';
import ChatList from '@/components/ChatList.vue';

// 2. 定義網頁路由
const routes = [
    { name: "home", path: '/', component: Home },
    { name: "notfound-link", path: "/:pathMatch(.*)*", component: NotFound },
    { name: "Login", path: '/login', component: Login },
    { name: 'cart', path: '/cart', component: Cart, meta:{requiresAuth: true} },
    { name: 'checkout', path: '/checkout', component: Checkout},
    { name: 'messenger', path: '/messenger', component: Messenger },
    { name: 'chatlist', path: '/chatlist', component: ChatList },
    { name: 'chatroom', path: '/chatroom', component: ChatRoom, props: true },
    ...routerSecure,
    ...routerPages,
    ...routerProduct,
    ...routerBuyer,
    ...routerSeller,
    ...routerMembers,
    ...routerOther,


];

// 3. 產生路由物件
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

// 添加全局前置守卫
router.beforeEach((to, from, next) => {
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
    if (requiresAuth && sessionStorage.getItem("token") == null) {
        Swal.fire({
            text: "請登入會員",
            icon: "error"
        }).then(function () {
            next({ name: 'Login' });
        })
    } else {
        next();
    }
});

// 4. 導出路由物件以利其他元件導入
export default router
