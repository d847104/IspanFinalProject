import Login from './Login.vue'
import Member from './Member.vue'

export default [
    { name: "secure-login-link", path: "/secure/login", component: Login },
    { name: "secure-member-link", path: "/secure/member", component: Member },
]