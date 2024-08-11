<template>
        <!-- 常用收件人Modal -->
        <div class="modal fade" id="myModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
        <div class="modal-content">
                <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">選擇常用收件人</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                        <div class="row mx-1 my-3 text-center">
                                <div class="col">收件人</div>
                                <div class="col">連絡電話</div>
                                <div class="col">地址</div>
                        </div>
                <div class="btn-group-vertical d-flex" role="group" aria-label="Vertical radio toggle button group" v-for="recepient in recepients">
                        <input type="radio" class="btn-check" name="vbtn-radio" :id="'rc'+recepient.recepientID" autocomplete="off" v-model="selectedRecepient" :value="recepient">
                        <label class="btn btn-outline-primary list-group-item-action text-center" :for="'rc'+recepient.recepientID">
                                <div class="row">
                                        <div class="col">{{recepient.name}}</div>
                                        <div class="col">{{ recepient.mobile }}</div>
                                        <div class="col">{{ recepient.address }}</div>
                                </div>
                        </label>
                </div>
                </div>
                <div class="modal-footer">
                        <button type="button" class="btn btn-primary" @click="confirmRecepient" data-bs-dismiss="modal">選擇</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                </div>
        </div>
        </div>
        </div>
        <!-- 常用收件人Modal結束 -->
        <div class="container">
                <div class="my-4">
                        <h2 class="fw-bold">結帳商品列表</h2>
                </div>
                <div v-for="item in carts" class="container d-flex justify-content-center">
                        <div class="card mb-3 col" :id="item.cartID">
                        <div class="row g-0">
                                <!-- 商品圖片 -->
                                <div class="col">
                                        <RouterLink :to="{name: 'pages-productpage-link', query: {productID: item.product.productID}}">
                                                <img :src="item.img" class="img-fluid rounded-start object-fit-fit" :alt="item.product.productName">
                                        </RouterLink>
                                </div>
                                <div class="col-md align-self-center">
                                        <div class="card-body">
                                                <!-- 商品名稱 -->
                                                <h4 class="card-title fw-bold">{{ item.product.productName }}</h4>
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
                                                                <div class="col text-center text-danger">
                                                                        NT$ {{ item.product.price }}
                                                                </div>
                                                                <!-- 數量 LABEL -->
                                                                <div class="col text-center">數量</div>
                                                                <div class="col text-center">
                                                                        {{ item.quantity }}
                                                                </div>
                                                        </div>
                                                        <div class="row mt-4">
                                                                <div class="col text-center justify-content-center">
                                                                        配送方式
                                                                        <div class="vstack gap-1" v-for="delivery in item.product.productDeliveries">
                                                                                <div class="p-1 fs-6 alert alert-success">{{delivery.delivery.delivery}}</div>
                                                                        </div>
                                                                </div>
                                                                <div class="col text-center justify-content-center">
                                                                        付款方式
                                                                        <div class="vstack gap-1" v-for="method in item.product.productPayMethods">
                                                                                <div class="p-1 fs-6 alert alert-info">{{method.payMethodID.payMethod}}</div>
                                                                        </div>
                                                                </div>
                                                                <!-- 垃圾桶按鈕 -->
                                                                <div class="col d-flex align-items-center justify-content-center">
                                                                        <span>
                                                                                <button type="button" style="font-size: 1.5em; border: none; background-color: inherit;" class="text-danger" @click="remove">
                                                                                        <font-awesome-icon :icon="['fas', 'trash-can']" />
                                                                                </button>
                                                                        </span>
                                                                </div>
                                                        </div>
                                                </p>
                                        </div>
                                </div>
                        </div>
                        </div>
                </div>
                <div class="alert alert-danger fs-4 text-center" role="alert" v-if="deliveryAlert!=''">
                        <font-awesome-icon :icon="['fas', 'triangle-exclamation']" /> {{ deliveryAlert }}
                </div>
                <div class="alert alert-warning fs-4 text-center" role="alert" v-if="payMethodAlert!=''">
                        <font-awesome-icon :icon="['fas', 'triangle-exclamation']" /> {{ payMethodAlert }}
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
                                                <input class="form-check-input" type="radio" name="exampleRadios" 
                                                :id="'delievriId'+delivery.deliveryID" :value="delivery.deliveryID" 
                                                @change="confirmDelivery(delivery.deliveryFee, delivery.deliveryID,$event)">
                                                <label class="form-check-label" :for="'delievriId'+delivery.deliveryID">{{ delivery.delivery }}</label>
                                        </div>
                                        <div class="mt-4" v-if="deliveryId==2 || deliveryId==3">
                                                <button @click="callExpressMap">選擇門市</button>
                                                <span v-if="expressmap!=null" v-html="expressmap"></span>
                                        </div>
                                </div>
                                <div class="col-lg-auto">
                                        <h4 class="fw-bold">收件資訊</h4>
                                        <button class="btn btn-primary" type="button" data-bs-toggle="modal" data-bs-target="#myModal">常用收件人</button>
                                </div>
                                <div class="col-lg">
                                        <!-- 收件人 -->
                                        <div class="row align-items-center fs-4">
                                                <div class="col-4 text-end">
                                                        <label for="recipient" class="col-form-label">收件人</label>
                                                </div>
                                                <div class="col">
                                                        <input type="text" id="recipient" class="form-control" v-model="recipient">
                                                </div>
                                        </div>
                                        <!-- 電話 -->
                                        <div class="row align-items-center fs-4">
                                                <div class="col-4 text-end">
                                                        <label for="phone" class="col-form-label">電話</label>
                                                </div>
                                                <div class="col">
                                                        <input type="text" id="phone" class="form-control" v-model="phone">
                                                </div>
                                        </div>
                                        <!-- 地址 -->
                                        <div class="row align-items-center fs-4" v-if="deliveryId == 1">
                                                <div class="col-4 text-end">
                                                        <label for="address" class="col-form-label">地址</label>
                                                </div>
                                                <div class="col">
                                                        <input type="text" id="address" class="form-control" v-model="address">
                                                </div>
                                        </div>
                                        <div class="row"><button type="button" class="btn btn-sm btn-info" @click="addRecepient">新增常用收件人</button></div>
                                </div>

                        </div>
                        <div class="row my-2">
                                <h4 class="fw-bold">付款方式</h4>
                                <div v-if="payMethods" class="my-4">
                                        <div class="btn-group btn-group-lg" role="group">
                                                <template v-for="payMethod in payMethods">
                                                        <input type="radio" class="btn-check" name="btnradio" autocomplete="off" 
                                                        :id="'payMethodId'+payMethod.payMethodID" :value="payMethod.payMethodID" 
                                                        @change="confirmPayMethod(payMethod.payMethodID, $event)">
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
                                        <button type="button" class="btn btn-success btn-lg" @click="confirmCheckout">確認付款</button>
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
        import { useRouter } from 'vue-router';
        import axios from 'axios';

        const router = useRouter();
        
        const carts = ref([]);
        // 接收從購物車傳遞過來的資料
        onMounted(async() => {
                emitter.on('result', selectedItems);
                await nextTick();
                if(carts.value.length==0){router.push({name: "cart"})};
                callRecepients();
                callDelivery();
                callPayMethod();
        })
        onUnmounted(() => {emitter.off('result', selectedItems);});
        const selectedItems = (items) => {
                carts.value = items;
        }

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
        // 付款方式ID
        const payMethodId = ref(null);
        // 運送方式提示訊息
        const deliveryAlert = ref("");
        // 付款方式提示訊息
        const payMethodAlert = ref("");
        // 收件人
        const recipient = ref("");
        // 電話
        const phone = ref("");
        // 地址
        const address = ref("");
        // 常用收件人清單
        const recepients = ref([])
        // 選取的常用收件人
        const selectedRecepient = ref(null)

        const expressmap = ref(null)

        function callRecepients(){
                axiosapi.get(`/ajax/recepient/member/${sessionStorage.getItem("memberID")}`)
                .then(function(response){
                        recepients.value = response.data.list;
                }).catch(function(error){
                        console.log("error",error)
                        console.log(error.message);
                        Swal.fire({
                                icon: "error",
                                text: "錯誤:無法取得常用收件人列表",
                                allowOutsideClick: false,
                        })
                })
        }

        function confirmRecepient(){
               console.log(selectedRecepient.value);
               recipient.value = selectedRecepient.value.name;
               phone.value = selectedRecepient.value.mobile;
               address.value = selectedRecepient.value.address;
        }

        function addRecepient(){
                if(recipient.value==""){
                        Swal.fire({
                                icon: "warning",
                                text: "請輸入收件人",
                                allowOutsideClick: false,
                        })
                }else if(phone.value==""){
                        Swal.fire({
                                icon: "warning",
                                text: "請輸入連絡電話",
                                allowOutsideClick: false,
                        })
                }
                let request = {
                        "memberID": sessionStorage.getItem("memberID"),
                        "name": recipient.value,
                        "mobile": phone.value,
                        "address": address.value
                }
                axiosapi.post("/ajax/recepient/insert", request)
                .then(function(result){
                        if(result.data.success){
                                callRecepients();
                                Swal.fire({
                                        position: "center",
                                        icon: "success",
                                        title: "已新增常用收件人",
                                        showConfirmButton: false,
                                        timer: 800
                                });
                        }
                }).catch(function(error){
                        console.log("error",error);
                        console.log(error.message);
                        Swal.fire({
                                icon: "error",
                                text: "新增常用收件人失敗",
                                allowOutsideClick: false,
                        })
                })
        }

        // 呼叫 Delivery 清單
        function callDelivery(){
                axiosapi.get("/delivery/all")
                .then(function(response){
                        deliveries.value =  response.data.list;
                })
                .catch(function(error){
                        console.log("error",error)
                        console.log(error.message);
                        Swal.fire({
                                icon: "error",
                                text: "錯誤:無法取得配送方式列表",
                                allowOutsideClick: false,
                        })
                })
        }

        // 選擇運送方式回傳運費, 並檢核是否符合商品運送方式
        function confirmDelivery(selectedFee, selectedId,event){
                carts.value.forEach(cart=>document.getElementById(cart.cartID).style.border = null);
                deliveryAlert.value = "";
                if(event.target.checked){
                        deliveryFee.value = selectedFee;
                        deliveryId.value = selectedId;
                }
                const cartsNotMeet = carts.value.map(cart=>{
                        const notMeets = !cart.product.productDeliveries.map(delivery => delivery.delivery.deliveryID).includes(selectedId);
                        return{cartID: cart.cartID, notMeets}
                });
                cartsNotMeet.filter(function(cart){return cart.notMeets==true;})
                .forEach(cart=>{
                        document.getElementById(cart.cartID).style.border = "5px solid var(--bs-danger-border-subtle)";
                        deliveryAlert.value = "您選取的運送方式不支援部分結帳商品，請再次確認";
                })
        }

        // 呼叫 PayMethod 清單
        function callPayMethod() {
                axiosapi.get("/payMethod/all")
                .then(function(response){
                        payMethods.value =  response.data.list;
                })
                .catch(function(error){
                        console.log("error",error)
                        console.log(error.message);
                        Swal.fire({
                                icon: "error",
                                text: "錯誤:無法取得付款方式列表",
                                allowOutsideClick: false,
                        })
                })
        }

        // 選擇付款方式並檢核是否符合商品運送方式
        function confirmPayMethod(selectedId, event){
                carts.value.forEach(cart=>document.getElementById(cart.cartID).style.backgroundColor = null);
                payMethodAlert.value = "";
                if(event.target.checked){
                        payMethodId.value = selectedId;
                }
                const cartsNotMeet = carts.value.map(cart=>{
                        const notMeets = !cart.product.productPayMethods.map(method => method.payMethodID.payMethodID).includes(selectedId);
                        return{cartID: cart.cartID, notMeets}
                });
                cartsNotMeet.filter(function(cart){return cart.notMeets==true;})
                .forEach(cart=>{
                        document.getElementById(cart.cartID).style.backgroundColor = "var(--bs-warning-border-subtle)";
                        payMethodAlert.value = "您選取的付款方式不支援部分結帳商品，請再次確認";
                })
        }

        // 超商電子地圖
        function callExpressMap(){
                const logisticsSubType = computed(()=>{
                        if(deliveryId.value==2) return "UNIMARTC2C";
                        if(deliveryId.value==3) return "FAMIC2C";
                })
                let request = {
                        "LogisticsSubType": logisticsSubType.value,
                        "IsCollection": payMethodId.value == 3 ? "Y" : "N"
                }
                axiosapi.post("/api/ECPayLogistic/expressMap",request)
                .then(function(response){
                        console.log(response);
                        // Create a form and set it up for submission
                        let form = document.createElement('form');
                        form.method = 'post';
                        form.action = 'https://logistics-stage.ecpay.com.tw/Express/map';
                        
                        // Parse the response HTML to extract hidden input fields
                        const parser = new DOMParser();
                        const doc = parser.parseFromString(response.data, 'text/html');
                        const inputs = doc.querySelectorAll('input');
                        inputs.forEach(input => {
                                let hiddenField = document.createElement('input');
                                hiddenField.type = 'hidden';
                                hiddenField.name = input.name;
                                hiddenField.value = input.value;
                                form.appendChild(hiddenField);
                        });

                        // Append form to the body and submit
                        document.body.appendChild(form);
                        form.submit();
                }).catch(function(error){
                        console.log(error);
                })
        }
        
        // 確認付款並最後檢核
        function confirmCheckout(){
                if(deliveryId.value == null){
                        Swal.fire({
                                icon: "warning",
                                text: "請選擇運送方式",
                                allowOutsideClick: false,
                        })
                }else if(payMethodId.value == null){
                        Swal.fire({
                                icon: "warning",
                                text: "請選擇付款方式",
                                allowOutsideClick: false,
                        })
                }else if(deliveryAlert.value!="" && payMethodAlert.value!=""){
                        Swal.fire({
                                icon: "warning",
                                text: "請選擇結帳商品可支援的運送及付款方式",
                                allowOutsideClick: false,
                        })
                }else if(deliveryAlert.value!="" && payMethodAlert.value==""){
                        Swal.fire({
                                icon: "warning",
                                text: "請選擇結帳商品可支援的運送方式",
                                allowOutsideClick: false,
                        })
                }else if(deliveryAlert.value=="" && payMethodAlert.value!=""){
                        Swal.fire({
                                icon: "warning",
                                text: "請選擇結帳商品可支援的付款方式",
                                allowOutsideClick: false,
                        })
                }else if(recipient.value.length==0){
                        Swal.fire({
                                icon: "warning",
                                text: "請填寫收件人資訊",
                                allowOutsideClick: false,
                        })
                }else if(phone.value.length==0){
                        Swal.fire({
                                icon: "warning",
                                text: "請填寫聯絡電話資訊",
                                allowOutsideClick: false,
                        })
                }
        }
</script>
        
<style>
        
</style>