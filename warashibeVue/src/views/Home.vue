<template>
        <div class="container">
                <h5>熱門商品</h5>
                <div class="row">
                        <compCard v-for="product in productsPopular" :key="product.productID" :product="product"></compCard>
                </div>
                
                <h5>隨機精選</h5>
        
                <h5>物換心儀</h5>
                <div class="row">
                        <compSecHandCard v-for="product in productsSecHand" :key="product.productID" :product="product"></compSecHandCard>
                </div>
        </div>
</template>

<script setup>
        import { ref, onMounted } from 'vue';
        import compCard from '@/components/compCard.vue';
        import compSecHandCard from '@/components/compSecHandCard.vue';
        import callPopular from '@/plugins/product_popular.js';
        import callSecondHand from '@/plugins/product_secondhand.js';

        const productsPopular = ref([]);
        const startPopular = ref(0);
        const rowsPopular = ref(8);
        const currentPagePopular = ref(0);
        const totalPagesPopular = ref(0);
        const totalPopular = ref(0);
        const lastPageRowsPopular = ref(0);

        const productsSecHand = ref([]);
        const startSecHand = ref(0);
        const rowsSecHand = ref(8);
        const currentPageSecHand = ref(0);
        const totalPagesSecHand = ref(0);
        const totalSecHand = ref(0);
        const lastPageRowsSecHand = ref(0);
        
        onMounted(function() {
                callPopular(productsPopular,startPopular,rowsPopular,currentPagePopular,totalPagesPopular,totalPopular,lastPageRowsPopular);
                callSecondHand(productsSecHand,startSecHand,rowsSecHand,currentPageSecHand,totalPagesSecHand,totalSecHand,lastPageRowsSecHand);
        })
</script>

<style>

</style>