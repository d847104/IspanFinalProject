<template>
        <nav class="navbar navbar-expand-lg bg-body-tertiary container fixed-nav">
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                        <ul class="navbar-nav w-100 justify-content-evenly">
                                <template v-for="mainCategory in mainCategories" :key="mainCategory.mainCategoryID">
                                        <li class="nav-item" v-if="mainCategory.subCategories.length === 0" >
                                                <a class="nav-link fs-5" href="#">{{ mainCategory.mainCategory }}</a>
                                        </li>
                                        <li class="nav-item dropdown" v-if="mainCategory.subCategories.length !== 0" >
                                                <a class="nav-link dropdown-toggle fs-5 fw-bold" href="#" role="button"
                                                        data-bs-toggle="dropdown" aria-expanded="false">{{
                                                                mainCategory.mainCategory }}</a>
                                                <ul class="dropdown-menu">
                                                        <template v-for="subCategory in filteredSubCategories(mainCategory.mainCategoryID)" :key="subCategory.subCategoryID">
                                                                <li
                                                                        v-if="subCategory.mainCategory == mainCategory.mainCategoryID">
                                                                        <a class="dropdown-item fs-5 fw-bold"
                                                                                @click="postSubcategory(subCategory.subCategoryID)">{{ subCategory.subCategory
                                                                                }}</a>
                                                                </li>
                                                        </template>
                                                </ul>
                                        </li>
                                </template>
                        </ul>
                </div>
        </nav>
</template>

<script setup>
import { ref,onMounted, nextTick } from 'vue';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';
import emitter from '@/plugins/events';
import { useRouter } from 'vue-router'
const mainCategories = ref([]);
const subCategories = ref([]);
const router = useRouter();

onMounted(async () => {
        try {
                let mainCategoryResponse = await axiosapi.get("/api/categories/mainCategory/all");
                mainCategories.value = mainCategoryResponse.data.list;

                let subCategoryResponse = await axiosapi.get("/api/categories/subCategory/all");
                subCategories.value = subCategoryResponse.data.list;
        } catch (error) {
                console.log("Error fetching categories", error);
                Swal.fire({
                        icon: "error",
                        text: "Error fetching categories: " + error.message,
                        allowOutsideClick: false,
                });
        }
});

function filteredSubCategories(mainCategoryID) {
        return subCategories.value.filter(subCategory => subCategory.mainCategory == mainCategoryID);
}
async function postSubcategory(subcategoryID) {
        console.log(subcategoryID);

        let requestSubCategory = {
                "start": 0,
                "max": 30,
                "dir": false,
                "order": "productID"             
        }

        try {
                let response = await axiosapi.post(`/api/products/category/${subcategoryID}`, requestSubCategory);
                router.push("/pages/productListPage").then(() => {
                        emitter.emit("result", response.data.list);
                });


        } catch (error) {
                console.log("Error posting subcategory", error);
                Swal.fire({
                        icon: "error",
                        text: "Error posting subcategory: " + error.message,
                        allowOutsideClick: false,
                });
        }
}

</script>

<style scoped>
.dropdown:hover>.dropdown-menu {
        display: block;
}

.dropdown>.dropdown-toggle:active {
        /*Without this, clicking will make it sticky*/
        pointer-events: none;
}

.fixed-nav {
        top: 111px;
        /* 設定在 navbar 以下的高度 */
        background-color: #FCFCFC;
        /* 設定背景顏色以匹配 .navbar */
}
</style>