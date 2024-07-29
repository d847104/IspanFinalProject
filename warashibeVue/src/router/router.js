//0. 引用Vue Router
import { createRouter, createWebHistory } from 'vue-router'

//1. 引用元件
import Home from "../views/Home.vue";
import NotFound from "../views/NotFound.vue";
// import routerBuyer from '../views/buyer/router-buyer.js';
import routerBuyer from '../views/buyer/router-buyer.js';
import routerMembers from '../views/members/router-members.js';
import routerSecure from '@/views/secure/router-secure.js';
import routerProduct from '@/views/product/router-product';
import routerSeller from '@/views/seller/router-seller';
import routerOther from '@/views/other/router-other';
import routerPages from '@/views/pages/router-pages';
// import routerCore2 from '../views/03-core2/router-core.js';
// import routerComp from '../views/04-comp/router-comp.js';
// import routerImpl from '../views/05-impl/router-impl.js';
// import routerAdvance from '../views/08-advance/router-advance.js';
// import routerLibrarys from '../views/09-librarys/router-librarys.js';

// 2. 定義網頁路由
const routes = [
    { name: "home", path: '/', component: Home },
    { name: "notfound-link", path: "/:pathMatch(.*)*", component: NotFound },
    ...routerSecure,
    ...routerPages,
    ...routerProduct,
    ...routerBuyer,
    ...routerSeller,
    ...routerMembers,
    ...routerOther
];

// 3. 產生路由物件
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

// 4. 導出路由物件以利其他元件導入
export default router
