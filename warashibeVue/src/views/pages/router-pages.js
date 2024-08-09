import ProductListPage from './ProductListPage.vue';
import ProductUploadPage from './ProductUploadPage.vue';
import ProductSellerPage from './ProductSellerPage.vue';
export default [
    { name: "product-list-page-link", path: '/pages/productListPage', component: ProductListPage },
    { name: "product-Upload-page-link", path: '/pages/productUploadPage', component: ProductUploadPage, meta: { requiresAuth: true } },
    { name: "product-Seller-page-link", path: '/pages/productSellerPage', component: ProductSellerPage },
]