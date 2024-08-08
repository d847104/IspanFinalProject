<template>
    <br>
    <div class="row">
        <div class="col-5">
            <h3 style="color:#ECFFFF;">出貨訂單管理</h3>
        </div>
        <div class="col">
            <button type="button" class="btn btn-secondary">批次接單</button>
        </div>
        <div class="col">
            <button type="button" class="btn btn-secondary">批次列印</button>
        </div>
        <div class="col">
            <button type="button" class="btn btn-secondary">批次出貨</button>
        </div>
    </div>

    <div v-if="selectedOrder">
        <div style="display: flex; align-items: center; justify-content: flex-start;">
            <button type="submit" class="btn btn-secondary" style="margin-right: 10px;">全選</button>
            <h4 style="margin: 0;">待出貨</h4>
        </div>
        <!-- 用d-flex右邊就會空一大格，不用的話不會在中間 -->
        <div class="alert alert-dark d-flex align-items-center" role="alert">
            <input type="checkbox" class="form-check-input me-2" id="exampleCheck1">
            <label class="form-check-label" for="exampleCheck1"></label>
            <div class="mb-3 form-check">
                <div class="row gx-3">
                    <div class="col-md-3">
                        <div class="p-3">
                            <h5>訂單編號：</h5>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="p-3">
                            <h5>下單時間：</h5>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="p-3">
                            <h5>出貨期限：</h5>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="p-3">
                            <button type="button" class="btn btn-secondary">確認接單</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 訂單列表 -->
        <div v-if="orders.length > 0">
            <div class="alert alert-light" role="alert" v-for="order in orders" :key="order.orderID">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-md-10">
                            <h5>HI:{{ BuyerID }}</h5>
                            <h5 style="color:#6C6C6C;">買家：</h5>
                            <div class="card mb-3">
                                <div class="row g-0" style="max-width: 1200px;">
                                    <div class="col-md-4 image-container">
                                        <img :src="productImgMap[order.orderID] || '/src/assets/images/no-image.jpg'"
                                            alt="產品圖片" />
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5>訂單編號：</h5><br>
                                            <h5 class="card-title">品名：</h5>
                                            <div class="container text-left">
                                                <br>
                                                <div class="row">
                                                    <div class="col-5">
                                                        商品規格：
                                                    </div>
                                                    <div class="col">
                                                        單價：
                                                    </div>
                                                    <div class="col">
                                                        數量：
                                                    </div>
                                                </div>
                                            </div>
                                            <br><br>
                                            <div class="row">
                                                <div class="col-5">
                                                    <h5>總金額：</h5>
                                                </div>
                                                <div class="col">
                                                    付款方式
                                                </div>
                                                <div class="col">
                                                    配送方式
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="p-4">
                                <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">訂單明細</a>
                                <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">列印出貨單</a>
                                <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">確認出貨</a>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 訂單明細彈出視窗 -->
    <div v-if="showOrderDetailsModal" class="order-details-modal">
        <div class="order-details-container">
            <h5>訂單明細</h5>
            <pre> {{ orderTooltipContent[currentOrderID] }}</pre>
            <button @click="closeOrderDetailsModal"
                class="btn btn-outline-secondary btn-sm d-block close-button">關閉</button>
        </div>
    </div>
    <!-- 已出貨 -->
    <div v-if="selectedOrder">
        <h4>已出貨</h4>
        <div class="alert alert-dark" role="alert">
            <div class="row gx-3">
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>訂單編號：</h5>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>下單時間：</h5>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>出貨日期：</h5>
                    </div>
                </div>
            </div>
        </div>
        <!-- 訂單列表 -->
        <div v-if="orders.length > 0">
            <div class="alert alert-light" role="alert" v-for="order in orders" :key="order.orderID">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-md-10">
                            <h5>HI:{{ BuyerID }}</h5>
                            <h5 style="color:#6C6C6C;">買家：</h5>
                            <div class="card mb-3">
                                <div class="row g-0" style="max-width: 1200px;">
                                    <div class="col-md-4 image-container">
                                        <img :src="productImgMap[order.orderID] || '/src/assets/images/no-image.jpg'"
                                            alt="產品圖片" />
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5>訂單編號：</h5><br>
                                            <h5 class="card-title">品名：</h5>
                                            <div class="container text-left">
                                                <br>
                                                <div class="row">
                                                    <div class="col-5">
                                                        商品規格：
                                                    </div>
                                                    <div class="col">
                                                        單價：
                                                    </div>
                                                    <div class="col">
                                                        數量：
                                                    </div>
                                                </div>
                                            </div>
                                            <br><br>
                                            <div class="row">
                                                <div class="col-5">
                                                    <h5>總金額：</h5>
                                                </div>
                                                <div class="col">
                                                    付款方式
                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="p-4">
                                <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">訂單明細</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 已完成 -->
    <div v-if="selectedOrder">
        <h4>已完成</h4>
        <div class="alert alert-dark" role="alert">
            <div class="row gx-3">
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>訂單編號：</h5>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>下單時間：</h5>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>出貨日期：</h5>
                    </div>
                </div>
            </div>
        </div>
        <!-- 訂單列表 -->
        <div v-if="orders.length > 0">
            <div class="alert alert-light" role="alert" v-for="order in orders" :key="order.orderID">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-md-10">
                            <h5>HI:{{ BuyerID }}</h5>
                            <h5 style="color:#6C6C6C;">買家：</h5>
                            <div class="card mb-3">
                                <div class="row g-0" style="max-width: 1200px;">
                                    <div class="col-md-4 image-container">
                                        <img :src="productImgMap[order.orderID] || '/src/assets/images/no-image.jpg'"
                                            alt="產品圖片" />
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5>訂單編號：</h5><br>
                                            <h5 class="card-title">品名：</h5>
                                            <div class="container text-left">
                                                <br>
                                                <div class="row">
                                                    <div class="col-5">
                                                        商品規格：
                                                    </div>
                                                    <div class="col">
                                                        單價：
                                                    </div>
                                                    <div class="col">
                                                        數量：
                                                    </div>
                                                </div>
                                            </div>
                                            <br><br>
                                            <div class="row">
                                                <div class="col-5">
                                                    <h5>總金額：</h5>
                                                </div>
                                                <div class="col">
                                                    付款方式
                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="p-4">
                                <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">訂單明細</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';
