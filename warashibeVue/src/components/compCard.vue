<template>
        <div class="col-lg-3 col-md-6 mb-5">
                <div class="card h-100 rounded-3">
                        <!-- 商品照片 -->
                        <RouterLink :to="{name: 'pages-productpage-link', query: {productID: product.productID}}">
                                <img class="card-img-top object-fit-contain" :alt="product.productName" :src="path" height="300px">
                        </RouterLink>
                        <div class="card-body d-flex align-items-end">
                                <div class="d-flex flex-column w-100">
                                        <!-- 商品名稱 -->
                                        <h5 class="card-title fw-bold">{{ product.productName }}</h5>
        
                                        <!-- 價格或二手換物 -->
                                        <div class="card-text text-danger text-end fs-5 my-2" v-if="!product.secondHand">NT$
                                                {{ product.price }}</div>
                                        <div class="card-text text-success text-end fs-5 my-2" v-if="product.secondHand">想交換
                                                {{ product.wishItem }}</div>
                                        
                                        <!-- 規格(若存在) -->
                                        <div class="row" v-if="product.specs">
                                                <!-- 規格一 -->
                                                <select class="form-select" v-model="selectedSpecOne" @change="specOneChange">
                                                        <option value="" disabled selected>請選擇{{ product.specs.specOneName }}</option>
                                                        <option v-for="specOne in product.specs.specOnes" :key="specOne.specOneID" :value="specOne.specOneID">{{ specOne.specOne }}</option>
                                                </select>
                                                <!-- 規格二(若存在) -->
                                                <template v-if="filteredSpecTwos.length">
                                                        <select class="form-select" v-model="selectedSpecTwo">
                                                        <option value="" disabled selected>請選擇{{ filteredSpecTwoName }}</option>
                                                        <option v-for="specTwo in filteredSpecTwos" :key="specTwo.specTwo" :value="specTwo.specTwoID">{{ specTwo.specTwo }}</option>
                                                        </select>
                                                </template>
                                        </div>

                                        <div class="row mt-2">
                                                <!-- 加入購物車 -->
                                                <div class="col-2">
                                                        <a href="#"><font-awesome-icon :icon="['fas', 'cart-plus']" size="2x" pull="left" /></a>
                                                </div>
                                                
                                                <!-- 數量按鈕群組 -->
                                                <div class="input-group col">
                                                        <!-- 數量減少按鈕 -->
                                                        <button class="btn btn-outline-secondary" type="button" @click="removeOne" :disabled="quantity===1">
                                                                <font-awesome-icon :icon="['fas', 'minus']" />
                                                        </button>
                
                                                        <!-- 數量 -->
                                                        <input type="text" class="form-control text-center" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-trigger="manual" :title="alert" ref="quantityDOM" v-model="quantity" @change="checkQt" @focus="focus">
                                                        
                                                        <!-- 數量增加按鈕 -->
                                                        <button class="btn btn-outline-secondary" type="button" @click="addOne" :disabled="exceed">
                                                                <font-awesome-icon :icon="['fas', 'plus']" />
                                                        </button>
                                                </div>
                                                <!-- 數量按鈕群組結束 -->
                                                
                                                <!-- 收藏愛心 -->
                                                <div class="col-2">
                                                        <a href="#"><font-awesome-icon :icon="['far', 'heart']" size="2x" beat
                                                                style="color:lightcoral;--fa-beat-scale: 1.0" pull="right"
                                                                @mouseover="(e) => { e.target.style.setProperty('--fa-beat-scale', 1.3) }"
                                                                @mouseout="(e) => { e.target.style.setProperty('--fa-beat-scale', 1.0) }" /></a>
                                                </div>
                                        </div>
                                </div>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { computed, onMounted, ref, toRaw } from 'vue';
        import { RouterLink } from 'vue-router';
        import { Tooltip } from 'bootstrap/dist/js/bootstrap.bundle.min.js';

        // 接收父元件資料
        const props = defineProps(["product"]);

        // 若商品有圖片則選擇第一張的 ImgID 以網址 {id} 方式秀出圖片,否則使用 comingsoon
        const path = computed(() => {
                if (props.product.productImgs[0]) {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })

        // 用於選擇的規格一
        const selectedSpecOne = ref("");

        // 用於選擇的規格二
        const selectedSpecTwo = ref("");

        // 計算屬性：過濾出符合選擇的規格一的規格二清單
        const filteredSpecTwos = computed(() => {
                const specOne = props.product.specs.specOnes.find((specOne) => specOne.specOneID === selectedSpecOne.value);
                return specOne && specOne.specTwoNames.length ? specOne.specTwoNames[0]?.specTwos : [];
        });

        // 計算屬性：過濾出符合選擇的規格一的規格二名字
        const filteredSpecTwoName = computed(() => {
                const specOne = props.product.specs.specOnes.find((specOne) => specOne.specOneID === selectedSpecOne.value);
                return specOne && specOne.specTwoNames.length ? specOne.specTwoNames[0]?.specTwoName : null;
        })

        // 當規格一改變時
        function specOneChange(){
                selectedSpecTwo.value = null; // 清空選擇的規格二
        }

        // 元件渲染完畢後初始化 Boottrap tooltip; 計算規格下拉式清單
        onMounted(function(){
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                        return new Tooltip(tooltipTriggerEl)
                })
        })

        // 預設 Bootstrap tooltip 商品庫存訊息
        const alert = `該產品庫存剩餘${props.product.stock}件`;
        
        // 使用 ref 綁定數量 INPUT DOM 物件
        const quantityDOM = ref(null);

        // 雙向綁定數量 INPUT,預設值為 1
        const quantity = ref(1)

        // 自定義 switch flag 存放數量 INPUT 是否超過商品庫存量,預設 false
        const exceed = ref(false);
        
        // 數量 INPUT 被鎖定時先取得修改前的值
        var focusQt;
        function focus(){
                focusQt = quantity.value;
        }

        // 數量 INPUT 被修改後檢查合法性,否則就恢復至 focusValue
        function checkQt(){
                exceed.value = false;
                let newQt = parseInt(quantity.value);
                if(!Number.isInteger(newQt) || newQt <1){
                        quantity.value = focusQt;
                        return;
                }else if(newQt > props.product.stock){
                        quantity.value = props.product.stock;
                        exceed.value = true;
                        Tooltip.getInstance(quantityDOM.value).show();
                        setTimeout(()=>Tooltip.getInstance(quantityDOM.value).hide(),1200);
                }else{
                        quantity.value = newQt;
                }
        }

        // 數量增加按鈕
        function addOne(){
                exceed.value = false;
                let newQt = parseInt(quantity.value);
                if(newQt < props.product.stock){
                        newQt++
                        quantity.value = newQt;
                }else{
                        quantity.value = props.product.stock;
                        exceed.value = true;
                        Tooltip.getInstance(quantityDOM.value).show();
                        setTimeout(()=>Tooltip.getInstance(quantityDOM.value).hide(),1200);
                }
        }

        // 數量減少按鈕
        function removeOne(){
                exceed.value = false;
                let newQt = parseInt(quantity.value);
                if(newQt > 1){
                        newQt--;
                        quantity.value = newQt;
                }
        }
</script>

<style scoped></style>