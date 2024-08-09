<template>
    <div class="container mt-5">
        <div class="row">
            <!-- 右側內容區域 -->
            <div class="col-md-12">
                <!-- 賣家資訊欄 -->
                <div class="d-flex justify-content-between mb-4">
                    <div class="col-md-3">
                        <img :src="profileImgUrl" alt="賣家" class="img-fluid profile-img">
                    </div>
                    <div class="col-md-4">
                        <div v-if="seller[0]">{{ seller[0].intro }} </div>
                    </div>
                    <div class="col-md-3">
                        <div><b>賣家資訊</b></div>
                        <div>賣家評價: {{ averageRanking }}</div>
                        <div>商品數量: {{ productTotal }}</div>
                        <!-- <div>Message to Seller</div> -->
                    </div>
                </div>

                <hr>
                <!-- 商品列表 -->
                <div v-if="SellerProducts.length === 0" class="no-products">
                    <h3>查無商品</h3>
                    <br>
                </div>
                <div class="row">
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
import { ref, onMounted, onUnmounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter();
const SellerProducts = ref([]);
const seller = ref([]);
const productTotal = ref(0);
const averageRanking = ref(0);
const profileImgUrl = ref(null);
const sellerID = ref(route.query.sellerID);

onMounted(function () {
    loadProductsFromLocalStorage();
    callProduct(sellerID.value);
});

// 从本地存储加载数据
const loadProductsFromLocalStorage = () => {
    const onSale = localStorage.getItem('SellerProducts');
    if (onSale) SellerProducts.value = JSON.parse(onSale);
};

//sellerID
async function callProduct(sellerID) {
    axiosapi.get(`/api/products/member/${sellerID}`).then(function (response) {
        //取得賣家資訊
        productTotal.value = response.data.count;
        // console.log(response.data.list[0]);
        if(response.data.list) {
            const products = response.data.list || [];
            getSellerInfo(products[0].member);
            getSellerProductsRank(products[0].member);
            for (let product of products) {
                product.isEditing = false; // 預設不可編輯
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
        }

    }).catch(function (error) {
        console.error('Error fetching products:', error);
    });
}

async function getSellerProductsRank(sellerID) {
    try {
        const response = await axiosapi.get(`/api/ranks/member/${sellerID}`);
        const rankingList = Array.isArray(response.data.list) ? response.data.list : [];
        if (rankingList.length > 0) {
            // 提取 ranking 數值並計算總和
            const totalRanking = rankingList.reduce((sum, item) => sum + item.ranking, 0);
            // 計算平均值
            averageRanking.value = totalRanking / rankingList.length;
        } else {
            averageRanking.value = 0; // 如果沒有資料，設置平均值為 0 或其他適當值
        }
    } catch (error) {
        console.error('Error getting seller products rank:', error);
        averageRanking.value = 0; // 在錯誤情況下，設置平均值為 0 或其他適當值
    }
}

async function getSellerInfo(sellerID) {
    await axiosapi.get(`ajax/members/${sellerID}`).then(function(response) {
        seller.value = response.data.list;
        if (seller.value[0] && seller.value[0].profileImg) {
            profileImgUrl.value = `data:image/png;base64,${seller.value[0].profileImg}`;
        } else {
            profileImgUrl.value = `${import.meta.env.VITE_API_URL}/images/default-person.png`;
        }
    }).catch(function(error) {
        console.error('Error get Seller:', error);
        profileImgUrl.value = `${import.meta.env.VITE_API_URL}/images/default-person.png`;
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



</script>

<style scoped>

.container{
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 50vh;
    min-width: 100vh;
    padding: 20px;
}

.profile-img {
    max-width: 100px; /* 限制圖片最大寬度 */
    max-height: 100px; /* 限制圖片最大高度 */
    object-fit: cover; /* 確保圖片在限制區域內顯示且不失真 */
}

</style>