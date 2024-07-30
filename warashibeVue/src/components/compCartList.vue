<template>
        <div class="card mb-3">
                <div class="row g-0">
                        <div class="col-md-4">
                                <img :src="path" class="img-fluid rounded-start" :alt="cart.product.productName">
                        </div>
                        <div class="col-md-8 align-self-center">
                                <div class="card-body">
                                        <h4 class="card-title">{{ cart.product.productName }}</h4>
                                        <p class="card-text text-end fs-4">
                                                <div class="row">
                                                        <label :for="cart.product.productID" class="col-7">
                                                                <template v-if="alert">
                                                                        <span class="me-4 fs-5 text-danger">
                                                                                該產品庫存剩餘 {{ cart.product.stock }} 件                
                                                                        </span>
                                                                </template>
                                                                數量
                                                        </label>
                                                        <div class="input-group col">
                                                                <button class="btn btn-outline-secondary" type="button" @click="removeOne">
                                                                        <font-awesome-icon :icon="['fas', 'minus']" />
                                                                </button>
                                                                <input type="text" class="form-control text-center" placeholder="" :value="cart.quantity" :id="cart.product.productID" @change="checkQt">
                                                                <button class="btn btn-outline-secondary" type="button" @click="addOne">
                                                                        <font-awesome-icon :icon="['fas', 'plus']" />
                                                                </button>
                                                        </div>
                                                </div>
                                        </p>
                                </div>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { computed, ref } from 'vue';
        import modifyCart from '@/plugins/cart_modify';

        const props = defineProps(["cart"])
        const path = computed(() => {
                if (props.cart.product.productImgs[0]) {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.cart.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })

        const alert = ref(false);
        
        function checkQt(event){
                alert.value = false;
                let productId = props.cart.product.productID
                let cartQt = parseInt(document.getElementById(productId).value);
                if(cartQt > props.cart.product.stock){
                        props.cart.quantity = props.cart.product.stock
                        alert.value = true;
                }
        }

        function addOne(){
                alert.value = false;
                let productId = props.cart.product.productID
                let cartQt = parseInt(document.getElementById(productId).value);
                if(cartQt < props.cart.product.stock){
                        cartQt++
                        props.cart.quantity = cartQt;
                }else{
                        props.cart.quantity = props.cart.product.stock
                        alert.value = true;
                }
        }

        function removeOne(){
                alert.value = false;
                let productId = props.cart.product.productID
                let cartQt = parseInt(document.getElementById(productId).value);
                if(cartQt > 1){
                        cartQt--;
                        props.cart.quantity = cartQt;
                }else{
                        console.log("Remove from Cart!")
                }
        }
</script>

<style></style>