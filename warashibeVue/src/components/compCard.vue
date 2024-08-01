<template>
        <div class="col-lg-3 col-md-6 mb-5">
                <div class="card h-100">
                        <!-- 商品照片 -->
                        <img class="card-img-top h-100 object-fit-contain" :alt="product.productName" :src="path" @click="viewProductDetail">
                        <div class="card-body">
                                <!-- 商品名稱 -->
                                <h6 class="card-title fw-bold">{{ product.productName }}</h6>

                                <!-- 價格或二手換物 -->
                                <div class="card-text text-danger text-end fs-5" v-if="!product.secondHand">NT$
                                        {{ product.price }}</div>
                                <div class="card-text text-success text-end fs-5" v-if="product.secondHand">想交換
                                        {{ product.wishItem }}</div>
                                <!-- 價格或二手換物結束 -->

                                <div class="mt-3">
                                        <!-- 加入購物車 -->
                                        <a href="#"><font-awesome-icon :icon="['fas', 'cart-plus']" size="2x" pull="left" /></a>
                                        <!-- 數量按鈕群組 -->
                                        <div class="input-group col">
                                                <!-- 減少數量按鈕 -->
                                                <button class="btn btn-outline-secondary" type="button"
                                                        @click="removeOne" :disabled="quantity === 1">
                                                        <font-awesome-icon :icon="['fas', 'minus']" />
                                                </button>
                                                <!-- 數量 -->
                                                <input type="text" class="form-control text-center" placeholder=""
                                                        v-model="quantity" :id="product.productID + 'Qt'"
                                                        @change="checkQt">
                                                <!-- 增加數量按鈕 -->
                                                <button class="btn btn-outline-secondary" type="button" @click="addOne"
                                                        :style="[exceed ? 'border-color: var(--bs-dark-border-subtle)' : '']">
                                                        <span data-bs-toggle="tooltip" data-bs-placement="top"
                                                                :title="alert" v-show="exceed"
                                                                style="color: var(--bs-dark-border-subtle);">
                                                                <font-awesome-icon :icon="['fas', 'plus']" />
                                                        </span>
                                                        <span v-show="!exceed">
                                                                <font-awesome-icon :icon="['fas', 'plus']" />
                                                        </span>
                                                </button>
                                        </div>
                                        <!-- 數量按鈕群組結束 -->
                                        <!-- 收藏愛心 -->
                                        <a href="#"><font-awesome-icon :icon="['far', 'heart']" size="2x" beat
                                                        style="color:lightcoral;--fa-beat-scale: 1.0" pull="right"
                                                        @mouseover="(e) => { e.target.style.setProperty('--fa-beat-scale', 1.3) }"
                                                        @mouseout="(e) => { e.target.style.setProperty('--fa-beat-scale', 1.0) }" /></a>
                                        <!-- 收藏愛心結束 -->
                                </div>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { computed, onMounted, ref } from 'vue';
        import { useRouter } from 'vue-router';
        import { Tooltip } from 'bootstrap/dist/js/bootstrap.bundle.min.js';

        const router = useRouter();
        const props = defineProps(["product"]);
        const path = computed(() => {
                if (props.product.productImgs[0]) {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })
        const viewProductDetail = () => {
                router.push(`/pages/productpage?productID=${props.product.productID}`);
        };

        onMounted(function(){
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                        return new Tooltip(tooltipTriggerEl)
                })
        })

        const alert = `該產品庫存剩餘${props.product.stock}件`;
        const exceed = ref(false);
        const quantity = ref(1)

        var focusValue;
        function focus(){
                console.log(quantity.value)
                focusValue = quantity.value;
        }

        function checkQt(){
                exceed.value = false;
                if(quantity.value > props.product.stock){
                        exceed.value = true;
                        quantity.value = props.product.stock;
                }else if(quantity.value < 1 || !Number.isInteger(quantity.value)){
                        quantity.value = focusValue;
                }
        }
</script>

<style scoped></style>