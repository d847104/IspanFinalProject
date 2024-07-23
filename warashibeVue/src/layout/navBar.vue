<template>
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <div class="container-fluid">
                        <div class="collapse navbar-collapse" id="navbarNavDropdown">
                                <ul class="navbar-nav">
                                        <li class="nav-item dropdown" v-for="mainCategory in mainCategories">
                                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">{{mainCategory.mainCategory}}</a>
                                                <template v-for="subCategory in subCategories">
                                                        <ul class="dropdown-menu" v-if="subCategory.mainCategory == mainCategory.mainCategoryID">
                                                                <li><a class="dropdown-item" href="#">{{subCategory.subCategory}}</a></li>
                                                        </ul>
                                                </template>
                                        </li>
                                </ul>
                        </div>
                </div>
        </nav>
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

<style>

</style>