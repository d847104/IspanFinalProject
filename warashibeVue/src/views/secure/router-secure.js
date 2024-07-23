import Login from "./Login.vue";
import RegisterOne from './RegisterOne.vue';
import RegisterTwo from './RegisterTwo.vue';
import RegisterThree from './RegisterThree.vue';
export default [
    { name: "login-link", path: "/secure/Login", component: Login },
    { name: "register-one-link", path: '/secure/registerOne', component: RegisterOne },
    { name: "register-two-link", path: '/secure/registerTwo', component: RegisterTwo },
    { name: "register-three-link", path: '/secure/registerThree', component: RegisterThree },
]