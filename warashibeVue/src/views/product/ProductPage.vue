<template>
<div class="product-page">
    <div class="container">
    <div class="row">
        <!-- 左側商品圖片區域 -->
        <div class="col-md-7">
        <div class="product-image">
            <img :src="currentImage" class="main-image" alt="Product Image" />
            <div class="image-thumbnails-wrapper">
            <button
                v-if="imageIndex > 0"
                @click="prevImages"
                class="carousel-control-prev"
            >
                <span class="carousel-control-prev-icon">&lt;</span>
            </button>
            <div class="thumbnails">
                <img
                v-for="(img, index) in displayedImages"
                :key="`${img.type}-${img.imgID || img.specID}`"
                :src="img.img || img.specImg"
                class="thumbnail"
                @mouseover="currentImage = img.img || img.specImg"
                :class="{ active: currentImage === (img.img || img.specImg) }"
                />
            </div>
            <button
                v-if="imageIndex + 5 < combinedImages.length"
                @click="nextImages"
                class="carousel-control-next"
            >
                <span class="carousel-control-next-icon">&gt;</span>
            </button>
            </div>
        </div>
        </div>
        <!-- 右側商品資訊區域 -->
        <div class="col-md-5">
        <div class="seller-info" @click="navigateToSellerPage">
            <img :src="sellerImg" class="seller-image" alt="Seller Image" />
            <div class="seller-details">
                <span class="seller-name">{{ seller.username }}</span>
                <span class="seller-intro">{{ seller.intro }}</span>
            </div>
        </div>
        <!-- 新增聊天圖示按鈕 -->
        <button class="chat-button" @click="openChat">
            <font-awesome-icon :icon="['fas', 'comments']" />
        </button>
        <h1>{{ product.productName }}</h1>
        <p class="price">NT$ {{ product.price }}</p>
        <p class="description">{{ product.description }}</p>

        <!-- 運送方式 -->
        <div class="delivery-methods" v-if="deliveryMethods.length">
            <h5>運送方式：</h5>
            <div>
            <button
                v-for="method in deliveryMethods"
                :key="method"
                class="btn btn-outline-secondary delivery-method"
            >
                {{ method }}
            </button>
            </div>
        </div>

        <!-- 付款方式 -->
        <div class="payment-methods" v-if="paymentMethods.length">
            <h5>付款方式：</h5>
            <div>
            <button
                v-for="method in paymentMethods"
                :key="method"
                class="btn btn-outline-secondary payment-method"
            >
                {{ method }}
            </button>
            </div>
        </div>

        <div class="specs" v-if="groupedSpecs && Object.keys(groupedSpecs).length">
            <div v-for="(specs, specName) in groupedSpecs" :key="specName" class="spec">
            <h5 style="margin-top: 15px;">{{ specName }}</h5>
            <div v-for="spec in specs" :key="spec.specOne || spec.specTwo" style="display: inline-block; margin-right: 10px;">
                <button
                v-if="spec.specOne"
                class="btn btn-outline-secondary"
                @click="toggleSpecOne(spec.specOne, spec.specImg, spec.specOneID)"
                @mouseover="updateCurrentImage(spec.specImg)"
                :class="{ active: selectedSpecOne === spec.specOne }"
                >
                {{ spec.specOne }}
                </button>
                <button
                v-if="spec.specTwo && selectedSpecOne"
                class="btn btn-outline-secondary"
                @click="selectSpecTwo(spec.specTwo, spec.specTwoID)"
                :class="{ active: selectedSpecTwo === spec.specTwo }"
                >
                {{ spec.specTwo }}
                </button>
            </div>
            </div>
        </div>
        <div class="quantity">
            <button @click="decreaseQuantity">-</button>
            <input type="number" v-model="quantity" />
            <button @click="increaseQuantity">+</button>
        </div>
        <div>
            <button class="btn btn-success" @click="addToFavorite">加入最愛</button>&nbsp;
            <button class="btn btn-primary" @click="addToCart">加入購物車</button>&nbsp;
            <button class="btn btn-danger" @click="buyNow">直接購買</button>
        </div>
        </div>
    </div>
    <!-- 查看更多 -->
    <div class="row mt-4">
        <div class="col-md-12">
        <h3>查看更多</h3>
        <div class="row">
            <template v-for="product in relatedProducts">
                <RelatedCard                
                v-if="product.productID != productID"
                :key="product.productID"
                :product="product"
                />
            </template>
        </div>
        <Paginate
            :page-count="pages"
            :click-handler="callFind"
            :prev-text="'&lt;'"
            :next-text="'&gt;'"
        />
        </div>
    </div>
    </div>
