<template>
    <br><br><br><br><br>
    <h3 style="color:#ECFFFF;">購物訂單管理</h3>
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

        <h5 style="color:#ECFFFF;">訂單狀態: {{ progressText }}</h5>
        <div class="alert alert-secondary" role="alert">
            <div class="container overflow-hidden text-center">
                <div class="row gx-5 align-items-center">
                    <div class="col-md-10">
                        <div class="p-3">
                            <div class="progress" role="progressbar" aria-label="Order progress" aria-valuenow="25"
                                aria-valuemin="0" aria-valuemax="100" style="height: 20px">
                                <div class="progress-bar" :style="{ width: progressWidth }">
                                    {{ progressText }}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="p-3">
                            <a v-if="['newOrder', 'paid', 'confirmed'].includes(selectedOrder?.orderStatus)" href="#"
                                class="btn btn-outline-secondary btn-sm d-block mb-2" @click="cancelOrder">取消訂單</a>
                            <a v-if="['shipped', 'completed'].includes(selectedOrder?.orderStatus)" href="#"
                                class="btn btn-outline-secondary btn-sm d-block mb-2" @click="requestReturn">我要退貨</a>
                            <a v-if="['shipped', 'completed'].includes(selectedOrder?.orderStatus)" href="#"
                                class="btn btn-outline-secondary btn-sm d-block mb-2" @click="completeOrder">完成訂單</a>
                            <a v-if="selectedOrder?.orderStatus === 'returned'" href="#"
                                class="btn btn-secondary btn-sm d-block mb-2"
                                style="pointer-events: none; opacity: 0.6;">退貨中</a>
                            <a v-if="selectedOrder?.orderStatus === 'canceled'" href="#"
                                class="btn btn-secondary btn-sm d-block mb-2"
                                style="pointer-events: none; opacity: 0.6;">已取消</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <h5 style="color:#6C6C6C;">目前沒有訂單</h5>
    </div>

    <!-- 顯示訂單列表 -->
    <div v-if="orders.length > 0">
        <div class="alert alert-light" role="alert" v-for="order in orders" :key="order.orderID">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-10">
                        <h5>HI:{{ BuyerID }}</h5>
                        <h5 style="color:#6C6C6C;">賣家：{{ order.seller }}</h5>
                        <div class="card mb-3">
                            <div class="row g-0" style="max-width: 1200px;">
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
                            <!-- <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2"
                                :title="orderTooltipContent[order.orderID]"
                                @mouseover="loadOrderTooltip(order.orderID)">訂單明細</a> -->
                            <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2"
                                @click="openOrderDetailsModal(order.orderID)">
                                訂單明細
                            </a>
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

    <!-- 訂單明細彈出視窗 -->
    <div v-if="showOrderDetailsModal" class="order-details-modal">
        <div class="order-details-container">
            <h5>訂單明細</h5>
            <pre> {{ orderTooltipContent[currentOrderID] }}</pre>

            <button @click="closeOrderDetailsModal" class="btn btn-outline-secondary btn-sm d-block">關閉</button>
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

// 訂單進度star
const progressWidth = computed(() => {
    if (!selectedOrder.value) return '0%';
    switch (selectedOrder.value.orderStatus) {
        case 'newOrder':
            return '20%';
        case 'paid':
            return '40%';
        case 'confirmed':
            return '60%';
        case 'shipped':
            return '80%';
        case 'completed':
            return '100%';
        default:
            return '0%';
    }
});

const progressText = computed(() => {
    if (!selectedOrder.value) return '未開始';
    switch (selectedOrder.value.orderStatus) {
        case 'newOrder':
            return '新訂單';
        case 'paid':
            return '已付款';
        case 'confirmed':
            return '賣家接單';
        case 'shipped':
            return '已出貨';
        case 'completed':
            return '已到貨';
        case 'returned':
            return '已退貨';
        case 'canceled':
            return '已取消';
        default:
            return '未知狀態';
    }
});
// 訂單進度end

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
        currentOrder.isRated = true;
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

async function cancelOrder() {
    if (!selectedOrder.value) return;

    Swal.fire({
        title: '確定取消訂單？',
        text: "取消後無法恢復訂單",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '確認',
        cancelButtonText: '取消'
    }).then(async (result) => {
        if (result.isConfirmed) {
            console.log("取消訂單的 orderID:", selectedOrder.value.orderID); // 確保 orderID 正確
            try {
                const response = await axiosapi.put(`/private/pages/orders/modify`, {
                    orderID: selectedOrder.value.orderID,
                    orderStatus: 'canceled',
                    seller: selectedOrder.value.seller,
                    delivery: selectedOrder.value.delivery,
                    deliveryFee: selectedOrder.value.deliveryFee,
                    orderTime: selectedOrder.value.orderTime,
                    payMethod: selectedOrder.value.payMethod,
                    total: selectedOrder.value.total,
                    orderProducts: [],
                    orderUpdate: selectedOrder.value.orderUpdate,
                    notificationID: [],
                    secondHand: selectedOrder.value.secondHand,
                    buyer: selectedOrder.value.buyer
                });
                console.log("伺服器響應:", response); // 確保 response 正確
                selectedOrder.value.orderStatus = 'canceled';
                orders.value = orders.value.map(order =>
                    order.orderID === selectedOrder.value.orderID ? { ...order, orderStatus: 'canceled' } : order
                );
                Swal.fire({
                    text: '訂單已取消',
                    icon: 'success'
                });
            } catch (error) {
                console.log("更新訂單狀態時發生錯誤:", error);
                Swal.fire({
                    text: "取消訂單失敗：" + error.message,
                    icon: "error"
                });
            }
        }
    });
}


async function requestReturn() {
    if (!selectedOrder.value) return;
    Swal.fire({
        title: '確定要求退貨？',
        text: "退貨後無法恢復訂單",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '確認',
        cancelButtonText: '取消'
    }).then(async (result) => {
        if (result.isConfirmed) {
            try {
                const updatedOrder = {
                    ...selectedOrder.value,
                    orderID: selectedOrder.value.orderID,
                    orderStatus: 'returned',
                    seller: selectedOrder.value.seller,
                    delivery: selectedOrder.value.delivery,
                    deliveryFee: selectedOrder.value.deliveryFee,
                    orderTime: selectedOrder.value.orderTime,
                    payMethod: selectedOrder.value.payMethod,
                    total: selectedOrder.value.total,
                    orderProducts: [],
                    orderUpdate: selectedOrder.value.orderUpdate,
                    notificationID: [],
                    secondHand: selectedOrder.value.secondHand,
                    buyer: selectedOrder.value.buyer
                };
                await axiosapi.put(`/private/pages/orders/modify`, updatedOrder);
                selectedOrder.value.orderStatus = 'returned';
                orders.value = orders.value.map(order =>
                    order.orderID === selectedOrder.value.orderID ? { ...order, orderStatus: 'returned' } : order
                );
                Swal.fire({
                    text: '退貨申請已提交',
                    icon: 'success'
                });
            } catch (error) {
                console.log("Error updating order status:", error);
                Swal.fire({
                    text: "要求退貨失敗：" + error.message,
                    icon: "error"
                });
            }
        }
    });
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

.button-container {
    display: flex;
    justify-content: center;
    gap: 10px;
}

button {
    margin: 0;
}

/* 訂單明細彈出視窗 */
.order-details-modal {
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

.order-details-container {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
}
</style>
