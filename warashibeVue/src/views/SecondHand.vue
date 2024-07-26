<template>
        <div>
            <compCard v-for="product in products" :key="product.productID" :product="product"></compCard>
        </div>
        <!-- <InfiniteLoading @infinite="load" /> -->
</template>

<script setup>
    import { ref, onMounted, onUpdated } from 'vue';
    import compCard from '@/components/compCard.vue'
    import callFind from '@/plugins/product_find';
    import InfiniteLoading from 'v3-infinite-loading';
    import 'v3-infinite-loading/lib/style.css';

    const products = ref([]);
    const start = ref(0);
    const rows = ref(8);
    const currentPage = ref(0);
    const totalPages = ref(0);
    const total = ref(0);
    const lastPageRows = ref(0);

    onMounted(function() {
        callFind(products,start,rows,currentPage,totalPages,total,lastPageRows);
    })

    var viewHeight = 180;
    onUpdated(()=>{
        const cards = document.querySelectorAll(".card");
        var r;
        cards.forEach(card=>{
            r = Math.floor(Math.random()*viewHeight);
            card.style.margin = r + "px";
        })
    })
    console.log(products.length)
    const load = async $state => {
        console.log("loading...");
        viewHeight += 180;
        callFind(products,start,rows,currentPage,totalPages,total,lastPageRows,currentPage + 1);
        if(products.value.length < rows.value) {$state.complete();}
        else{$state.loaded();}
    }
</script>

<style scoped>
    .card {float: left;}
</style>