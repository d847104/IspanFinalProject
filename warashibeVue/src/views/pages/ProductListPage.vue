<template>
    <h3>&nbsp;</h3>


    <div class="row">

        <div class="col-md-3">
            <div class="filter-sidebar border p-3">
                <h4>篩選</h4>
                <!-- Price Filter -->
                <div class="mb-3">
                    <h5>價格篩選</h5>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="price1">
                        <label class="form-check-label" for="price1">
                            $0 - $50
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="price2">
                        <label class="form-check-label" for="price2">
                            $50 - $100
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="price3">
                        <label class="form-check-label" for="price3">
                            $100 - $200
                        </label>
                    </div>
                </div>


                <!-- Brand Filter -->
                <div class="mb-3">
                    <h5>品牌篩選</h5>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="brand1">
                        <label class="form-check-label" for="brand1">
                            品牌 A
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="brand2">
                        <label class="form-check-label" for="brand2">
                            品牌 B
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="brand3">
                        <label class="form-check-label" for="brand3">
                            品牌 C
                        </label>
                    </div>
                </div>


                <!-- Category Filter -->
                <div class="mb-3">
                    <h5>分類篩選</h5>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="category1">
                        <label class="form-check-label" for="category1">
                            分類 A
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="category2">
                        <label class="form-check-label" for="category2">
                            分類 B
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="category3">
                        <label class="form-check-label" for="category3">
                            分類 C
                        </label>
                    </div>
                </div>
            </div>
        </div>

        

        <div class=" col-md-9">
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

            <br />

            <div class="col-lg-12">
                <ProductList v-for="item in products" :key="item.productID" :item="item"></ProductList>
            </div>
        </div>
    </div>


    <br />
    <br />



</template>

<script setup>
import ProductList from '@/components/ProductList.vue';
import axiosapi from '@/plugins/axios.js';
import swal from 'sweetalert2';
import { ref, onMounted } from 'vue';
// const item = ref({});
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

    console.log("page頁數", page);
    if (page) {
        start.value = (page - 1) * rows.value;
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

    axiosapi.post("/api/products/search", request).then(function (response) {
        products.value = response.data.list;
        console.log(products.value);
        total.value = response.data.count;
        console.log("total", total.value);
        pages.value = Math.ceil(total.value / rows.value);
        console.log("pages", pages.value);
        lastPageRows.value = total.value % rows.value;
        console.log("lastPageRows", lastPageRows.value);

    }).catch(function (error) {
        swal.fire({
            text: "查詢失敗：" + error.message,
            icon: "error"
        });
    });

}


</script>

<style>
.filter-sidebar {
    max-width: 280px;
}
</style>