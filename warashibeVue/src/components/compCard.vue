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
                                                <select class="form-select" v-model="selectedSpecOne" @change="specOneChange" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-trigger="manual" :title="alartSpec" ref="specDOM">
                                                        <option value="" disabled selected>請選擇{{ product.specs.specOneName }}</option>
                                                        <option v-for="specOne in product.specs.specOnes" :key="specOne.specOneID" :value="specOne.specOneID">{{ specOne.specOne }}</option>
                                                </select>
                                                <!-- 規格二(若存在) -->
<<<<<<< HEAD
                                                <template v-if="filteredSpecTwos.length">
                                                        <select class="form-select" v-model="selectedSpecTwo">
                                                        <option value="" disabled selected>請選擇{{ filteredSpecTwoName }}</option>
                                                        <option v-for="specTwo in filteredSpecTwos" :key="specTwo.specTwo" :value="specTwo.specTwoID">{{ specTwo.specTwo }}</option>
=======
                                                 <template v-if="filteredSpecTwos.length">
                                                        <select class="form-select" v-model="selectedSpecTwo" @change="specTwoChange">
                                                                <option value="" disabled selected>請選擇{{ filteredSpecTwoName }}</option>
                                                                <option v-for="specTwo in filteredSpecTwos" :key="specTwo.specTwo" :value="specTwo.specTwoID">{{ specTwo.specTwo }}</option>