</div>
</template>

<script setup>
import { ref, onMounted, watch, inject } from 'vue';
import axiosapi from '@/plugins/axios';
import { useRoute, useRouter } from 'vue-router';
import RelatedCard from '@/components/RelatedCard.vue';
import Paginate from 'vuejs-paginate-next';
import Swal from 'sweetalert2';

const isLogin = inject('isLogin');
const route = useRoute();
const router = useRouter();
const productID = ref(route.query.productID);
const product = ref({});
const productImages = ref([]);
const specImages = ref([]);
const combinedImages = ref([]);
const displayedImages = ref([]);
const currentImage = ref('');
const imageIndex = ref(0);
const groupedSpecs = ref({});
const quantity = ref(1);
const selectedSpecOne = ref('');
const selectedSpecTwo = ref('');
const selectedSpecOneID = ref(null);
const selectedSpecTwoID = ref(null);
const selectedSpecImage = ref('');
const relatedProducts = ref([]);
const seller = ref({});
const sellerImg = ref('');
const total = ref(0);
const pages = ref(0);
const current = ref(1);
const rows = ref(4);
const deliveryMethods = ref([]);
const paymentMethods = ref([]);

const fetchProduct = async () => {
    resetProductData();

    const { data } = await axiosapi.get(`/api/products/${productID.value}`);
    product.value = data;
    console.log(data);
    await fetchProductImages(data.productImgs);
    if (data.specs && data.specs.specOnes) {
        await fetchSpecImages(data.specs.specOnes);
        groupSpecs(data.specs);
    }
    combineImages();
    await fetchRelatedProducts(data.subCategory);

    await fetchDeliveryMethods(); // 调用获取运送方式的方法
    await fetchPaymentMethods(); // 调用获取付款方式的方法

    await recordBrowsingHistory();
    await fetchSellerInfo(product.value.member);
};

const resetProductData = () => {
    product.value = {};
    productImages.value = [];
    specImages.value = [];
    combinedImages.value = [];
    displayedImages.value = [];
    currentImage.value = '';
    groupedSpecs.value = {};
    quantity.value = 1;
    selectedSpecOne.value = '';
    selectedSpecTwo.value = '';
    selectedSpecOneID.value = null;
    selectedSpecTwoID.value = null;
    selectedSpecImage.value = '';
};

const fetchDeliveryMethods = async () => {
    const { data } = await axiosapi.get(`/productDelivery/product/${productID.value}`);
    if (data.success) {
        deliveryMethods.value = data.list.map(item => {
            switch (item.deliveryID) {
                case 1:
                    return '中華郵政 $45';
                case 2:
                    return '7-11交貨便 $60';
                case 3:
                    return '全家店到店 $60';
                default:
                    return '未知的運送方式';
            }
        });
    }
};

const fetchPaymentMethods = async () => {
    const { data } = await axiosapi.get(`/productPayMethod/product/${productID.value}`);
    if (data.success) {
        paymentMethods.value = data.list.map(item => {
            switch (item.payMethodID) {
                case 1:
                    return 'LINE Pay';
                case 2:
                    return '藍新金流';
                case 3:
                    return '貨到付款';
                default:
                    return '未知的付款方式';
            }
        });
    }
};

const fetchSellerInfo = async (memberID) => {
    const { data } = await axiosapi.get(`/ajax/members/${memberID}`);
    if (data.list && data.list.length > 0) {
        seller.value = data.list[0];
    }
    if(seller.value.profileImg){
        sellerImg.value = `data:image/jpeg;base64,${seller.value.profileImg}`;
    }
    else{
        sellerImg.value = '/src/img/海棉寶.png';
    }
};

// 添加的openChat函数，用于打开聊天对话
const openChat = () => {
    if (!isLogin.value) {
        Swal.fire('請登入會員', '', 'warning');
        return;
    }
    // 假设聊天页面为 /chatroom，并使用 sellerID 作为参数
    router.push(`/chatroom?sellerID=${seller.value.id}`);
};

