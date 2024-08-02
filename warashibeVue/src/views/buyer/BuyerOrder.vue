<template>
    <br>
    <h3 style="color:#6C6C6C;">購物訂單管理</h3>
    <!-- 訂單明細按鈕功能、訂單狀態未完成 -->
    <!-- 顯示選擇的訂單 -->
    <div v-if="selectedOrder">
        <div class="alert alert-dark" role="alert">
            <div class="row gx-5">
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>訂單編號：{{ selectedOrder.orderID }}</h5>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>訂單時間：{{ formatDateTime(selectedOrder.orderTime) }}</h5>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3">
                        <h5>總金額：{{ selectedOrder.total }}</h5>
                    </div>
                </div>
            </div>
        </div>

        <h5 style="color:#6C6C6C;">訂單狀態: {{ selectedOrder.orderStatus }}</h5>
        <div class="alert alert-secondary" role="alert">
            <div class="container overflow-hidden text-center">
                <div class="row gx-5 align-items-center">
                    <div class="col-md-10">
                        <div class="p-3">
                            <div class="progress" role="progressbar" aria-label="Example 20px high" aria-valuenow="25"
                                aria-valuemin="0" aria-valuemax="100" style="height: 20px">
                                <div class="progress-bar" :style="{ width: progressWidth }">{{ progressText }}</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="p-3">
                            <a href="#" class="btn btn-secondary btn-sm d-block mb-2">完成訂單</a>
                            <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">取消訂單</a>
                            <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">我要退貨</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <p>目前沒有訂單</p>
    </div>

    <!-- 顯示訂單列表 -->
    <div v-if="orders.length > 0">
        <div class="alert alert-light" role="alert" v-for="order in orders" :key="order.orderID">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-10">
                        <h5>HI:{{ BuyerID }}</h5>
                        <h5 style="color:#6C6C6C;">賣家：{{ order.seller }}</h5>
                        <div class="card mb-3 ">
                            <div class="row g-0 " style="max-width: 1200x;">
                                <div class="col-md-4 image-container">
                                    <img :src="productImgMap[order.orderID] || '/path/to/default/image.png'"
                                        alt="產品圖片" />
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h5>訂單編號：{{ order.orderID }}</h5>
                                        <h5 class="card-title">品名:【{{ productNameMap[order.orderID] || '產品名稱未提供' }}】
                                        </h5>
                                        <p class="card-text">商品介紹內容:{{ productDescriptionMap[order.orderID] || '產品描述未提供'
                                            }}</p>
                                        <br><br>
                                        <p class="card-text"><small class="text-body-secondary">更新時間: {{
                                            formatDateTime(order.orderUpdate) }}</small></p>
                                        <p class="card-text"><small class="text-body-secondary">訂購時間: {{
                                            formatDateTime(order.orderTime) }}</small></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="p-4">
                            <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">訂單明細</a>
                            <a v-if="!order.isRated" href="#" class="btn btn-outline-secondary btn-sm d-block mb-2"
                                @click="openRatingModal(order.orderID)">
                                評價
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <p>目前沒有訂單</p>
    </div>

    <!-- 評價彈出視窗 -->
    <div v-if="showRatingModal" class="rating-modal">
        <div class="rating-container">
            <div class="rating-stars">
                <span v-for="n in 5" :key="n" @mouseover="hoverStar(n)" @mouseout="resetStar" @click="setRating(n)"
                    :class="{ 'active': n <= rating }">
                    ★
                </span>
            </div>
            <textarea v-model="ratingMessage" placeholder="請輸入評價" class="form-control" id="exampleFormControlTextarea1"
                rows="3"></textarea>
            <div class="button-container">
                <button @click="closeRatingModal" class="btn btn-outline-secondary btn-sm d-block">關閉</button>
                <button @click="submitRating" class="btn btn-outline-secondary btn-sm d-block">提交評價</button>
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
const apiUrl = import.meta.env.VITE_API_URL;

let BuyerID = sessionStorage.getItem("memberID");

