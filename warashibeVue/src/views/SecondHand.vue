<template>
        <div v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="10">
            <compCard v-for="product in products" :key="product.productID" :product="product"></compCard>
        </div>
</template>

<script setup>
    import { ref, onMounted, onUpdated } from 'vue';
    import compCard from '@/components/compCard.vue'
    import callFind from '@/plugins/product_find';

    const products = ref([]);
    const start = ref(0);
    const rows = ref(12);
    const currentPage = ref(0);
    const totalPages = ref(0);
    const total = ref(0);
    const lastPageRows = ref(0);

    onMounted(function() {
        callFind(products,start,rows,currentPage,totalPages,total,lastPageRows);
    })

    onUpdated(()=>{
        const cards = document.querySelectorAll(".card");
        var r;
        cards.forEach(card=>{
            r = Math.floor(Math.random()*180);
            card.style.margin = r + "px";
        })
    })

    const loadMore = async () => {
        if (busy.value || currentPage.value >= totalPages.value) return
        busy.value = true

        // 更新 start 和 currentPage 值
        start.value = currentPage.value * rows.value
        currentPage.value += 1

        try {
            // 加载更多数据
            await callFind(products, start, rows, currentPage, totalPages, total, lastPageRows)
        } catch (error) {
            console.error(error)
        } finally {
            busy.value = false
        }
    }
</script>

<style scoped>
    .card {float: left;}
</style>