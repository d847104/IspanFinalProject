<template>
    <h3>&nbsp;</h3>


    <div class="row">
        <div class=" col-md-12">
            <ul class="list-group list-group-horizontal-md list-group-item-secondary">
                <li @click="showPopular = false; showDir=false; callSearch()" class="btn btn-outline-secondary col-md-3">綜合排序</li>
                <li @click="showPopular = false; showDir=true; callSearch()" class="btn btn-outline-secondary col-md-3">新上市</li>
                <li @click="showPopular = true;  getPopular()" class="btn btn-outline-secondary col-md-3">最熱銷</li>
                <li class="btn btn-outline-secondary col-md-3">
                    <select class="form-select" aria-label="Default select example" name="price" id="price" @change="sortProducts($event)">
                        <option value="priceHigh">價格:高到低</option>
                        <option value="priceLow">價格:低到高</option>
                    </select>
                </li>
            </ul>

            <div class="col-lg-12">
                <ProductList v-if="!showPopular" v-for="item in products" :key="item.productID" :item="item"></ProductList>
                <ProductList v-if="showPopular" v-for="item in products" :key="item.productID" :item="item"></ProductList>
            </div>
            <div>
                <Paginate :first-last-button="true" first-button-text="&lt;&lt;" last-button-text="&gt;&gt;"
                prev-text="&lt;" next-text="&gt;"
                :page-count="pages"
                :initial-page="current"
                v-model = "current"
                :click-handler="callSearch">
            </Paginate>
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
import { ref, onMounted,  onUnmounted} from 'vue';
import { nextTick } from 'vue';
import emitter from '@/plugins/events';
import Paginate from 'vuejs-paginate-next';

const products = ref([]);
const showPopular = ref(false); // 狀態變量用於切換顯示的產品列表
const total = ref(0)
const current = ref(1)
const pages = ref(0)
const start = ref(0)
const rows = ref(10)
const showDir = ref(false)
const lastPageRows = ref(0)
const findName = ref(""); //模糊查詢搜尋字串
const receivedEvent = ref(false); // 增加的狀態變量

onMounted(async() => {
    emitter.on('result', receiveMessage);
    await nextTick();
    if (!receivedEvent.value) {
        callSearch();
    }
});
const receiveMessage = (msg) => {
    console.log("收到的訊息",msg);
    products.value = msg;
    receivedEvent.value = true; // 接收到事件时设置为 true

};

onUnmounted(() => {
    emitter.off('result', receiveMessage);
});


async function getPopular() {
    let requestPopular = {
        "start": start.value,
        "max": rows.value,
        "dir": showDir.value,
        "order": "productID"
    }

    try {
        const response = await axiosapi.post("/api/products/popular",requestPopular);
        products.value = response.data.list;

    } catch(error) {
        console.error('Error fetching popular products:', error);
    }
}

function sortProducts(event) {
    const order = event.target.value;
    const sortedProducts = [...products.value];
    if (order === 'priceHigh') {
        sortedProducts.sort((a, b) => b.price - a.price);
    } else if (order === 'priceLow') {
        sortedProducts.sort((a, b) => a.price - b.price);
    }
    products.value = sortedProducts;
}


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
        "dir": showDir.value,
        "order": "productID",
        "name": "",
    };
    axiosapi.post("/api/products/search", request).then(function (response) {
        products.value = response.data.list;
        total.value = response.data.count;
        pages.value = Math.ceil(total.value / rows.value);
        lastPageRows.value = total.value % rows.value;

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