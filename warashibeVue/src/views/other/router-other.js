import Notification from "./Notification.vue"
import HelpcCenter from "./HelpcCenter.vue"
import ContactMessage from "./ContactMessage.vue"

export default [
    { name: "other-notification-link", path: "/other/notification", component: Notification, meta: { requiresAuth: true } },
    { name: "other-helpcenter-link", path: "/other/helpcenter", component: HelpcCenter, meta: { requiresAuth: true } },
    { name: "other-contectmessage-link", path: "/other/contectmessage", component: ContactMessage, meta: { requiresAuth: true } },
]
