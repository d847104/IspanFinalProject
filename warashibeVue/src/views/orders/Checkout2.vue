<template>
    <!-- 結帳商品未完成、總金額欄位、收件資料等要寫進資料庫(便利商店分店選擇還要寫API，以宅配DEMO) -->
    <h3 style="color: azure;">結帳</h3>
    <!-- 顯示訂單列表 -->
    <div>
        <div class="alert alert-light" role="alert">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-12">
                        <h5>HI:{{ BuyerID }}</h5>
                        <div class="card mb-3">
                            <div class="row g-0">
                                <div class="col-md-4 image-container">
                                    <img src="..." alt="..." />
                                    <!-- <img :src="productImgMap[order.orderID] || '/path/to/default/image.png'"
                                        alt="產品圖片" />     -->
                                </div>
                                <div class="col-md-10">
                                    <div class="card-body ">
                                        <!-- <h5 style="color:#6C6C6C;">賣家：{{ order.seller }}</h5> -->
                                        <p class="card-title" style="color:#6C6C6C;">賣家：</p><br>
                                        <!-- <h5>訂單編號：{{ order.orderID }}</h5> -->
                                        <h5 class="card-text">品名: </h5>
                                        <div class="container text-left">
                                            <br>
                                            <div class="row">
                                                <div class="col-5">
                                                    商品規格:
                                                </div>
                                                <div class="col">
                                                    單價:
                                                </div>
                                                <div class="col">
                                                    數量:
                                                </div>
                                                <div class="col">
                                                    小計:
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="margin-left: 50px;">
                <div class="form-check">
                    <h5> 運送方式:</h5>
                    <div v-for="delivery in sortedDeliveries" :key="delivery.deliveryID" class="form-check">
                        <div class="row">
                            <div class="col-2">
                                <input class="form-check-input" type="radio" name="deliveryRadio"
                                    :id="'deliveryRadio' + delivery.deliveryID" :value="delivery.deliveryID">
                                <label class="form-check-label" :for="'deliveryRadio' + delivery.deliveryID">
                                    {{ delivery.delivery }}
                                </label>
                            </div>
                            <div class="col">
                                <p>運費: {{ delivery.deliveryFee }} </p>
                            </div>
                        </div>
                    </div>

                    <br>
                    <div class="col-md-2">
                        <label class="form-label">收件人姓名</label>
                        <input type="text" class="form-control">
                    </div><br>
                    <div class="col-md-2">
                        <label class="form-label">聯絡手機</label>
                        <input type="text" class="form-control">
                    </div><br>
                    <div class="col-md-5">
                        <label class="form-label">收件地址</label>
                        <input type="text" class="form-control">
                    </div>
                    <br>
                    <div class="col-md-3">
                        <label class="form-label">備註</label>
                        <input type="text" class="form-control">
                    </div>
                    <br><br>
                    <form class="row g-3">
                        <div class="form-check col-md-3">
                            <h5> 付款方式:</h5>
                            <div v-for="payMethod in sortedPayMethods" :key="payMethod.payMethodID" class="form-check">
                                <input class="form-check-input" type="radio" name="paymentRadio"
                                    :id="'paymentRadio' + payMethod.payMethodID" :value="payMethod.payMethodID">
                                <label class="form-check-label" :for="'paymentRadio' + payMethod.payMethodID">
                                    {{ payMethod.payMethod }}
                                </label>
                            </div>
                        </div>

                        <div class="col-md-3">
                            <fieldset disabled>
                                <label abel for="disabledTextInput" class="form-label">總金額</label>
                                <input type="text" id="disabledTextInput" class="form-control" placeholder="訂單總金額">
                            </fieldset>
                        </div>
                        <div class="col-md-3 ">
                            <button type="button" class="btn btn-secondary align-items-right" :style="{
                                position: 'absolute', margin: '30px', right: '250px'
                            }">付款</button>
                        </div>

                    </form>
                </div>


            </div>
        </div>
    </div>
</template>

<script setup>
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';
import { ref, onMounted, computed } from 'vue';
let BuyerID = sessionStorage.getItem("memberID");

const deliveries = ref([]);
const payMethods = ref([]);

const findAllDelivery = async () => {
    try {
        const response = await axiosapi.post('/delivery/find', {});
        deliveries.value = response.data.list;
    } catch (error) {
        console.error('Error fetching deliveries:', error);
    }
};

const findAllPaymethody = async () => {
    try {
        const response = await axiosapi.post('/payMethod/find', {});
        payMethods.value = response.data.list;
    } catch (error) {
        console.error('Error fetching pay methods:', error.response ? error.response.data : error.message);
    }
};

onMounted(() => {
    findAllDelivery();
    findAllPaymethody();
});

const sortedDeliveries = computed(() => {
    return deliveries.value.slice().sort((a, b) => a.deliveryID - b.deliveryID);
});
const sortedPayMethods = computed(() => {
    return payMethods.value.slice().sort((a, b) => a.payMethodID - b.payMethodID);
});



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