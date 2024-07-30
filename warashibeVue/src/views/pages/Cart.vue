<template>
        <div class="container">
                <h2>購物車</h2>
                <compCartList v-for="cart in cartList" :key="cart.cardID" :cart="cart"></compCartList>
        </div>
</template>
        
<script setup>
        import callCart from '@/plugins/cart_getlist';
        import { onMounted, ref } from 'vue';
        import compCartList from '@/components/compCartList.vue';

        const cartList = ref([]);
        const memberId = 4;
        // 這邊先 Hard Code 會員ID, 待加入登入功能後應實際從 httpSession 取得

        onMounted(function(){
                callCart(memberId,cartList)
        })
</script>
        
<style>
        
</style>