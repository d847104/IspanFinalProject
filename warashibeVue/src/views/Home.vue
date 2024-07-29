<template>
    <div class="container">
        <h2 class="lh-lg">熱門商品</h2>
        <div class="row">
            <compCard v-for="product in productsPopular" :key="product.productID" :product="product"></compCard>
        </div>
        <br>
        <h2 class="lh-lg">隨機精選</h2>
        <div class="row">
            <compCard v-for="product in productsRandom" :key="product.productID" :product="product"></compCard>
        </div>
        <br>
        <h2 class="lh-lg">物換心儀</h2>
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
        import callRandom from '@/plugins/product_random';

        const productsPopular = ref([]);
        const startPopular = ref(0);
        const rowsPopular = ref(8);
        const currentPagePopular = ref(0);
        const totalPagesPopular = ref(0);
        const totalPopular = ref(0);
        const lastPageRowsPopular = ref(0);

        const productsRandom = ref([]);
        const startRandom = ref(0);
        const rowsRandom = ref(8);
        const currentPageRandom = ref(0);
        const totalPagesRandom = ref(0);
        const totalRandom = ref(0);
        const lastPageRowsRandom = ref(0);

        const productsSecHand = ref([]);
        const startSecHand = ref(0);
        const rowsSecHand = ref(8);
        const currentPageSecHand = ref(0);
        const totalPagesSecHand = ref(0);
        const totalSecHand = ref(0);
        const lastPageRowsSecHand = ref(0);
        
        onMounted(function() {
                callPopular(productsPopular,startPopular,rowsPopular,currentPagePopular,totalPagesPopular,totalPopular,lastPageRowsPopular);
                callRandom(productsRandom,startRandom,rowsRandom,currentPageRandom,totalPagesRandom,totalRandom,lastPageRowsRandom);
                callSecondHand(productsSecHand,startSecHand,rowsSecHand,currentPageSecHand,totalPagesSecHand,totalSecHand,lastPageRowsSecHand);
        })
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px; /* 可調整，根據你的設計 */
}
</style>