<template>
        <div class="card mb-3 col">
                <div class="row g-0">
                        <!-- 商品圖片 -->
                        <div class="col-md-3">
                                <RouterLink :to="{name: 'pages-productpage-link', query: {productID: cart.product.productID}}">
                                        <img :src="path" class="img-fluid rounded-start object-fit-cover" :alt="cart.product.productName" style="height:100%;">
                                </RouterLink>
                        </div>
                        <div class="col-md-7 align-self-center">
                                <div class="card-body">
                                        <!-- 商品名稱 -->
                                        <h4 class="card-title">{{ cart.product.productName }}</h4>
                                        <p class="card-text text-end fs-4">
                                                <!-- 商品規格(若存在) -->
                                                <template v-if="cart.specOne">
                                                        <!-- 商品規格一 -->
                                                        <div class="row mt-2" v-if="cart.specOne">
                                                                <div class="col-md"></div>
                                                                <span class="col">{{ cart.specOne.specOneName.specOneName }}</span>
                                                                <div class="input-group col justify-content-center">
                                                                        {{ cart.specOne.specOne }}
                                                                </div>
                                                        </div>
                                                        <!-- 商品規格二 -->
                                                        <div class="row mt-2" v-if="cart.specTwo">
                                                                <div class="col-md"></div>
                                                                <span class="col"> {{ cart.specTwo.specTwoName.specTwoName }}</span>
                                                                <div class="input-group col justify-content-center">
                                                                        {{ cart.specTwo.specTwo }}
                                                                </div>
                                                        </div>
                                                </template>
                                                <!-- 商品規格結束 -->
                                                <div class="row mt-2">
                                                        <!-- 金額 -->
                                                         <div class="col-lg text-start text-danger">
                                                                NT$ {{ cart.product.price }}
                                                         </div>
                                                        <!-- 數量 LABEL -->
                                                        <label :for="cart.cartID" class="col-lg justify-content-center">
                                                                數量
                                                        </label>
                                                        <!-- 數量按鈕群組 -->
                                                        <div class="input-group col-md">
                                                                <!-- 數量減少按鈕 -->
                                                                <button class="btn btn-outline-secondary" type="button" @click="removeOne" :disabled="quantity===1">
                                                                        <font-awesome-icon :icon="['fas', 'minus']" />
                                                                </button>

                                                                <!-- 數量 -->
                                                                <input type="text" class="form-control text-center" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-trigger="manual" :title="alert" ref="quantityDOM" v-model="quantity" :id="cart.cartID" @change="checkQt" @focus="focus">

                                                                <!-- 數量增加按鈕 -->
                                                                <button class="btn btn-outline-secondary" type="button" @click="addOne" :disabled="exceed">
                                                                        <font-awesome-icon :icon="['fas', 'plus']" />
                                                                </button>
                                                        </div>
                                                        <!-- 數量按鈕群組結束 -->
                                                </div>
                                                <!-- 垃圾桶按鈕 -->
                                                <div class="row mt-4 text-end">
                                                        <span>
                                                                <button type="button" style="font-size: 1.5em; border: none; background-color: inherit;" class="text-danger" @click="remove">
                                                                        <font-awesome-icon :icon="['fas', 'trash-can']" />
                                                                </button>
                                                        </span>
                                                </div>
                                        </p>
                                </div>
                        </div>
                        <!-- 選取商品 CHECKBOX -->
                        <div class="card-body col-md-1 align-self-stretch d-flex align-items-center justify-content-center" style="background-color: var(--bs-light);">
                                <div class="form-check">
                                        <input class="form-check-input" type="checkbox" style="width:2em; height:2em;" value="" :id="cart.cartID" v-model="isSelected" :disabled="chbdisabled" @change="emits('upddateChb',isSelected,cart.seller.memberID)">
                                </div>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { RouterLink } from 'vue-router';
        import { computed, onMounted, ref } from 'vue';
        import { Tooltip } from 'bootstrap/dist/js/bootstrap.bundle.min.js';
        import Swal from 'sweetalert2';

        // 元件渲染完畢後初始化 Boottrap tooltip
        onMounted(function(){
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                        return new Tooltip(tooltipTriggerEl);
                });
        })
        
        // 接收從 cart.vue 取得使用者在資料庫的cart資料 以及是否勾選
        const props = defineProps(["cart","isChecked"]);
        // 修改數量及刪除須回傳 cart.vue 重新渲染
        const emits = defineEmits(["updateCart","removeCart","selectCart","upddateChb"]);
        // 雙向綁定 cart.vue 是否勾選
        const isSelected = defineModel("isChecked")
        const chbdisabled = defineModel("isDisabled")

        // 若商品有圖片則選擇第一張的 ImgID 以網址 {id} 方式秀出圖片,否則使用 comingsoon
        const path = computed(() => {
                if (props.cart.product.productImgs[0]) {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.cart.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })

        // 計算該規格的產品庫存量
        const stock = computed(() => {
                if (props.cart.specTwo) {
                        return props.cart.specTwo.specTwoQt;
                } else if (props.cart.specOne){
                        return props.cart.specOne.specOneQt;
                } else {
                        return props.cart.product.stock;
                }
        })

        // 預設 Bootstrap tooltip 商品庫存訊息
        const alert = ref(`該產品庫存剩餘${stock.value}件`);

        // 使用 ref 綁定數量 INPUT DOM 物件
        const quantityDOM = ref(null);

        // 雙向綁定數量 INPUT,預設值為接收的 props 數量資料
        const quantity = ref(props.cart.quantity);

        // 自定義 switch flag 存放數量 INPUT 是否超過商品庫存量,預設 false
        const exceed = ref(false);

        // 數量 INPUT 被鎖定時先取得修改前的值
        var focusQt;
        function focus(){
                focusQt = quantity.value;
        }

        // 數量 INPUT 被修改後檢查合法性,最後 emits 傳資料回 Cart.vue 父元件呼叫 axios 修改資料庫 TABLE cart
        function checkQt(){
                exceed.value = false;
                let cartQt = parseInt(quantity.value);
                if(!Number.isInteger(cartQt) || cartQt <1){
                        quantity.value = focusQt;
                        return;
                }else if(cartQt > stock.value){
                        quantity.value = stock.value;
                        exceed.value = true;
                        Tooltip.getInstance(quantityDOM.value).show();
                        setTimeout(()=>Tooltip.getInstance(quantityDOM.value).hide(),1200);
                }else{
                        quantity.value = cartQt;
                }
                emits("updateCart",{ ...props.cart, quantity: quantity.value });
        }
        
        // 數量增加按鈕
        function addOne(){
                exceed.value = false;
                let cartQt = parseInt(quantity.value);
                if(cartQt < stock.value){
                        cartQt++
                        quantity.value = cartQt;
                }else{
                        quantity.value = stock.value;
                        exceed.value = true;
                        Tooltip.getInstance(quantityDOM.value).show();
                        setTimeout(()=>Tooltip.getInstance(quantityDOM.value).hide(),1200);
                }
                emits("updateCart",{ ...props.cart, quantity: quantity.value });
        }

        // 數量減少按鈕
        function removeOne(){
                exceed.value = false;
                let cartQt = parseInt(quantity.value);
                if(cartQt > 1){
                        cartQt--;
                        quantity.value = cartQt;
                }
                emits("updateCart",{ ...props.cart, quantity: quantity.value });
        }

        // 垃圾桶按鈕,發出 REQUEST 刪除資料庫 TABLE cart 一筆資料
        function remove(){
                Swal.fire({
                        title: "從購物車移除此商品?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "確認移除",
                        cancelButtonText: "取消"
                }).then((result) => {
                        if (result.isConfirmed) {
                                emits("removeCart",props.cart.cartID,props.cart.member)
                        }
                });
        }
</script>

<style scoped>

</style>