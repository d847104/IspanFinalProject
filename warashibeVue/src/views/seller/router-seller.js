import SellerManageProduct from "./SellerManageProduct.vue"
import SellerOrder from "./SellerOrder.vue"

export default [
    { name: "seller-sellermanageproduct-link", path: "/seller/sellermanageproduct", component: SellerManageProduct, meta: { requiresAuth: true } },
    { name: "seller-sellerorder-link", path: "/seller/sellerorder", component: SellerOrder, meta: { requiresAuth: true }}



]