<template>
        <div class="container">
                <div class="my-4">
                        <h2 class="fw-bold">購物車</h2>
                </div>
                <div class="mt-20">
                        <div v-for="seller in sellerList">
                                <div class="row">
                                        <div class="col-10"><h3 class="fw-bold">賣家 {{ seller.username }}</h3></div>
                                        <div class="col">
                                                <span class="fs-4 fw-bold me-3">全選</span>
                                                <input class="form-check-input" type="checkbox" style="width: 2em; height: 2em;" :checked="isAllSelected(seller.memberID)" @change="toggleSelectSeller(seller.memberID)">
                                        </div>
                                </div>
                                <template v-for="cart in cartList" :key="cart.cartID">
                                        <compCartList v-if="cart.seller.memberID === seller.memberID" :cart="cart" @update-cart="updateCart" @remove-cart="removeCart" :is-selected="selectedCarts[cart.cartID]" v-model:isChecked="selectedCarts[cart.cartID]" v-model:isDisabled="disabledCarts[cart.cartID]">
                                        </compCartList>
                                </template>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { onMounted, ref } from 'vue';
        import axiosapi from "@/plugins/axios.js";
        import Swal from "sweetalert2";
        import compCartList from '@/components/compCartList.vue';

        // 這邊先 Hard Code 會員ID, 待加入登入功能後應實際從 httpSession 取得
        const memberId = 4;
        const sellerList = ref([]);
        const cartList = ref([]);
        const selectedCarts = ref({}); // 用於追蹤每個商品是否被選中
        const disabledCarts = ref({}); // 用於管理是否能被選取

        // 點選賣家全選/取消全選
        function toggleSelectSeller(sellerID) {
                const isSelected = isAllSelected(sellerID);
                cartList.value.forEach(cart => {
                        if (cart.seller.memberID === sellerID) {
                                selectedCarts.value[cart.cartID] = !isSelected;
                        }
                });
        }

        // 判斷某賣家的商品是否全被選中
        function isAllSelected(sellerID) {
                return cartList.value
                .filter(cart => cart.seller.memberID === sellerID)
                .every(cart => selectedCarts.value[cart.cartID]);
        }

        onMounted(function () {
                cartList.value = callCart(memberId);
        })

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
        function removeCart(cartId){
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
</script>

<style></style>