const selectedOrder = ref(null);
const orders = ref([]);
const productNameMap = ref({}); // 用於存儲每個訂單的產品名稱
const productDescriptionMap = ref({});
const productImgMap = ref({});
const showRatingModal = ref(false);
const rating = ref(0);
const ratingMessage = ref('');
const currentOrderID = ref(null);

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
                // 檢查本地儲存中的評價狀態
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
            productImgMap.value[orderID] = products[0].productName || '產品圖未提供';
            // 設置產品描述
            if (productID) {
                await fetchProductDescription(productID, orderID);
                await findProductImg(productID, orderID);
                orders.value = orders.value.map(order =>
                    order.orderID === orderID ? { ...order, productID } : order
                ); // 更新 orders 中的 productID
            } else {
                productDescriptionMap.value[orderID] = '產品描述未提供';
                productImgMap.value[orderID] = '產品圖未提供';
            }
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
        // 設置產品描述
        productDescriptionMap.value[orderID] = productData.description || '產品描述未提供';
        // 調試語句
        console.log('Product Description for Order ID', orderID, ':', productDescriptionMap.value[orderID]);
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
        // 調試語句，檢查 imgData 的結構
        console.log('Raw image data:', imgData);
        if (Array.isArray(imgData.list) && imgData.list.length > 0) {
            const image = imgData.list.find(item => item.product === productID);
            if (image) {
                // 確保 img 包含正確的 MIME 類型前綴
                productImgMap.value[orderID] = `data:image/png;base64,${image.img}`; // 假設圖片格式為 PNG
                console.log('Found image data for Order ID', orderID, ':', productImgMap.value[orderID]); // 顯示找到的圖片數據
            } else {
                productImgMap.value[orderID] = '';
                console.log('No image found for productID:', productID);
            }
        } else {
            productImgMap.value[orderID] = '';
            console.log('Image data list is empty or invalid:', imgData);
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

// 計算訂單進度
const progressWidth = computed(() => {
    if (!selectedOrder.value) return '0%';
    switch (selectedOrder.value.orderStatus) {
        case '已送出':
            return '25%';
        case '已處理':
            return '50%';
        case '已出貨':
            return '75%';
        case '完成':
            return '100%';
        default:
            return '0%';
    }
});

const progressText = computed(() => {
    if (!selectedOrder.value) return '未開始';
    return selectedOrder.value.orderStatus;
});

/* 評分STAR */
function openRatingModal(orderID) {
    showRatingModal.value = true;
    currentOrderID.value = orderID;
}

function closeRatingModal() {
    showRatingModal.value = false;
    rating.value = 0;
    ratingMessage.value = '';
}

function hoverStar(n) {
    // 可以根據需要顯示星星的 hover 效果
    // 例如，將星星顏色設置為黃色
}

function resetStar() {
    // 恢復星星的顏色
}

function setRating(n) {
    rating.value = n;
}

async function submitRating() {
    if (rating.value === 0) {
        Swal.fire({
            text: '請選擇評分星星',
            icon: 'warning'
        });
        return;
    }

    const currentOrder = orders.value.find(order => order.orderID === currentOrderID.value);
    if (!currentOrder) {
        Swal.fire({
            text: '找不到相關訂單',
            icon: 'error'
        });
        return;
    }

    try {
        await axios.post(`${apiUrl}/api/ranks`, {
            rankDate: new Date().toISOString(),
            ranking: rating.value,
            product: currentOrder.productID, // 確保此字段存在於訂單中
            order: currentOrderID.value,
            member: BuyerID,
            rankmsg: ratingMessage.value
        });
        // 更新訂單狀態為已評價
        currentOrder.isRated = true;

        // 在本地儲存評價狀態，避免重整後顯示評價按鈕
        localStorage.setItem(`order-${currentOrderID.value}-rated`, 'true');
        Swal.fire({
            text: '評價提交成功',
            icon: 'success'
        });

        closeRatingModal();
    } catch (error) {
        console.log("Error submitting rating:", error);
        Swal.fire({
            text: "提交評價失敗：" + error.message,
            icon: "error"
        });
    }
}
/* 評分END */


</script>

<style scoped>
/* 調整商品圖大小 */
.image-container {
    display: flex;
    justify-content: center;
    /* 將圖片居中 */
    align-items: center;
    /* 垂直居中圖片 */
    max-width: 150px;
    max-height: 150px;
    border: none;
    /* 移除容器邊框 */

    background-color: transparent;
    /* 設置背景色為透明 */
}

.image-container img {
    max-width: 100%;
    max-height: 100%;
    width: auto;
    height: auto;
    border: none;
    /* 移除圖片邊框 */
    display: block;
    /* 確保圖片顯示為塊級元素 */
}

/* 評分STAR */
.rating-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.rating-container {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
}

.rating-stars {
    font-size: 24px;
    color: lightgray;
}

.rating-stars .active {
    color: gold;
}

.rating-stars span {
    cursor: pointer;
    margin: 0 5px;
}

textarea {
    width: 100%;
    height: 80px;
    margin: 10px 0;
}

button {
    margin: 5px;
}

/* 評分END */


.button-container {
    display: flex;
    justify-content: center;
    gap: 10px;
    /* 調整按鈕間距 */
}

button {
    margin: 0;
    /* 移除按鈕的外邊距 */
}
</style>
