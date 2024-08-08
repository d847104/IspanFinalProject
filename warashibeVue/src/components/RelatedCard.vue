<template>
<div class="col-lg-3 col-md-6 mb-4">
    <div class="card h-100 rounded-3" v-show="product.productStatus">
        <img :src="productImageSrc" class="card-img-top" :alt="product.productName" @click="viewProductDetail" />
        <div class="card-body">
            <h5 class="card-title">{{ product.productName }}</h5>
            <p class="card-text text-danger text-end">NT$ {{ product.price }}</p>
            <div class="specs" v-if="product.specs && product.specs.specOnes && product.specs.specOnes.length">
                <select class="form-select" v-model="selectedSpecOneID" @change="filterSpecTwos">
                    <option value="">{{ selectPlaceholder }}</option>
                    <option v-for="specOne in product.specs.specOnes" :key="specOne.specOneID" :value="specOne.specOneID">
                        {{ specOne.specOne }}
                    </option>
                </select>
                <select class="form-select" v-if="filteredSpecTwos.length" v-model="selectedSpecTwoID">
                    <option value="">{{ selectPlaceholder }}</option>
                    <option v-for="specTwo in filteredSpecTwos" :key="specTwo.specTwoID" :value="specTwo.specTwoID">
                        {{ specTwo.specTwo }}
                    </option>
                </select>
            </div>
            <div class="action-buttons">
                <a href="#" @click="addToCart" class="btn btn-link btn-sm">
                    <font-awesome-icon :icon="['fas', 'cart-plus']" size="2x" pull="left" />
                </a>
                <div class="input-group">
                    <button class="btn btn-outline-secondary" type="button" @click="decreaseQuantity">
                        <font-awesome-icon :icon="['fas', 'minus']" />
                    </button>
                    <input type="number" class="form-control text-center" v-model="quantity" />
                    <button class="btn btn-outline-secondary" type="button" @click="increaseQuantity">
                        <font-awesome-icon :icon="['fas', 'plus']" />
                    </button>
                </div>
                <a href="#" class="btn btn-link btn-sm">
                    <font-awesome-icon :icon="['far', 'heart']" size="2x" pull="right" beat style="color:lightcoral;--fa-beat-scale: 1.0" @mouseover="(e)=>{e.target.style.setProperty('--fa-beat-scale',1.3)}" @mouseout="(e)=>{e.target.style.setProperty('--fa-beat-scale',1.0)}" @click="addToFavorite" />
                </a>
            </div>
        </div>
    </div>
</div>
</template>

<script setup>
import { ref, computed, inject } from 'vue';
import { useRouter } from 'vue-router';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

const props = defineProps(["product"]);
const router = useRouter();
const isLogin = inject("isLogin");

const selectedSpecOneID = ref('');
const selectedSpecTwoID = ref('');
const filteredSpecTwos = ref([]);
const quantity = ref(1);

const selectPlaceholder = '請選擇:';

const productImageSrc = computed(() => {
    return props.product.productImgs && props.product.productImgs.length
        ? props.product.productImgs[0].img
        : 'default-image-path'; // 替換為默認圖片的路徑
});

const viewProductDetail = () => {
    router.push(`/pages/productpage?productID=${props.product.productID}`);
};

const filterSpecTwos = () => {
    if (selectedSpecOneID.value) {
        const selectedSpecOne = props.product.specs.specOnes.find(
            (specOne) => specOne.specOneID === selectedSpecOneID.value
        );
        filteredSpecTwos.value = selectedSpecOne.specTwoNames.flatMap(specTwoName => specTwoName.specTwos);
    } else {
        filteredSpecTwos.value = [];
    }
};

const decreaseQuantity = () => {
    if (quantity.value > 1) quantity.value -= 1;
};

const increaseQuantity = () => {
    if (props.product.specs && selectedSpecOneID.value && selectedSpecTwoID.value) {
        const selectedSpecOne = props.product.specs.specOnes.find(
            (specOne) => specOne.specOneID === selectedSpecOneID.value
        );
        const selectedSpecTwo = selectedSpecOne.specTwoNames
            .flatMap(specTwoName => specTwoName.specTwos)
            .find(specTwo => specTwo.specTwoID === selectedSpecTwoID.value);

        if (selectedSpecTwo && quantity.value < selectedSpecTwo.specTwoQt) {
            quantity.value += 1;
        }
    } else if (!props.product.specs) {
        if (quantity.value < props.product.stock) {
            quantity.value += 1;
        }
    } else {
        quantity.value += 1;
    }
};

const addToCart = async () => {
    if (!isLogin.value) {
        Swal.fire('請登入會員', '', 'warning');
        return;
    }

    if (props.product.specs && (!selectedSpecOneID.value || !selectedSpecTwoID.value)) {
        Swal.fire('失敗', '請選擇完整的商品規格', 'error');
        return;
    }

    const data = {
        member: sessionStorage.getItem("memberID"),
        product: props.product.productID,
        specOne: selectedSpecOneID.value,
        specTwo: selectedSpecTwoID.value,
        quantity: quantity.value,
        seller: props.product.member,
    };

    try {
        await axiosapi.post('/api/cart/create', data);
        Swal.fire('成功', '已將該商品加入購物車', 'success');
    } catch (error) {
        console.error('加入購物車失敗', error);
        Swal.fire('失敗', '加入購物車失敗', 'error');
    }
};

const addToFavorite = async () => {
    try {
        await axiosapi.post('/ajax/favorite/insert', {
            memberID: sessionStorage.getItem("memberID"),
            productID: props.product.productID,
            sellerID: props.product.member
        });
        Swal.fire('成功', '已將該商品加入最愛', 'success');
    } catch (error) {
        console.error('加入最愛失敗', error);
        Swal.fire('失敗', '加入最愛失敗', 'error');
    }
};
</script>

<style scoped>
.little-product-card {
    cursor: pointer;
    width: 100%;
    max-width: 280px; /* 設定卡片最大寬度 */
    height: 420px; /* 設定卡片固定高度 */
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.card-img-top {
    height: 300px; /* 設定圖片固定高度 */
    object-fit: cover;
    cursor: pointer;
}

.specs {
    margin-top: 10px;
    margin-bottom: 10px;
}

.quantity-group {
    display: flex;
    align-items: center;
    margin: 10px 0;
}

.quantity-group button {
    width: 30px;
    height: 30px;
}

.quantity-group input {
    width: 50px;
    text-align: center;
    margin: 0 5px;
}

.action-buttons {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.action-buttons a {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
}
</style>
