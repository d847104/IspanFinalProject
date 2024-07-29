<template>
        <nav class="navbar navbar-expand-lg bg-body-tertiary container">
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                        <ul class="navbar-nav w-100 justify-content-evenly">
                        <template v-for="mainCategory in mainCategories">
                                <li class="nav-item" v-if="mainCategory.subCategories.length==0">
                                        <a class="nav-link fs-5" href="#">{{mainCategory.mainCategory}}</a>
                                </li>
                                <li class="nav-item dropdown" v-if="mainCategory.subCategories.length!==0">
                                        <a class="nav-link dropdown-toggle fs-5 fw-bold" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">{{mainCategory.mainCategory}}</a>
                                                <ul class="dropdown-menu">
                                                        <template v-for="subCategory in subCategories">
                                                        <li v-if="subCategory.mainCategory == mainCategory.mainCategoryID">
                                                                <a class="dropdown-item fs-5 fw-bold" href="#">{{subCategory.subCategory}}</a>
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
        .dropdown:hover > .dropdown-menu {display: block;}
        .dropdown > .dropdown-toggle:active {
                /*Without this, clicking will make it sticky*/
                pointer-events: none;
        }
</style>