<template>
        <div class="card mb-3 col">
                <div class="row g-0">
                        <div class="col-md-3">
                                <img :src="path" class="img-fluid rounded-start" :alt="cart.product.productName">
                        </div>
                        <div class="col-md-7 align-self-center">
                                <div class="card-body">
                                        <h4 class="card-title">{{ cart.product.productName }}</h4>
                                        <p class="card-text text-end fs-4">
                                                <template v-if="cart.productSpec">
                                                        <div class="row mt-2" v-if="cart.productSpec.specOneName">
                                                                <span class="col">{{ cart.productSpec.specOneName }}</span>
                                                                <div class="input-group col justify-content-center">
                                                                        {{ cart.productSpec.specOne }}
                                                                </div>
                                                        </div>
                                                        <div class="row mt-2" v-if="cart.productSpec.specTwoName">
                                                                <span class="col"> {{ cart.productSpec.specTwoName }}</span>
                                                                <div class="input-group col justify-content-center">
                                                                        {{ cart.productSpec.specTwo }}
                                                                </div>
                                                        </div>
                                                </template>
                                                <div class="row mt-2">
                                                        <label :for="cart.product.productID+'Qt'" class="col">
                                                                數量
                                                        </label>
                                                        <div class="input-group col">
                                                                <button class="btn btn-outline-secondary" type="button" @click="removeOne" :disabled="cart.quantity===1">
                                                                        <font-awesome-icon :icon="['fas', 'minus']" />
                                                                </button>
                                                                <input type="text" class="form-control text-center" placeholder="" :value="cart.quantity" :id="cart.product.productID+'Qt'" @change="checkQt">
                                                                <button class="btn btn-outline-secondary" type="button" @click="addOne" :style="[exceed?'border-color: var(--bs-dark-border-subtle)':'']">
                                                                        <span data-bs-toggle="tooltip" data-bs-placement="top" :title="alert" v-show="exceed" style="color: var(--bs-dark-border-subtle);">
                                                                                <font-awesome-icon :icon="['fas', 'plus']" />
                                                                        </span>
                                                                        <span v-show="!exceed">
                                                                                <font-awesome-icon :icon="['fas', 'plus']" />
                                                                        </span>
                                                                </button>
                                                        </div>
                                                </div>
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
                        <div class="card-body col-md-1 align-self-stretch d-flex align-items-center justify-content-center" style="background-color: var(--bs-light);">
                                <div class="form-check">
                                        <input class="form-check-input" type="checkbox" style="width:2em; height:2em;" value="" :id="cart.product.productID+'ck'">
                                </div>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { computed, onMounted, ref } from 'vue';
        import { Tooltip } from 'bootstrap/dist/js/bootstrap.bundle.min.js';
        import Swal from 'sweetalert2';
        import modifyCart from '@/plugins/cart_modify';
        import removeCart from '@/plugins/cart_remove';

        const emits = defineEmits(["updateCart"])
        onMounted(function(){
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                        return new Tooltip(tooltipTriggerEl)
                })
        })

        const props = defineProps(["cart"])
        const path = computed(() => {
                if (props.cart.product.productImgs[0]) {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.cart.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })

        const alert = `該產品庫存剩餘${props.cart.product.stock}件`;
        const exceed = ref(false);
        
        function checkQt(){
                exceed.value = false;
                let productId = props.cart.product.productID
                let cartQt = parseInt(document.getElementById(productId+'Qt').value);
                if(cartQt > props.cart.product.stock){
                        props.cart.quantity = props.cart.product.stock
                        exceed.value = true;
                }else{
                        props.cart.quantity = cartQt;
                }
                modifyCart(
                        props.cart.cartID,
                        props.cart.member,
                        props.cart.product.productID,
                        props.cart.productSpec!=null ? props.cart.productSpec.specID : null,
                        props.cart.seller.memberID,
                        props.cart.quantity
                )
        }

        function addOne(){
                exceed.value = false;
                let productId = props.cart.product.productID
                let cartQt = parseInt(document.getElementById(productId+'Qt').value);
                if(cartQt < props.cart.product.stock){
                        cartQt++
                        props.cart.quantity = cartQt;
                        
                }else{
                        props.cart.quantity = props.cart.product.stock
                        exceed.value = true;
                }
                modifyCart(
                        props.cart.cartID,
                        props.cart.member,
                        props.cart.product.productID,
                        props.cart.productSpec!=null ? props.cart.productSpec.specID : null,
                        props.cart.seller.memberID,
                        props.cart.quantity
                )
        }

        function removeOne(){
                exceed.value = false;
                let productId = props.cart.product.productID
                let cartQt = parseInt(document.getElementById(productId+'Qt').value);
                if(cartQt > 1){
                        cartQt--;
                        props.cart.quantity = cartQt;
                }else{
                        console.log("Remove from Cart!")
                }
                modifyCart(
                        props.cart.cartID,
                        props.cart.member,
                        props.cart.product.productID,
                        props.cart.productSpec!=null ? props.cart.productSpec.specID : null,
                        props.cart.seller.memberID,
                        props.cart.quantity
                )
        }

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
                                removeCart(props.cart.cartID)
                                Swal.fire({
                                        position: "center",
                                        icon: "success",
                                        title: "已移除",
                                        showConfirmButton: false,
                                        timer: 800
                                });
                                emits("updateCart",props.cart.member)
                        }
                });
        }
</script>

<style></style>