//0. 引用Vue Router
import { createRouter, createWebHistory } from 'vue-router'

//1. 引用元件
import Home from "../views/Home.vue";
// import NotFound from "../views/NotFound.vue";
// import routerBuyer from '../views/buyer/router-buyer.js';
import routerBuyer from '../views/buyer/router-buyer.js';
// import routerCore1 from '../views/03-core1/router-core.js';
// import routerCore2 from '../views/03-core2/router-core.js';
// import routerComp from '../views/04-comp/router-comp.js';
// import routerImpl from '../views/05-impl/router-impl.js';
// import routerAdvance from '../views/08-advance/router-advance.js';
// import routerLibrarys from '../views/09-librarys/router-librarys.js';

// 2. 定義網頁路由
const routes = [
    { path: "/", name: "home-link", component: Home },
    // { path: "/:pathMatch(.*)*", name: "notfound-link", component: NotFound },
    ...routerBuyer,
    // ...routerCore1,
    // ...routerCore2,
    // ...routerComp,
    // ...routerImpl,
    // ...routerAdvance,
    // ...routerLibrarys,
]

// 3. 產生路由物件
const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

// 4. 導出路由物件以利其他元件導入
export default router
