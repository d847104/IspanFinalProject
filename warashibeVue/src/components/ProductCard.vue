<template>
    <div class="col-lg-3 col-md-6 mb-4">
        <div class="card h-100">
            <RouterLink :to="`/product/${product.productID}`">
                <img class="card-img-top" :src="`${path}${product.productID}`" :alt="product.productName">
            </RouterLink>
            <div class="card-body">
                <h5 class="card-title">
                    <RouterLink :to="`/product/${product.productID}`">{{ product.productName }}</RouterLink>
                </h5>
                <p class="card-text text-danger">NT$ {{ product.price }}</p>
                <p class="card-text">{{ product.description }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
    const props = defineProps(["product"]);
    const path = import.meta.env.VITE_PHOTO_URL
</script>

<style scoped>
.card-img-top {
    height: 150px;
    object-fit: cover;
}
.card-title a {
    text-decoration: none;
    color: inherit;
}
.card-title a:hover {
    text-decoration: underline;
}
</style>
