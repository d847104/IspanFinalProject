<template>
    <br>
    <h3 style="color:#6C6C6C;">購物訂單管理</h3>
    
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
                        <h5 style="color:#6C6C6C;">賣家：{{ order.seller }}</h5>
                        <div class="card mb-3">
                            <div class="row g-0" style="max-width: 500px;">
                                <div class="col-md-4">
                                    <img src="..." class="img-fluid rounded-start" alt="...">
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <h5>訂單編號：{{ order.orderID }}</h5>
                                        <h5 class="card-title">品名:【{{ productNameMap[order.orderID] || '產品名稱未提供' }}】</h5>
                                        <p class="card-text">商品介紹內容</p>
                                        <br><br>
                                        <p class="card-text"><small class="text-body-secondary">更新時間: {{ formatDateTime(order.orderUpdate) }}</small></p>
                                        <p class="card-text"><small class="text-body-secondary">訂購時間: {{ formatDateTime(order.orderTime) }}</small></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class="p-4">
                            <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">訂單明細</a>
                            <a href="#" class="btn btn-outline-secondary btn-sm d-block mb-2">評價</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <p>目前沒有訂單</p>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';
import { format } from 'date-fns';

const BuyerID = ref(1);
const selectedOrder = ref(null);
const orders = ref([]);
const productNameMap = ref({}); // 用於存儲每個訂單的產品名稱

onMounted(() => {
    fetchOrders(BuyerID.value);
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

        // 假設產品名稱是第一個產品的名稱
        if (products.length > 0) {
            productNameMap.value[orderID] = products[0].productName;
        } else {
            productNameMap.value[orderID] = '產品名稱未提供'; // 沒有產品時的預設值
        }
    } catch (error) {
        console.log("Error fetching products:", error);
        Swal.fire({
            text: "查詢產品資料失敗：" + error.message,
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
</script>

<style>
/* 在此添加自定義樣式 */
</style>
