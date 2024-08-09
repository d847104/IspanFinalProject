<template>
    <div>
        <h3>幫助中心</h3>
        <form @submit.prevent="submitRequest">
            <div class="mb-3">
                <label for="requestType" class="form-label">問題種類</label>
                <select class="form-select" v-model="requestType" @change="loadRelatedOptions">
                    <option value="" disabled selected>請選擇問題種類</option>
                    <option value="buyerOrderIssue">買家訂單問題</option>
                    <option value="sellerOrderIssue">賣家訂單問題</option>
                    <option value="deleteProduct">協助刪除商品</option>
                </select>
            </div>
            <div v-if="requestType === 'buyerOrderIssue'" class="mb-3">
                <label for="relatedOrderId" class="form-label">選擇問題訂單</label>
                <select class="form-select" v-model="relatedOrderId">
                    <option value="" disabled selected>請選擇訂單</option>
                    <option v-for="order in buyerOrders" :key="order.orderID" :value="order.orderID">
                        訂單編號: {{ order.orderID }} - 總金額: {{ order.total }}
                    </option>
                </select>
            </div>
            <div v-if="requestType === 'sellerOrderIssue'" class="mb-3">
                <label for="relatedOrderId" class="form-label">選擇問題訂單</label>
                <select class="form-select" v-model="relatedOrderId">
                    <option value="" disabled selected>請選擇訂單</option>
                    <option v-for="order in sellerOrders" :key="order.orderID" :value="order.orderID">
                        訂單編號: {{ order.orderID }} - 總金額: {{ order.total }}
                    </option>
                </select>
            </div>
            <div v-if="requestType === 'deleteProduct'" class="mb-3">
                <label for="relatedProductId" class="form-label">選擇要刪除的商品</label>
                <select class="form-select" v-model="relatedProductId">
                    <option value="" disabled selected>請選擇商品</option>
                    <option v-for="product in sellerProducts" :key="product.productID" :value="product.productID">
                        商品名稱: {{ product.productName }} - 價格: {{ product.price }}
                    </option>
                </select>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">詳細說明</label>
                <textarea class="form-control" v-model="description" rows="5"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>
</template>

<script setup>
import { ref, watch, inject } from 'vue';
import axiosapi from '@/plugins/axios';

const requestType = ref('');
const relatedOrderId = ref('');
const relatedProductId = ref('');
const description = ref('');

const buyerOrders = ref([]);
const sellerOrders = ref([]);
const sellerProducts = ref([]);

watch(() => requestType.value, loadRelatedOptions);

async function loadRelatedOptions() {
    if (requestType.value === 'buyerOrderIssue') {
        const response = await axiosapi.get(`/private/pages/orders/buyer/${sessionStorage.getItem('memberID')}`);
        buyerOrders.value = response.data.list;
    } else if (requestType.value === 'sellerOrderIssue') {
        const response = await axiosapi.get(`/private/pages/orders/seller/${sessionStorage.getItem('memberID')}`);
        sellerOrders.value = response.data.list;
    } else if (requestType.value === 'deleteProduct') {
        const response = await axiosapi.get(`/api/products/member/${sessionStorage.getItem('memberID')}`);
        sellerProducts.value = response.data.list;
    }
}

async function submitRequest() {
    const memberType = requestType.value === 'buyerOrderIssue' ? 'buyer' : 'seller';

    const request = {
        memberId: user.value.id,
        memberType: memberType,
        requestType: requestType.value,
        relatedOrderId: relatedOrderId.value,
        relatedProductId: relatedProductId.value,
        description: description.value
    };

    try {
        await axiosapi.post('/api/help-center/submit', request);
        alert('您的請求已提交');
    } catch (error) {
        console.error('提交請求失敗', error);
        alert('提交請求失敗');
    }
}
</script>

<style scoped>
/* Add your styles here */
</style>