const navigateToSellerPage = () => {
    router.push(`/pages/productSellerPage?sellerID=${seller.value.id}`);
};

const recordBrowsingHistory = async () => {
    if (isLogin.value && sessionStorage.getItem("memberID")!=null && product.value.productID) {
        const history = {
        member: sessionStorage.getItem("memberID"),
        product: product.value.productID,
        browseTime: new Date().toISOString(),
        };

        try {
        await axiosapi.post('/api/browsing-history', history);
        console.log('Browsing history recorded successfully.');
        } catch (error) {
        console.error('Failed to record browsing history:', error);
        }
    }
};

const fetchRelatedProducts = async (subCategoryID) => {
    const data = {
        start: (current.value - 1) * rows.value,
        max: rows.value,
        dir: false,
        order: 'productID',
    };

    const response = await axiosapi.post(`/api/products/category/${subCategoryID}`, data);
    const products = response.data.list;

    for (let product of products) {
        const images = [];
        for (let imgID of product.productImgs) {
        const { data } = await axiosapi.get(`/api/productImg/${imgID}`);
        images.push({ imgID, img: `data:image/jpeg;base64,${data.list.img}` });
        }
        product.productImgs = images;
    }

    relatedProducts.value = products;
    total.value = response.data.count;
    pages.value = Math.ceil(total.value / rows.value);
};

const fetchProductImages = async (imageIDs) => {
    const images = [];
    for (let imgID of imageIDs) {
        const { data } = await axiosapi.get(`/api/productImg/${imgID}`);
        images.push({ imgID, img: `data:image/jpeg;base64,${data.list.img}` });
    }
    productImages.value = images;
};

const fetchSpecImages = async (specs) => {
    const images = [];
    for (let specOne of specs) {
        if (specOne.specOneImg) {
        const { data } = await axiosapi.get(`/api/specImg/${specOne.specOneImg}`);
        images.push({ specID: specOne.specOneID, specImg: `data:image/jpeg;base64,${data.list.specImg}` });
        }
    }
    specImages.value = images;
};

const combineImages = () => {
    combinedImages.value = [];

    productImages.value.forEach((img) => {
        combinedImages.value.push({ ...img, type: 'product' });
    });

    specImages.value.forEach((img) => {
        combinedImages.value.push({ ...img, type: 'spec' });
    });

    if (combinedImages.value.length > 0) {
        currentImage.value = combinedImages.value[0].img || combinedImages.value[0].specImg;
        updateDisplayedImages();
    }
};

const updateDisplayedImages = () => {
    displayedImages.value = combinedImages.value.slice(imageIndex.value, imageIndex.value + 5);
};

const prevImages = () => {
    if (imageIndex.value > 0) {
        imageIndex.value -= 1;
        updateDisplayedImages();
    }
};

const nextImages = () => {
    if (imageIndex.value + 5 < combinedImages.value.length) {
        imageIndex.value += 1;
        updateDisplayedImages();
    }
};

const updateCurrentImage = async (image) => {
    if (image) {
        try {
        const { data } = await axiosapi.get(`/api/specImg/${image}`);
        currentImage.value = `data:image/jpeg;base64,${data.list.specImg}`;
        } catch (error) {
        console.error('Failed to fetch image:', error);
        }
    }
};

const toggleSpecOne = (spec, image, specOneID) => {
    if (selectedSpecOne.value === spec) {
        selectedSpecOne.value = '';
        selectedSpecOneID.value = null;
        selectedSpecImage.value = '';
        selectedSpecTwo.value = '';
        selectedSpecTwoID.value = null;
    } else {
        selectedSpecOne.value = spec;
        selectedSpecOneID.value = specOneID;
        selectedSpecImage.value = image || '';
        selectedSpecTwo.value = '';
        selectedSpecTwoID.value = null;
    }
    quantity.value = 1;
    filterSpecs();
};

const selectSpecTwo = (spec, specTwoID) => {
    if (selectedSpecTwo.value === spec) {
        selectedSpecTwo.value = '';
        selectedSpecTwoID.value = null;
    } else {
        selectedSpecTwo.value = spec;
        selectedSpecTwoID.value = specTwoID;
    }
    quantity.value = 1;
    filterSpecs();
};

