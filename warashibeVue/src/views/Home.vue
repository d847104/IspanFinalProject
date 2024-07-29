<template>
    <div class="container">
            <h5>熱門商品</h5>
            <div class="row">
                    <compCard v-for="product in productsPopular" :key="product.productID" :product="product"></compCard>
            </div>
            
            <h5>隨機精選</h5>
    
            <h5>物換心儀</h5>
            <div class="row">
                    <compSecHandCard v-for="product in productsSecHand" :key="product.productID" :product="product"></compSecHandCard>
            </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 ">
                <nav class="nav flex-column w-2 p-3 ">
                    
                    <a class="nav-link" href="#" style="color:#6C6C6C;">服飾</a>
                    <a class="nav-link" href="#" style="color:#6C6C6C;">圖書</a>
                    <a class="nav-link" href="#" style="color:#6C6C6C;">3C用品</a>
                    <a class="nav-link" href="#" style="color:#6C6C6C;">生活用品</a>
                    <a class="nav-link" href="#" style="color:#6C6C6C;">家電用品</a>
                    <a class="nav-link" href="#" style="color:#6C6C6C;">運動用品</a>
                    <a class="nav-link" href="#" style="color:#6C6C6C;">寵物用品</a>
                    <a class="nav-link active" aria-current="page" href="#" style="color:#6C6C6C;">嬰幼兒用品</a>
                    <!-- <a class="nav-link disabled" aria-disabled="true">Disabled</a> -->
                </nav>
            </div>
           
            <div class="col-md-10">
                <div class="container " >
                    <div class="text-left">
                        <h4 style="color:#6C6C6C;">熱門商品</h4>
                    </div>
                    <div class="card d-inline-flex p-2 mb-3 mx-2" style="max-width: 225px;background-color: #F0F8FF;">
                        <img src="/src/img/馬克杯8807.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>

                    <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;background-color: #F0FFFF;">
                        <img src="/src/img/馬克杯21569.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>
                    <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
                        <img src="/src/img/馬克杯36344.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>
                    <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
                        <img src="/src/img/馬克杯21569.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>
                    <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
                        <img src="/src/img/馬克杯36344.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>
                </div>
                <br>
                <div class="container">
                    <div class="text-left">
                        <h4 style="color:#6C6C6C;">推薦給您</h4>
                    </div>
                    <div class="card d-inline-flex p-2 mb-3 mx-2" style="max-width: 225px ;">
                        <img src="/src/img/馬克杯84089.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>

                    <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
                        <img src="/src/img/馬克杯88795.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>
                    <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
                        <img src="/src/img/馬克杯27117.jpg" class="card-img-top" alt="...">

                        <div class="card-body " >
                            <h5 class="card-title ">【馬克杯】</h5>
                            <p class="card-text ">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                            <div class="button-container">
                            <a href="#" class="btn btn-secondary btn-sm mx-1"><font-awesome-icon icon="fa-solid fa-list" /></a>
                            <a href="#" class="btn btn-secondary btn-sm "><font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#FFFFFF;"/></a>
                        </div></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script setup>
        import { ref, onMounted } from 'vue';
        import compCard from '@/components/compCard.vue';
        import compSecHandCard from '@/components/compSecHandCard.vue';
        import callPopular from '@/plugins/product_popular.js';
        import callSecondHand from '@/plugins/product_secondhand.js';

        const productsPopular = ref([]);
        const startPopular = ref(0);
        const rowsPopular = ref(8);
        const currentPagePopular = ref(0);
        const totalPagesPopular = ref(0);
        const totalPopular = ref(0);
        const lastPageRowsPopular = ref(0);

        const productsSecHand = ref([]);
        const startSecHand = ref(0);
        const rowsSecHand = ref(8);
        const currentPageSecHand = ref(0);
        const totalPagesSecHand = ref(0);
        const totalSecHand = ref(0);
        const lastPageRowsSecHand = ref(0);
        
        onMounted(function() {
                callPopular(productsPopular,startPopular,rowsPopular,currentPagePopular,totalPagesPopular,totalPopular,lastPageRowsPopular);
                callSecondHand(productsSecHand,startSecHand,rowsSecHand,currentPageSecHand,totalPagesSecHand,totalSecHand,lastPageRowsSecHand);
        })
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px; /* 可調整，根據你的設計 */
}
</style>