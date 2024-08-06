import SellerManageProduct from "./SellerManageProduct.vue"

export default [
    { name: "seller-sellermanageproduct-link", path: "/seller/sellermanageproduct", component: SellerManageProduct, meta: { requiresAuth: true } },
]