<template>
    <h3>Product Table</h3>


    <div class="container col-md-12">
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
        <ProductList v-for="item in products" :key="item.productID" :item="item">

        </ProductList>

    </div>

</template>

<script setup>
import ProductList from '@/components/ProductList.vue';
import axiosapi from '@/plugins/axios.js';
import swal from 'sweetalert2';
import { ref, onMounted } from 'vue';
const item = ref({});
const products = ref([]);

    const total = ref(0)
    const current = ref(1)
    const pages = ref(0)
    const start = ref(0)
    const rows = ref(30)
    const lastPageRows = ref(0)
    const findName = ref(""); //模糊查詢搜尋字串

    onMounted(function () {
        callSearch();
    });

    function callSearch(page) {

        if (findName.value === "") {
            findName.value = null;
        }

        console.log("page頁數" , page);
        if(page) {
            start.value = (page - 1)* rows.value;
            current.value = page;
        } else {
            start.value = 0;
            current.value = 1;
        }

        let request = {
            "start": start.value,
            "max": rows.value,
            "dir": false,
            "order": "productName",
            "name": ""
        };

        axiosapi.post("/api/products/search", request).then(function(response) {
            products.value = response.data.list;
            console.log(products.value);    
            total.value = response.data.count;
            console.log("total" , total.value);
            pages.value = Math.ceil(total.value / rows.value);
            console.log("pages" , pages.value);
            lastPageRows.value = total.value % rows.value;
            console.log("lastPageRows" , lastPageRows.value);

        }).catch(function(error) {
            swal.fire({
                text: "查詢失敗："+error.message,
                icon: "error"
            });
        });

    }




</script>

<style></style>