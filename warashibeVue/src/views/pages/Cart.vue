<template>
        <div class="container">
                <h2>購物車</h2>
                <div class="row mb-4">
                        <div class="col-md-10"></div>
                        <div class="col-md-2">
                                <span class="fs-4 fw-bold me-4">全選</span>
                                <input class="form-check-input" type="checkbox" style="width: 2em; height: 2em;"
                                        value="">
                        </div>
                </div>
                <div v-for="seller in sellerList">
                        <h3>賣家 {{ seller.username }}</h3>
                        <template v-for="cart in cartList">
                                <compCartList v-if="cart.seller.memberID === seller.memberID" :key="cart.cartID" :cart="cart" @update-cart="updateCart" @remove-cart="removeCart">
                                </compCartList>
                        </template>
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
                        Swal.fire({
                                icon: "error",
                                text: "錯誤"+error.message,
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
                                console.log("購物車修改成功");
                        }else{
                                Swal.fire({
                                        icon: "error",
                                        text: "錯誤"+error.message,
                                        allowOutsideClick: false,
                                })
                        }
                })
                .catch(function(error){
                        console.log("error",error)
                        Swal.fire({
                                icon: "error",
                                text: "錯誤"+error.message,
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
                                callCart(memberId)
                        }else{
                                Swal.fire({
                                        icon: "error",
                                        text: "錯誤"+error.message,
                                        allowOutsideClick: false,
                                })
                        }
                })
                .catch(function(error){
                        console.log("error",error)
                        Swal.fire({
                                icon: "error",
                                text: "錯誤"+error.message,
                                allowOutsideClick: false,
                        })
                })
        }
</script>

<style></style>