import { format } from 'date-fns';
import axios from 'axios';

let BuyerID = sessionStorage.getItem("memberID");

const apiUrl = import.meta.env.VITE_API_URL;
const selectedOrder = ref(null);
const orders = ref([]);
const productNameMap = ref({}); // 存產品名稱
const productDescriptionMap = ref({});// 存訂單描述
const productImgMap = ref({});
const showRatingModal = ref(false);
const rating = ref(0);
const ratingMessage = ref('');
const currentOrderID = ref(null);
const orderTooltipContent = ref({});
const showOrderDetailsModal = ref(false);

onMounted(() => {
    fetchOrders(BuyerID);

});

async function fetchOrders(buyerID) {
    Swal.fire({
        text: "處理中.....",
        allowOutsideClick: false,
        showConfirmButton: false
    });

    try {
        const response = await axiosapi.get(`/private/pages/orders/buyer/${buyerID}`);
        const fetchedOrders = response.data.list || [];

        if (fetchedOrders.length > 0) {
            // 根據 ORDERID 降冪排序
            orders.value = fetchedOrders.sort((a, b) => b.orderID - a.orderID);
            // 讀取每個訂單的評價狀態
            orders.value.forEach(order => {
                // 檢查本機儲存中的評價狀態
                const isRated = localStorage.getItem(`order-${order.orderID}-rated`) === 'true';
                order.isRated = isRated;
            });
            // 設置選擇的訂單
            selectedOrder.value = orders.value[0]; // 假設選擇最新的一個訂單
            // 查詢每個訂單的產品資料
            for (const order of orders.value) {
                await fetchOrderProducts(order.orderID);
            }
        } else {
            orders.value = [];
            selectedOrder.value = null; // 沒有訂單時的處理
        }
        Swal.close();
    } catch (error) {
        console.log("error", error);
        Swal.fire({
            text: "查詢失敗：" + error.message,
            icon: "error"
        });
    }
}

