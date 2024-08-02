<template>

    <!-- <div class="container">
        <div class="row">
            <div class="d-flex justify-content-between mb-3">
                <div class="col-md-4">
                        <img src="" alt="賣家" class="img-fluid">
                    </div>
                    <div class="col-md-4">
                        <p v-if="user"> {{ user.intro }}</p>
                    </div>
            </div>
        </div>
    </div> -->



    <div class="container mt-5">
        <div class="row">
            <!-- 右側內容區域 -->
            <div class="col-md-9">
                <!-- 搜尋欄和賣家資訊 -->
                <div class="d-flex justify-content-between mb-4">

                    <div class="col-md-4">
                        <img src="" alt="賣家" class="img-fluid">
                    </div>
                    <div class="col-md-4">
                        <p > </p>
                    </div>
                    <div class="col-md-4">
                        <p>會員資訊</p>
                        <p>Rate: 4.5/5.0</p>
                        <p>Comments: 105</p>
                        <p>Message to Seller</p>
                    </div>
                </div>

                <hr>
                <hr>
                <!-- 商品列表 -->
                <div v-if="SellerProducts.length === 0" class="no-products">
                    <h3>查無商品</h3>
                    <br>
                </div>
                <div class="row product-list">
                    <compCard v-for="product in SellerProducts" :key="product.productID" :product="product"></compCard>




                </div>
                <!-- 分頁排序 -->

            </div>
        </div>
    </div>
</template>

<script setup>
    import compCard from '@/components/compCard.vue';
    import axiosapi from '@/plugins/axios';
    import { ref, onMounted, inject } from 'vue';
    const SellerProducts = ref([]);
    const user = inject('user');


    onMounted(function () {
        loadProductsFromLocalStorage();
        callProduct();

    });

    // 从本地存储加载数据
    const loadProductsFromLocalStorage = () => {
        const onSale = localStorage.getItem('SellerProducts');
        if (onSale) SellerProducts.value = JSON.parse(onSale);
    };


    async function callProduct() {
        // 暫時寫1號
        await axiosapi.get(`/api/products/member/${2}`).then(function (response) {
            const products = response.data.list || [];
            for (let product of products) {
                product.isEditing = false; // 默认不可编辑
                if (product.productImgs.length > 0) {
                    const imgID = product.productImgs[0];
                    product.imageUrl = fetchProductImage(imgID);
                } else {
                    product.imageUrl = '/default-product.png';
                }
            }
            SellerProducts.value = products.filter(product => product.productStatus);
            // 保存数据到本地存储
            saveProductsToLocalStorage();

        }).catch(function (error) {
            console.error('Error fetching products:', error);
        });
    }


    // 保存数据到本地存储
    const saveProductsToLocalStorage = () => {
        localStorage.setItem('SellerProducts', JSON.stringify(SellerProducts.value));

    };

    const fetchProductImage = async (imgID) => {
        try {
            const response = await axiosapi.get(`/api/productImg/${imgID}`);
            if (response.data && response.data.list && response.data.list.img) {
                return `data:image/png;base64,${response.data.list.img}`;
            }
        } catch (error) {
            console.error('Error fetching product image:', error);
        }
        return '/default-product.png';
    };











const products = ref([
    {
        name: '商品名稱1',
        description: '商品描述1',
        price: 59,
        stock: 20,
        image: 'path/to/image1.jpg',
        quantity: 1,
    },
    {
        name: '商品名稱2',
        description: '商品描述2',
        price: 69,
        stock: 15,
        image: 'path/to/image2.jpg',
        quantity: 1,
    },
    // 可以添加更多的商品
]);
</script>

<style scoped>
.product-item {
    border-bottom: 1px solid #ccc;
    padding-bottom: 10px;
    margin-bottom: 10px;
}

.container {
    justify-content: center;
}
</style>