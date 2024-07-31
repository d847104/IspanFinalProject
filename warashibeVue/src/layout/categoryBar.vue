<template>
        <div class="category-container container">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                        <template v-for="mainCategory in mainCategories">
                        <li class="nav-item" v-if="mainCategory.subCategories.length==0">
                                <a class="nav-link" href="#">{{mainCategory.mainCategory}}</a>
                        </li>
                        <li class="nav-item dropdown" v-if="mainCategory.subCategories.length!==0">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">{{mainCategory.mainCategory}}</a>
                                <ul class="dropdown-menu">
                                <template v-for="subCategory in subCategories">
                                        <li v-if="subCategory.mainCategory == mainCategory.mainCategoryID">
                                        <a class="dropdown-item" href="#">{{subCategory.subCategory}}</a>
                                        </li>
                                </template>
                                </ul>
                        </li>
                        </template>
                </ul>
                </div>
        </div>
        </nav>
        </div>
</template>

<script setup>
import { ref } from 'vue';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

const mainCategories = ref([]);
const subCategories = ref([]);

axiosapi.get("/api/categories/mainCategory/all")
.then(function(response){
        mainCategories.value = response.data.list;
}).catch(function(error){
        console.log("navBar.vue: getMainCategory", error);
        Swal.fire({
        icon: "error",
        text: "navBar.vue: getMainCategory() 錯誤" + error.message,
        allowOutsideClick: false,
        })
});

axiosapi.get("/api/categories/subCategory/all")
.then(function(response){
        subCategories.value = response.data.list;
}).catch(function(error){
        console.log("navBar.vue: getSubCategory", error);
        Swal.fire({
        icon: "error",
        text: "navBar.vue: getSubCategory() 錯誤" + error.message,
        allowOutsideClick: false,
        })
});
</script>

<style scoped>
.category-container {
display: flex;
justify-content: center;
align-items: center;
padding: 0;
background-color: transparent;
position: sticky;
z-index: 1020;
}

.navbar {
margin: 0;
background-color: transparent;
width: 100%;
}

.nav-link, .dropdown-item {
color: black;
}

.nav-link:hover, .dropdown-item:hover {
color: #0056b3;
background-color: transparent;
}

.dropdown:hover > .dropdown-menu { display: block; }
.dropdown > .dropdown-toggle:active {
pointer-events: none;
}
</style>