const filterSpecs = () => {
    if (selectedSpecOne.value) {
        const selectedSpec = groupedSpecs.value[product.value.specs.specOneName].find(
        (spec) => spec.specOne === selectedSpecOne.value
        );
        groupedSpecs.value[product.value.specs.specOneName] = [selectedSpec];

        const specTwoGroup = selectedSpec.specTwoNames.find(
        (specTwoName) => specTwoName.specTwoName === product.value.specs.specOnes[0].specTwoNames[0].specTwoName
        );
        groupedSpecs.value[specTwoGroup.specTwoName] = specTwoGroup.specTwos;
    } else {
        groupSpecs(product.value.specs);
    }
};

const groupSpecs = (specs) => {
    const grouped = {};
    specs.specOnes.forEach((specOne) => {
        if (!grouped[specs.specOneName]) {
        grouped[specs.specOneName] = [];
        }
        grouped[specs.specOneName].push({
        specOne: specOne.specOne,
        specOneID: specOne.specOneID,
        specImg: specOne.specOneImg,
        specTwoNames: specOne.specTwoNames,
        });

        specOne.specTwoNames.forEach((specTwoName) => {
        if (!grouped[specTwoName.specTwoName]) {
            grouped[specTwoName.specTwoName] = [];
        }
        specTwoName.specTwos.forEach((specTwo) => {
            grouped[specTwoName.specTwoName].push({
            specTwo: specTwo.specTwo,
            specTwoID: specTwo.specTwoID,
            specTwoQt: specTwo.specTwoQt,
            specTwoImg: specTwo.specTwoImg,
            });
        });
        });
    });
    groupedSpecs.value = grouped;
};

const decreaseQuantity = () => {
    if (quantity.value > 1) quantity.value -= 1;
};

const increaseQuantity = () => {
    if (product.value.specs) {
        const selectedSpec = product.value.specs.specOnes.find(
            (specOne) => specOne.specOneID === selectedSpecOneID.value
        );
    
        if (selectedSpec) {
            if (selectedSpec.specTwoNames.length === 0) {
                if (quantity.value < selectedSpec.specOneQt) {
                    quantity.value += 1;
                }
            } else {
                const selectedSpecTwo = selectedSpec.specTwoNames
                    .flatMap((specTwoName) => specTwoName.specTwos)
                    .find((specTwo) => specTwo.specTwoID === selectedSpecTwoID.value);
    
                if (selectedSpecTwo && quantity.value < selectedSpecTwo.specTwoQt) {
                    quantity.value += 1;
                }
            }
        }
    } else {
        if (quantity.value < product.value.stock) {
            quantity.value += 1;
        }
    }
};

const addToFavorite = async () => {
    if (!isLogin) {
        Swal.fire('請登入會員', '', 'warning');
        return;
    }

    const data = {
        memberID: sessionStorage.getItem("memberID"),
        productID: product.value.productID,
        sellerID: product.value.member,
    };

    try {
        await axiosapi.post('/ajax/favorite/insert', data);
        Swal.fire('成功', '已將該商品加入最愛', 'success');
    } catch (error) {
        console.error('加入最愛失敗', error);
        Swal.fire('失敗', '加入最愛失敗', 'error');
    }
};

const addToCart = async () => {
    if (!isLogin) {
        Swal.fire('請登入會員', '', 'warning');
        return;
    }

    // 如果商品有規格
    if (product.value.specs) {
        // 如果有規格一，但沒有選擇
        if (!selectedSpecOneID.value) {
            Swal.fire('失敗', '請選擇' + product.value.specs.specOneName, 'error');
            return;
        }

        // 如果有規格二，但沒有選擇
        if (product.value.specs.specOnes[0].specTwoNames.length > 0 && !selectedSpecTwoID.value) {
            Swal.fire('失敗', '請選擇完整的商品規格', 'error');
            return;
        }
    }

    const data = {
        member: sessionStorage.getItem("memberID"),
        product: product.value.productID,
        specOne: selectedSpecOneID.value || null,
        specTwo: selectedSpecTwoID.value || null,
        quantity: quantity.value,
        seller: product.value.member,
    };

    try {
        await axiosapi.post('/api/cart/create', data);
        Swal.fire('成功', '已將該商品加入購物車', 'success');
    } catch (error) {
        console.error('加入購物車失敗', error);
        Swal.fire('失敗', '加入購物車失敗', 'error');
    }
};

