<template>
        <div class="container">
                <div class="my-4">
                        <h2 class="fw-bold">購物車</h2>
                </div>
                <div v-for="seller in sellerList" class="container p-4">
                        <div class="row">
                                <div class="col-10"><h3 class="fw-bold">賣家 {{ seller.username }}</h3></div>
                                <div class="col">
                                        <label :for="'seller'+seller.memberID" class="fs-4 fw-bold me-3">全選</label>
                                        <input class="form-check-input" type="checkbox" style="width: 2em; height: 2em;" :id="'seller'+seller.memberID"
                                        :checked="isAllSelected(seller.memberID)" 
                                        @change="toggleSelectSeller(seller.memberID,$event)" 
                                        :disabled="disableSeller[seller.memberID]"
                                        v-model="selectedSeller[seller.memberID]">
                                </div>
                        </div>
                        <template v-for="cart in cartList" :key="cart.cartID">
                                <compCartList v-if="cart.seller.memberID === seller.memberID" :cart="cart" 
                                @update-cart="updateCart" 
                                @remove-cart="removeCart" 
                                :is-selected="selectedCarts[cart.cartID]" 
                                v-model:isChecked="selectedCarts[cart.cartID]" 
                                v-model:isDisabled="disabledCarts[cart.cartID]" 
                                @upddate-chb="updateChb">
                                </compCartList>
                        </template>
                        <template v-if="checkoutBtn[seller.memberID]">
                                <div class="row d-flex justify-content-end">
                                        <div class="col-10 text-end align-self-center">
                                                <span class="fs-4 fw-bold">已選擇 {{ selectedItemCount }} 項商品</span>
                                        </div>
                                        <div class="col-2 d-flex justify-content-center">
                                                <button type="button" class="btn btn-success btn-lg" @click="checkout">結帳</button>
                                        </div>
                                </div>
                        </template>
                </div>
        </div>
</template>

