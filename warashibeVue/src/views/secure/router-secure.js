import Login from "./Login.vue";
import RegisterOne from './RegisterOne.vue';
import RegisterTwo from './RegisterTwo.vue';
import RegisterThree from './RegisterThree.vue';
import Member from './Member.vue';
import BrowseHistory from "./BrowseHistory.vue";
import Favorite from "./Favorite.vue";
import ForgetPwd from "./ForgetPwd.vue";
import ResetPwd from "./ResetPwd.vue";
import modifyPwd from "./modifyPwd.vue";

export default [
    { name: "login-link", path: "/secure/Login", component: Login },
    { name: "register-one-link", path: '/secure/registerOne', component: RegisterOne },
    { name: "register-two-link", path: '/secure/registerTwo', component: RegisterTwo },
    { name: "register-three-link", path: '/secure/registerThree', component: RegisterThree },
    { name: "secure-login-link", path: "/secure/login", component: Login },
    { name: "secure-member-link", path: "/secure/member", component: Member, meta: { requiresAuth: true } },
    { name: "secure-browsehistory-link", path: "/secure/browseHistory", component: BrowseHistory },
    { name: "secure-favorite-link", path: "/secure/favorite", component: Favorite },
    { name: "secure-forgetPwd-link", path: "/secure/forgetPwd", component: ForgetPwd },
    { name: "secure-resetPwd-link", path: "/secure/resetPwd", component: ResetPwd },
    { name: "secure-modifyPwd-link", path: "/secure/modifyPwd", component: modifyPwd },
]