import { createApp, ref } from 'vue';
import App from '@/App.vue';
import router from '@/router/router';
import FontAwesomeIcon from '@/plugins/fontawesome.js';
import axiosapi from '@/plugins/axios';
import addCartApi from '@/plugins/cart_add';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'

const isLogin = ref(false);
const cartQt = ref(null);
const loginUserName = ref(null);
const loginUserId = ref(null);
const loginAccount = ref(null);

async function updateCartQt() {
    if (!isLogin.value) {
        if (localStorage.getItem("cartList")) cartQt.value = JSON.parse(localStorage.getItem("cartList")).length;
    } else {
        let response;
        if (localStorage.getItem("cartList")) {
            let cartList = JSON.parse(localStorage.getItem("cartList"));
            let addCartPromises = cartList.map(cart =>
                addCartApi(sessionStorage.getItem("memberID"),
                    cart.product, cart.seller, cart.specOne, cart.specTwo, cart.quantity)
            );
            Promise.all(addCartPromises).then(localStorage.removeItem("cartList")).then(async () => {
                try {
                    response = await axiosapi.get(`/api/cart/member/${sessionStorage.getItem("memberID")}/count`);
                    cartQt.value = response.data.list
                } catch (error) { console.log(error); }
            });
        } else {
            try {
                response = await axiosapi.get(`/api/cart/member/${sessionStorage.getItem("memberID")}/count`);
                cartQt.value = response.data.list
            } catch (error) { console.log(error); }
        }
    }
}

createApp(App)
    .use(router)
    .provide("isLogin", isLogin)
    .provide("cartQt", cartQt)
    .provide("loginUserName", loginUserName)
    .provide("loginUserId", loginUserId)
    .provide("loginAccount", loginAccount)
    .provide("updateCartQt", updateCartQt)
    .component('font-awesome-icon', FontAwesomeIcon)
    .mount('#app');