const buyNow = async () => {
    if (!isLogin) {
        Swal.fire('請登入會員', '', 'warning');
        return;
    }

    // 如果商品有規格
    if (product.value.specs) {
        // 如果有規格一，但沒有選擇
        if (!selectedSpecOneID.value) {
            Swal.fire('失敗', '請選擇' + product.value.specs.specOneName, 'error');
            return;
        }

        // 如果有規格二，但沒有選擇
        if (product.value.specs.specOnes[0].specTwoNames.length > 0 && !selectedSpecTwoID.value) {
            Swal.fire('失敗', '請選擇完整的商品規格', 'error');
            return;
        }
    }

    const data = {
        member: sessionStorage.getItem("memberID"),
        product: product.value.productID,
        specOne: selectedSpecOneID.value || null,
        specTwo: selectedSpecTwoID.value || null,
        quantity: quantity.value,
        seller: product.value.member,
    };

    try {
        await axiosapi.post('/api/cart/create', data);
        router.push('/cart');
    } catch (error) {
        console.error('直接購買失敗', error);
        Swal.fire('失敗', '直接購買失敗', 'error');
    }
};

const callFind = (page) => {
    current.value = page;
    fetchRelatedProducts(product.value.subCategory);
};

onMounted(fetchProduct);

watch(() => route.query.productID, (newProductID) => {
    productID.value = newProductID;
    fetchProduct();
});
</script>

<style scoped>
.product-page {
padding: 2rem;
}

.product-image {
position: relative;
}

.main-image {
width: 300px;
height: 300px;
max-height: 400px;
/* object-fit: contain; */
}

.image-thumbnails-wrapper {
display: flex;
align-items: flex-start; /* 靠左對齊 */
justify-content:flex-start;
position: relative;
margin-top: 1rem;
}

.thumbnails {
display: flex;
align-items: flex-start;
}

.thumbnail {
width: 100px;
height: 100px;
margin: 0 5px;
object-fit: contain;
cursor: pointer;
}

.thumbnail.active {
border: 2px solid #007bff;
}

.carousel-control-prev,
.carousel-control-next {
background-color: rgba(0, 0, 0, 0.5);
border: none;
color: white;
font-size: 1.5rem;
cursor: pointer;
padding: 0 5px;
display: flex;
align-items: center;
justify-content: center;
position: absolute;
top: 50%;
transform: translateY(-50%);
width: 30px;
height: 100%;
z-index: 1;
}

.carousel-control-prev {
left: 65px;
}

.carousel-control-next {
right: 65px;
}

.carousel-control-prev:hover,
.carousel-control-next:hover {
background-color: rgba(0, 0, 0, 0.7);
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
font-size: 0rem;
color: white;
}

.specs {
margin: 1rem 0;
}

.spec-img {
width: 50px;
height: 50px;
object-fit: contain;
}

.quantity {
display: flex;
align-items: center;
margin: 1rem 0;
}

.quantity button {
width: 30px;
height: 30px;
}

.quantity input {
width: 50px;
text-align: center;
margin: 0 5px;
}

.seller-info {
    display: flex;
    align-items: center;
    cursor: pointer;
    margin-bottom: 1rem;
}

.seller-image {
    width: 70px;
    height: 70px;
    border-radius: 50%;
    margin-right: 10px;
}

.seller-details {
    display: flex;
    flex-direction: column;
}

.seller-name {
    font-weight: bold;
}

.seller-intro {
    color: #666;
}

/* 新增的聊天图标按钮样式 */
.chat-button {
background: none;
border: none;
color: #007bff;
font-size: 24px;
cursor: pointer;
margin-left: 10px;
}

.delivery-methods, .payment-methods {
margin-top: 1rem;
}

.delivery-methods ul, .payment-methods ul {
padding-left: 0;
list-style: none;
}

.delivery-methods li, .payment-methods li {
margin-bottom: 0.5rem;
}

.delivery-method {
width: auto;
margin: 5px 10px 5px 0;
cursor: auto;
background-color: #007bff;
color: white;
}

.payment-method {
width: auto;
margin: 5px 10px 5px 0;
cursor: auto;
background-color: #28a745;
color: white;
}
</style>