>>>>>>> origin/Angel
                                                        </select>
                                                </template>
                                        </div>

                                        <div class="row mt-2">
                                                <!-- 加入購物車 -->
                                                <div class="col-3">
                                                        <button type="button" class="btn btn-link btn-sm" @click="addCart">
                                                                <font-awesome-icon :icon="['fas', 'cart-plus']" size="2x" pull="left" />
                                                        </button>
                                                        <a href="#"></a>
                                                </div>
                                                
                                                <!-- 數量按鈕群組 -->
                                                <div class="input-group col">
                                                        <!-- 數量減少按鈕 -->
                                                        <button class="btn btn-outline-secondary" type="button" @click="removeOne" :disabled="quantity===1">
                                                                <font-awesome-icon :icon="['fas', 'minus']" />
                                                        </button>
                
                                                        <!-- 數量 -->
                                                        <input type="text" class="form-control text-center" data-bs-toggle="tooltip" data-bs-placement="top" data-bs-trigger="manual" :title="alertQt" ref="quantityDOM" v-model="quantity" @change="checkQt" @focus="focus">
                                                        
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
        import { computed, onMounted, ref, watch, nextTick } from 'vue';
        import { RouterLink } from 'vue-router';
        import { Tooltip } from 'bootstrap/dist/js/bootstrap.bundle.min.js';

        // 接收父元件資料
        const props = defineProps(["product"]);

        const emits = defineEmits(["addCart"]);

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

        
        // 依據選取規格計算庫存量
        const stock = computed(() => {
                if (props.product.specs) {
                        if (selectedSpecTwo.value != null && selectedSpecTwo.value !== "") {
                                const specOne = props.product.specs.specOnes.find(specOne => specOne.specOneID === selectedSpecOne.value);
                                if (specOne) {
                                        const specTwo = specOne.specTwoNames[0].specTwos.find(specTwo => specTwo.specTwoID === selectedSpecTwo.value);
                                        return specTwo ? specTwo.specTwoQt : 0;
                                }
                        } else if (filteredSpecTwos.value.length !== 0) {
                                const specOne = props.product.specs.specOnes.find(specOne => specOne.specOneID === selectedSpecOne.value);
                                if (specOne) {
                                        return specOne.specTwoNames.reduce((acc, specTwoName) => {
                                                return acc + specTwoName.specTwos.reduce((subAcc, specTwo) => subAcc + specTwo.specTwoQt, 0);
                                        }, 0);
                                }
                        } else {
                                const specOne = props.product.specs.specOnes.find(specOne => specOne.specOneID === selectedSpecOne.value);
                                return specOne ? specOne.specOneQt : 0;
                        }
                }
                return props.product.stock;
        });
        
        // 庫存提示訊息
        const alertQt = computed(() => `該產品庫存剩餘${stock.value}件`);
        
        // 預設 Bootstrap tooltip 提示規格訊息 & 綁定要提式的下拉選單
        const alartSpec = ref("請先選擇規格");
        const specDOM = ref(null);
        
        // 使用 ref 綁定數量 INPUT DOM 物件
        const quantityDOM = ref(null);
        
        // 雙向綁定數量 INPUT,預設值為 1
        const quantity = ref(1)
        
        // 自定義 switch flag 存放數量 INPUT 是否超過商品庫存量,預設 false
        const exceed = ref(false);

        // 當規格一改變時
        function specOneChange(){
                selectedSpecTwo.value = null; // 清空選擇的規格二
                quantity.value = 1;     // 初始化數量
                exceed.value = false;
                console.log(`已選擇規格一ID${selectedSpecOne.value},庫存量剩餘${stock.value}`);
        }

        function specTwoChange(){
                quantity.value = 1;     // 初始化數量
                exceed.value = false;
                console.log(`已選擇規格二ID${selectedSpecTwo.value},庫存量剩餘${stock.value}`);
        }

        // 初始化 Bootstrap tooltip
        function initializeTooltips() {
                const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
                tooltipTriggerList.forEach(tooltipTriggerEl => {new Tooltip(tooltipTriggerEl);});
        }

        // 元件渲染完畢後初始化 Bootstrap tooltip; 計算規格下拉式清單
        onMounted(async() => {
                await nextTick(); // 等待 DOM 更新
                initializeTooltips();
        })

        // 更新 tooltips 內容
        watch(alertQt, () => {
                nextTick(() => {
                        const tooltip = Tooltip.getInstance(quantityDOM.value);
                        if (tooltip) {
                                tooltip.setContent({ '.tooltip-inner': alertQt.value });
                        }
                });
        });
        
        // 數量 INPUT 被鎖定時先取得修改前的值
        var focusQt;
        function focus(event){
                if(props.product.specs){
                        if (selectedSpecOne.value == "" || selectedSpecOne.value == null){
                                event.target.blur();
                                Tooltip.getInstance(specDOM.value).show();
                                setTimeout(()=>Tooltip.getInstance(specDOM.value).hide(),1200);
                                event.preventDefault();
                        } else if (filteredSpecTwos.value.length != 0){
                                if (selectedSpecTwo.value == "" || selectedSpecTwo.value == null){
                                        event.target.blur();
                                        Tooltip.getInstance(specDOM.value).show();
                                        setTimeout(()=>Tooltip.getInstance(specDOM.value).hide(),1200);
                                        event.preventDefault();
                                }
                        }
                }
                focusQt = quantity.value;
        }

        // 數量 INPUT 被修改後檢查合法性,否則就恢復至 focusValue
        function checkQt(){
                exceed.value = false;
                let newQt = parseInt(quantity.value);
                if(!Number.isInteger(newQt) || newQt <1){
                        quantity.value = focusQt;
                        return;
                }else if(newQt > stock.value){
                        quantity.value = stock.value;
                        exceed.value = true;
                        Tooltip.getInstance(quantityDOM.value).show();
                        setTimeout(()=>Tooltip.getInstance(quantityDOM.value).hide(),1200);
                }else{
                        quantity.value = newQt;
                }
                console.log(`數量:${quantity.value}`);
        }

        // 數量增加按鈕
        function addOne(){
                if(props.product.specs){
                        if (selectedSpecOne.value == "" || selectedSpecOne.value == null){
                                Tooltip.getInstance(specDOM.value).show();
                                setTimeout(()=>Tooltip.getInstance(specDOM.value).hide(),1200);
                                return;
                        } else if (filteredSpecTwos.value.length != 0){
                                if (selectedSpecTwo.value == "" || selectedSpecTwo.value == null){
                                        Tooltip.getInstance(specDOM.value).show();
                                        setTimeout(()=>Tooltip.getInstance(specDOM.value).hide(),1200);
                                        return;
                                }
                        }
                }
                exceed.value = false;
                let newQt = parseInt(quantity.value);
                if(newQt < stock.value){
                        newQt++
                        quantity.value = newQt;
                }else{
                        quantity.value = stock.value;
                        exceed.value = true;
                        Tooltip.getInstance(quantityDOM.value).show();
                        setTimeout(()=>Tooltip.getInstance(quantityDOM.value).hide(),1200);
                }
                console.log(`數量:${quantity.value}`);
        }

        // 數量減少按鈕
        function removeOne(){
                exceed.value = false;
                let newQt = parseInt(quantity.value);
                if(newQt > 1){
                        newQt--;
                        quantity.value = newQt;
                }
                console.log(`數量:${quantity.value}`);
        }

        // 加入購物車
        function addCart(){
                if(props.product.specs){
                        if (selectedSpecOne.value == "" || selectedSpecOne.value == null){
                                Tooltip.getInstance(specDOM.value).show();
                                setTimeout(()=>Tooltip.getInstance(specDOM.value).hide(),1200);
                                return;
                        } else if (filteredSpecTwos.value.length != 0){
                                if (selectedSpecTwo.value == "" || selectedSpecTwo.value == null){
                                        Tooltip.getInstance(specDOM.value).show();
                                        setTimeout(()=>Tooltip.getInstance(specDOM.value).hide(),1200);
                                        return;
                                }
                        }
                }
                emits("addCart",
                props.product.productID,props.
                product.member,
                selectedSpecOne.value == null || selectedSpecOne.value == "" ? null : selectedSpecOne.value,
                selectedSpecTwo.value == null || selectedSpecTwo.value == "" ? null : selectedSpecTwo.value,
                quantity.value)
        }
</script>

<style scoped></style>