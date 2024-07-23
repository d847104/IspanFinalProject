<template>
    <h3>Product Table</h3>


    <div class="container col-md-12" >
        <ul class="list-group list-group-horizontal-md list-group-item-secondary">
            <li class="btn btn-outline-secondary col-md-3">綜合排序</li>
            <li class="btn btn-outline-secondary col-md-3">新上市</li>
            <li class="btn btn-outline-secondary col-md-3">最熱銷</li>
            <li class="btn btn-outline-secondary col-md-3">
                <select class="form-select" aria-label="Default select example" name="price" id="price">
                    <option value="priceHigh">價格:高到低</option>
                    <option value="priceLow">價格:低到高</option>
                </select>    
            </li>
        </ul>
    </div>

    <br />
    <br />


    <!-- v-for="item in products" :key="item.id" :product="item" -->
    <div class="row">
        <ProductList></ProductList>
        <ProductList></ProductList>
        <ProductList></ProductList>
        <ProductList></ProductList>
        <ProductList></ProductList>
        <ProductList></ProductList>
        <ProductList></ProductList>
        <ProductList></ProductList>
        <ProductList></ProductList>
    </div>

</template>

<script setup>
import ProductList from '@/components/ProductList.vue';



</script>

<style>

</style>