<script setup>
        import { onMounted, ref, computed } from 'vue';
        import { useRouter } from 'vue-router';
        import axiosapi from "@/plugins/axios.js";
        import Swal from "sweetalert2";
        import compCartList from '@/components/compCartList.vue';
        import { inject } from 'vue';
        import emitter from '@/plugins/events';

        const router = useRouter();
        const isLogin = inject("isLogin");
        const sellerList = ref([]);
        const cartList = ref([]);
        const selectedCarts = ref({}); // 用於追蹤每個商品是否被選中
        const disabledCarts = ref({}); // 用於管理商品是否能被選取
        const disableSeller = ref({}); // 用於管理該賣家的商品是否能被全選
        const selectedSeller = ref({})
        const checkoutBtn = ref({}) // 用於管理該賣家的結帳按鈕是否顯示
        const selectedItemCount = computed(() => { // 計算被選中的商品數量
                return Object.values(selectedCarts.value).filter(isSelected => isSelected).length;
        });
        
        onMounted(function () {
                if(isLogin.value){
                        cartList.value = callCart(sessionStorage.getItem("memberID"));
                }
        })

        // 點選賣家全選/取消全選
        function toggleSelectSeller(sellerID,event) {
                const isSelected = isAllSelected(sellerID);
                cartList.value.forEach(cart => {
                        if (cart.seller.memberID === sellerID) {
                                selectedCarts.value[cart.cartID] = !isSelected;
                        }
                });
                if(event.target.checked){
                        updateChb(true,sellerID);
                        checkoutBtn.value[sellerID] = true;
                }else{
                        enableAll();
                        checkoutBtn.value[sellerID] = false;
                }
        }

        // 判斷某賣家的商品是否全被選中
        function isAllSelected(sellerID) {
                return cartList.value
                .filter(cart => cart.seller.memberID === sellerID)
                .every(cart => selectedCarts.value[cart.cartID]);
        }

        // 個別商品選取/取消選取,是否 Disabled 其他賣家的商品 checkbox 以及是否顯示結帳按鈕
        function updateChb(isChecked,selectedSellerID){
                if(isChecked){
                        cartList.value.forEach(cart => {
                                if (cart.seller.memberID !== selectedSellerID) {
                                        disabledCarts.value[cart.cartID] = !selectedCarts.value[cart.cartID];
                                        selectedCarts.value[cart.cartID] = false;
                                }
                        });
                        sellerList.value.forEach(seller => {
                                if(seller.memberID !== selectedSellerID) {
                                        disableSeller.value[seller.memberID] = true;
                                        selectedSeller.value[seller.memberID] = false
                                }
                        })
                        checkoutBtn.value[selectedSellerID] = true;
                }else{
                        if(!isAnySelected()) {
                                enableAll();
                                checkoutBtn.value[selectedSellerID] = false;
                        }
                }
        }

        // 判定是否有任何商品被選取
        function isAnySelected() {
                return cartList.value.some(cart => selectedCarts.value[cart.cartID]);
        }

        // 使所有商品可以被選取
        function enableAll() {
                cartList.value.forEach(cart => {
                        disabledCarts.value[cart.cartID] = false;
                });
                sellerList.value.forEach(seller => {
                        disableSeller.value[seller.memberID] = false;
                })
        }

        // 依會員 ID 呼叫購物車清單
        function callCart(memberId){
                axiosapi.get("/api/cart/member/"+memberId)
                .then(function(response){
                        cartList.value =  response.data.list;
                        let arr = new Array();
                        for(let i = 0; i < response.data.list.length; i++) {
                                let obj = {};
                                let key = "memberID";
                                let value = "username";
                                obj[key] = response.data.list[i].seller.memberID;
                                obj[value] = response.data.list[i].seller.username;
                                arr.push(obj)
                        }
                        let set = new Set();
                        sellerList.value = arr.filter(item => !set.has(item.memberID) ? set.add(item.memberID) : false);
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
        
        // 監聽子元件 compCartList.vue 修改購物車 TABLE
        function updateCart(cart) {
                let request = {
                        "cartID": cart.cartID,
                        "member": cart.member,
                        "product": cart.product.productID,
                        "seller": cart.seller.memberID,
                        "quantity": cart.quantity,
                        "specOne": cart.specOne!=null ? cart.specOne.specOneID : null,
                        "specTwo": cart.specTwo!=null ? cart.specTwo.specTwoID : null
                }
                axiosapi.put("/api/cart/modify",request)
                .then(function(result){
                        if(result.data.success){
                                console.log(`DB Cart ID ${cart.cartID} 修改成功,數量:${cart.quantity}`);
                        }else{
                                console.log(result.data.message);
                                Swal.fire({
                                        icon: "error",
                                        text: "購物車修改數量失敗",
                                        allowOutsideClick: false,
                                })
                        }
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

        // 監聽子元件 compCartList.vue 刪除購物車 TABLE 資料一筆
        function removeCart(cartId,memberId){
                axiosapi.delete("/api/cart/delete/"+cartId)
                .then(function(result){
                        if(result.data.success){
                                console.log("購物車商品移除成功");
                                Swal.fire({
                                        position: "center",
                                        icon: "success",
                                        title: "已移除",
                                        showConfirmButton: false,
                                        timer: 800
                                });
                                callCart(memberId)
                        }else{
                                console.log(result.data.message);
                                Swal.fire({
                                        icon: "error",
                                        text: "移除失敗",
                                        allowOutsideClick: false,
                                })
                        }
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

        // 根據 selectedCarts 的布林值返回被選取的商品資料
        function getSelectedItems() {
                return cartList.value
                        .filter(cart => selectedCarts.value[cart.cartID]) // 根據布林值過濾
                        .map(cart => ({
                                cartID: cart.cartID,
                                product: cart.product,
                                quantity: cart.quantity,
                                sellerID: cart.seller,
                                specOne: cart.specOne == null ? null : cart.specOne,
                                specTwo: cart.specTwo == null ? null : cart.specTwo,
                                specOneName: cart.specOne == null ? null : cart.specOne.specOneName.specOneName,
                                specTwoName: cart.specTwo == null ? null : cart.specTwo.specTwoName.specTwoName
                                // 可以根據需要添加其他商品資訊
                        }));
        }

        // 結帳按鈕
        function checkout() {
                Swal.fire({
                        title: "確認結帳?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "我要結帳",
                        cancelButtonText: "取消"
                }).then((result) => {
                        if (result.isConfirmed) {
                                const selectedItems = getSelectedItems();
                                router.push({name: 'Checkout'}).then(()=>{
                                        emitter.emit('result',selectedItems);
                                });
                        }
                });
        }
</script>

<style></style>