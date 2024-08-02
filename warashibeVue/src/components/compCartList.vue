<template>
        <div class="card mb-3 col">
                <div class="row g-0">
                        <!-- 商品圖片 -->
                        <div class="col-md-3">
                                <RouterLink :to="{name: 'pages-productpage-link', query: {productID: cart.product.productID}}">
                                        <img :src="path" class="img-fluid rounded-start" :alt="cart.product.productName">
                                </RouterLink>
                        </div>
                        <div class="col-md-7 align-self-center">
                                <div class="card-body">
                                        <!-- 商品名稱 -->
                                        <h4 class="card-title">{{ cart.product.productName }}</h4>
                                        <p class="card-text text-end fs-4">
                                                <!-- 商品規格(若存在) -->
                                                <template v-if="cart.productSpec">
                                                        <!-- 商品規格一 -->
                                                        <div class="row mt-2" v-if="cart.productSpec.specOneName">
                                                                <span class="col">{{ cart.productSpec.specOneName }}</span>
                                                                <div class="input-group col justify-content-center">
                                                                        {{ cart.productSpec.specOne }}
                                                                </div>
                                                        </div>
                                                        <!-- 商品規格二 -->
                                                        <div class="row mt-2" v-if="cart.productSpec.specTwoName">
                                                                <span class="col"> {{ cart.productSpec.specTwoName }}</span>
                                                                <div class="input-group col justify-content-center">
                                                                        {{ cart.productSpec.specTwo }}
                                                                </div>
                                                        </div>
                                                </template>
                                                <!-- 商品規格結束 -->
                                                <div class="row mt-2">
                                                        <!-- 數量 LABEL -->
                                                        <label :for="cart.cartID" class="col">
                                                                數量
                                                        </label>
                                                        <!-- 數量按鈕群組 -->
                                                        <div class="input-group col">
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
                                                <div class="row mt-5">
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
                                        <input class="form-check-input" type="checkbox" style="width:2em; height:2em;" value="" :id="cart.product.productID+'ck'">
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
        
        // 接收從 cart.vue 取得使用者在資料庫的cart資料
        const props = defineProps(["cart"]);
        // 修改數量及刪除須回傳 cart.vue 重新渲染
        const emits = defineEmits(["updateCart","removeCart"]);

        // 若商品有圖片則選擇第一張的 ImgID 以網址 {id} 方式秀出圖片,否則使用 comingsoon
        const path = computed(() => {
                if (props.cart.product.productImgs[0]) {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.cart.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })

        // 預設 Bootstrap tooltip 商品庫存訊息
        const alert = `該產品庫存剩餘${props.cart.product.stock}件`;

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
                }else if(cartQt > props.cart.product.stock){
                        quantity.value = props.cart.product.stock;
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
                if(cartQt < props.cart.product.stock){
                        cartQt++
                        quantity.value = cartQt;
                }else{
                        quantity.value = props.cart.product.stock;
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
                                emits("removeCart",props.cart.cartID)
                                Swal.fire({
                                        position: "center",
                                        icon: "success",
                                        title: "已移除",
                                        showConfirmButton: false,
                                        timer: 800
                                });
                        }
                });
        }
</script>

<style scoped>

</style>