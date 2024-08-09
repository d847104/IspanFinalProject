<template>
        <div class="container p-5">
                <div class="my-4">
                        <h2 class="fw-bold">結帳商品列表</h2>
                </div>
                <div v-for="item in carts" class="container">
                        <div class="card mb-3 col">
                        <div class="row g-0">
                                <!-- 商品圖片 -->
                                <div class="col-md-3">
                                        <RouterLink :to="{name: 'pages-productpage-link', query: {productID: item.product.productID}}">
                                                <img :src="item.img" class="img-fluid rounded-start" :alt="item.product.productName">
                                        </RouterLink>
                                </div>
                                <div class="col-md align-self-center">
                                        <div class="card-body">
                                                <!-- 商品名稱 -->
                                                <h4 class="card-title">{{ item.product.productName }}</h4>
                                                <p class="card-text text-end fs-4">
                                                        <!-- 商品規格(若存在) -->
                                                        <template v-if="item.specOne">
                                                                <!-- 商品規格一 -->
                                                                <div class="row mt-2" v-if="item.specOne">
                                                                        <div class="col"></div>
                                                                        <span class="col text-center">{{ item.specOne.specOneName.specOneName }}</span>
                                                                        <div class="col text-center">
                                                                                {{ item.specOne.specOne }}
                                                                        </div>
                                                                </div>
                                                                <!-- 商品規格二 -->
                                                                <div class="row mt-2" v-if="item.specTwo">
                                                                        <div class="col"></div>
                                                                        <span class="col text-center"> {{ item.specTwo.specTwoName.specTwoName }}</span>
                                                                        <div class="col text-center">
                                                                                {{ item.specTwo.specTwo }}
                                                                        </div>
                                                                </div>
                                                        </template>
                                                        <!-- 商品規格結束 -->
                                                        <div class="row mt-2">
                                                                <!-- 金額 -->
                                                                <div class="col text-start text-danger">
                                                                        NT$ {{ item.product.price }}
                                                                </div>
                                                                <!-- 數量 LABEL -->
                                                                <div class="col text-center">數量</div>
                                                                <div class="col text-center">
                                                                        {{ item.quantity }}
                                                                </div>
                                                        </div>
                                                </p>
                                        </div>
                                </div>
                        </div>
                        </div>
                </div>
                <div class="my-4">
                        <h2 class="fw-bold">填寫結帳資訊</h2>
                        <div class="row my-4">
                                <div class="col-lg-3">
                                        <h4 class="fw-bold">運送方式</h4>
                                        <h4 class="text-center">運費 <span class="text-danger">NT$ {{ deliveryFee }}</span></h4>
                                </div>
                                <div class="col-lg-auto" v-if="deliveries">
                                        <div v-for="delivery in deliveries" class="form-check fs-4">
                                                <input class="form-check-input" type="radio" name="exampleRadios" :id="'delievriId'+delivery.deliveryID" :value="delivery.deliveryID" 
                                                @change="confirmDelivery(delivery.deliveryFee,delivery.deliveryID,$event)">
                                                <label class="form-check-label" :for="'delievriId'+delivery.deliveryID">{{ delivery.delivery }}</label>
                                        </div>
                                </div>
                                <div class="col-lg-auto"><h4 class="fw-bold">收件資訊</h4></div>
                                <div class="col-lg">
                                        <!-- 收件人 -->
                                        <div class="row align-items-center fs-4">
                                                <div class="col-4 text-end">
                                                        <label for="recipient" class="col-form-label">收件人</label>
                                                </div>
                                                <div class="col">
                                                        <input type="text" id="recipient" class="form-control">
                                                </div>
                                        </div>
                                        <!-- 電話 -->
                                        <div class="row align-items-center fs-4">
                                                <div class="col-4 text-end">
                                                        <label for="phone" class="col-form-label">連絡電話</label>
                                                </div>
                                                <div class="col">
                                                        <input type="text" id="phone" class="form-control">
                                                </div>
                                        </div>
                                        <!-- 地址 -->
                                        <div class="row align-items-center fs-4" v-if="deliveryId == 1">
                                                <div class="col-4 text-end">
                                                        <label for="address" class="col-form-label">地址</label>
                                                </div>
                                                <div class="col">
                                                        <input type="text" id="address" class="form-control">
                                                </div>
                                        </div>
                                </div>

                        </div>
                        <div class="row my-2">
                                <h4 class="fw-bold">付款方式</h4>
                                <div v-if="payMethods" class="my-4">
                                        <div class="btn-group btn-group-lg" role="group">
                                                <template v-for="payMethod in payMethods">
                                                        <input type="radio" class="btn-check" name="btnradio" :id="'payMethodId'+payMethod.payMethodID" autocomplete="off" :value="payMethod.payMethodID">
                                                        <label class="btn btn-light fs-4" :for="'payMethodId'+payMethod.payMethodID">{{ payMethod.payMethod }}</label>
                                                </template>
                                        </div>
                                </div>
                        </div>
                        <div class="row my-4 text-end">
                                <div class="row d-flex justify-content-end">
                                        <div class="col-lg-3">
                                                <span class="fw-bold fs-4">商品總計</span>
                                        </div>
                                        <div class="col-md-4 fs-4">
                                                <div class="row">
                                                        <span class="col">NT$ </span>
                                                        <span class="col">{{ totalCarts }}</span>
                                                </div>
                                        </div>
                                </div>
                                <div class="row d-flex justify-content-end">
                                        <div class="col-lg-2">
                                                <span class="fw-bold fs-4">運費</span>
                                        </div>
                                        <div class="col-md-4 fs-4">
                                                <div class="row">
                                                        <span class="col">NT$ </span>
                                                        <span class="col">{{ deliveryFee }}</span>
                                                </div>
                                        </div>
                                </div>
                                <div class="row d-flex justify-content-end">
                                        <div class="col-lg-2">
                                                <span class="fw-bold fs-4">訂單總金額</span>
                                        </div>
                                        <div class="col-md-4 fs-4">
                                                <div class="row">
                                                        <span class="col text-danger fw-bold">NT$ </span>
                                                        <span class="col text-danger fw-bold">{{ totalOrder }}</span>
                                                </div>
                                        </div>
                                </div>
                        </div>
                        <div class="row my-4 d-flex flex-row justify-content-end">
                                <div class="col-lg-3">
                                        <button type="button" class="btn btn-success btn-lg" @click="payconfirm">確認付款</button>
                                </div>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { ref, onMounted, onUnmounted, nextTick, computed } from 'vue';
        import emitter from '@/plugins/events';
        import axiosapi from '@/plugins/axios';
        import Swal from 'sweetalert2';

        onMounted(async() => {
                emitter.on('result', selectedItems);
                await nextTick();
                callDelivery();
                callPayMethod();
        })
        onUnmounted(() => {emitter.off('result', selectedItems);});

        const selectedItems = (items) => {
                carts.value = items;
        }
        const carts = ref([]);

        // 若商品有圖片則選擇第一張的 ImgID 以網址 {id} 方式秀出圖片,否則使用 comingsoon
        const path = computed(() => {
                if (item.product.productImgs[0]) {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.cart.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })
        // 商品總金額
        const totalCarts = computed(() => carts.value.reduce((sum, item) => { return sum + (item.quantity * item.product.price); }, 0))
        // 運送方式清單
        const deliveries = ref([]);
        // 運費
        const deliveryFee = ref(0);
        // 運送方式ID
        const deliveryId = ref(null);
        // 訂單總金額
        const totalOrder = computed(() => totalCarts.value + deliveryFee.value);
        // 付款方式清單
        const payMethods = ref([]);

        // 呼叫 Delivery 清單
        function callDelivery(){
                let request = {
                        "start": 0,
                        "max": 5,
                        "dir": true,
                        "delivery1": "deliveryID"
                }
                axiosapi.post("/delivery/find", request)
                .then(function(response){
                        deliveries.value =  response.data.list;
                })
                .catch(function(error){
                        console.log("error",error)
                        console.log(error.message);
                        Swal.fire({
                                icon: "error",
                                text: "錯誤",
                                allowOutsideClick: false,
                        })
                })
        }

        // 選擇運送方式回傳運費
        function confirmDelivery(selectedFee,selectedId,event){
                if(event.target.checked){
                        deliveryFee.value = selectedFee;
                        deliveryId.value = selectedId;
                }
        }

        // 呼叫 PayMethod 清單
        function callPayMethod() {
                let request = {
                        "start": 0,
                        "max": 5,
                        "dir": true,
                        "payMethod1": "payMethodID"
                }
                axiosapi.post("/payMethod/find", request)
                .then(function(response){
                        payMethods.value =  response.data.list;
                })
                .catch(function(error){
                        console.log("error",error)
                        console.log(error.message);
                        Swal.fire({
                                icon: "error",
                                text: "錯誤",
                                allowOutsideClick: false,
                        })
                })
        }

        function payconfirm(){
                let request = {

                }
        }
</script>
        
<style>
        
</style>