async function fetchOrderProducts(orderID) {
    try {
        const response = await axiosapi.get(`/private/pages/orderProducts/order/${orderID}`);
        const products = response.data.list || [];
        if (products.length > 0) {
            const productID = products[0].product; // 確保提取到正確的產品 ID
            productNameMap.value[orderID] = products[0].productName || '產品名稱未提供';
            // 查詢產品描述和圖片
            if (productID) {
                await fetchProductDescription(productID, orderID);
                await findProductImg(productID, orderID);
            } else {
                productDescriptionMap.value[orderID] = '產品描述未提供';
                productImgMap.value[orderID] = '產品圖未提供';
            } return productID; // 返回產品 ID
        } else {
            productNameMap.value[orderID] = '產品名稱未提供'; // 沒有產品時的預設值
            productDescriptionMap.value[orderID] = '產品描述未提供';
            productImgMap.value[orderID] = '產品圖未提供';
        }
    } catch (error) {
        console.log("Error fetching products:", error);
        Swal.fire({
            text: "查詢產品資料失敗：" + error.message,
            icon: "error"
        });
    }
}

async function fetchProductDescription(productID, orderID) {
    try {
        const response = await axiosapi.get(`/api/products/${productID}`);
        const productData = response.data || {};
        productDescriptionMap.value[orderID] = productData.description || '產品描述未提供';
    } catch (error) {
        console.log("Error fetching product description:", error);
        Swal.fire({
            text: "查詢產品描述失敗：" + error.message,
            icon: "error"
        });
    }
}

async function findProductImg(productID, orderID) {
    try {
        const response = await axiosapi.get(`/api/productImg/product/${productID}`);
        const imgData = response.data || {};
        if (Array.isArray(imgData.list) && imgData.list.length > 0) {
            const image = imgData.list.find(item => item.product === productID);
            if (image) {
                productImgMap.value[orderID] = `data:image/png;base64,${image.img}`; // 假設圖片格式為 PNG
            } else {
                productImgMap.value[orderID] = '';
            }
        } else {
            productImgMap.value[orderID] = '';
        }
    } catch (error) {
        console.log("Error fetching product image:", error);
        Swal.fire({
            text: "查詢產品圖片失敗：" + error.message,
            icon: "error"
        });
    }
}

function formatDateTime(isoDateString) {
    const date = new Date(isoDateString);
    return format(date, 'yyyy-MM-dd HH:mm:ss');
}


// 顯示訂單明細
function openOrderDetailsModal(orderID) {
    showOrderDetailsModal.value = true;
    currentOrderID.value = orderID;
    loadOrderTooltip(orderID);
}

function closeOrderDetailsModal() {
    showOrderDetailsModal.value = false;
    currentOrderID.value = null;
}

async function loadOrderTooltip(orderID) {
    try {
        const response = await axiosapi.get(`/private/pages/orders/${orderID}`);
        const products = response.data.list || [];
        if (products.length > 0) {
            const productID = products[0].orderID;
            const productResponse = await axiosapi.get(`/private/pages/orderProducts/order/${productID}`);
            console.log('Product Response:', productResponse.data.list); // 確認 productResponse.data 內容
            const productData = productResponse.data.list || [];
            orderTooltipContent.value[orderID] = productData.map(product => `
                商品名稱: ${product.productName || '未提供'}\n
                規格: ${product.specOneName || '未提供'}\n
                數量價格: ${product.quantityPrice || '未提供'}
            `).join('\n');
        } else {
            orderTooltipContent.value[orderID] = '無產品資料';
        }
    } catch (error) {
        console.log("error", error);
        orderTooltipContent.value[orderID] = '無法加載訂單詳細信息';
    }
    console.log('Updated orderTooltipContent (stringified):', JSON.stringify(orderTooltipContent.value));
}


</script>

<style scoped>
/* 調整商品圖大小 */
.image-container {
    display: flex;
    justify-content: center;
    align-items: center;
    max-width: 150px;
    max-height: 150px;
    border: none;
    background-color: transparent;
}

.image-container img {
    max-width: 100%;
    max-height: 100%;
    width: auto;
    height: auto;
    border: none;
    display: block;
}

</style>
