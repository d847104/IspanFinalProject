<template>
<div class="product-page">
    <div class="container">
    <div class="row">
        <!-- 左侧商品图片区域 -->
        <div class="col-md-7">
            <div class="product-image">
                <img :src="currentImage" class="main-image" alt="Product Image" />
                <div class="image-thumbnails-wrapper">
                    <button v-if="imageIndex > 0" @click="prevImages" class="carousel-control-prev">
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
                    <button v-if="imageIndex + 5 < combinedImages.length" @click="nextImages" class="carousel-control-next">
                        <span class="carousel-control-next-icon">&gt;</span>
                    </button>
                </div>
            </div>
        </div>
        <!-- 右侧商品信息区域 -->
        <div class="col-md-5">
            <h1>{{ product.productName }}</h1>
            <p class="price">NT$ {{ product.price }}</p>
            <p class="description">{{ product.description }}</p>
            <div class="specs">
                <div v-for="(specs, specName) in groupedSpecs" :key="specName" class="spec">
                    <h5 style="margin-top:15px;">{{ specName }}</h5>
                    <div v-for="spec in specs" :key="spec.specOne || spec.specTwo" style="display:inline-block; margin-right: 10px;">
                        <button 
                            v-if="spec.specOne" 
                            class="btn btn-outline-secondary" 
                            @click="selectSpecOne(spec.specOne, spec.specImg)"
                            @mouseover="updateCurrentImage(spec.specImg)"
                            :class="{ active: selectedSpecOne.value === spec.specOne }"
                        >
                            {{ spec.specOne }}
                        </button>
                        <button 
                            v-if="spec.specTwo" 
                            class="btn btn-outline-secondary" 
                            @click="selectSpecTwo(spec.specTwo, spec.specImg)"
                            @mouseover="updateCurrentImage(spec.specImg)"
                            :class="{ active: selectedSpecTwo.value === spec.specTwo }"
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
                <RelatedCard v-for="product in relatedProducts" :key="product.productID" :product="product" />
            </div>
            <Paginate :page-count="pages" :click-handler="callFind" :prev-text="'&lt;'" :next-text="'&gt;'" />
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

const user = inject("user");
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
const productSpecs = ref([]); // 确保初始化productSpecs
const groupedSpecs = ref({});
const quantity = ref(1);
const selectedSpecOne = ref('');
const selectedSpecTwo = ref('');
const selectedSpecImage = ref('');
const relatedProducts = ref([]);
const total = ref(0);
const pages = ref(0);
const current = ref(1);
const rows = ref(4);

const fetchProduct = async () => {
    // 清空与图片相关的变量
    productImages.value = [];
    specImages.value = [];
    combinedImages.value = [];
    displayedImages.value = [];
    currentImage.value = '';

    const { data } = await axiosapi.get(`/api/products/${productID.value}`);
    product.value = data;
    await fetchProductImages(data.productImgs);
    await fetchProductSpecs(data.productSpecs);
    combineImages();
    await fetchRelatedProducts(data.subCategory);
    
    // 记录浏览历史
    await recordBrowsingHistory();
};

const recordBrowsingHistory = async () => {
    if (user.value && user.value.id && product.value.productID) {
        const history = {
            member: user.value.id,
            product: product.value.productID,
            browseTime: new Date().toISOString()
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
    productImages.value = images; // 确保是新的图像集
};

const fetchProductSpecs = async (specIDs) => {
    const specs = [];
    for (let specID of specIDs) {
        const { data } = await axiosapi.get(`/api/productSpec/${specID}`);
        specs.push(data.list);
        if (data.list.specImg) {
            specImages.value.push({ specID, specImg: `data:image/jpeg;base64,${data.list.specImg}` });
        }
    }
    productSpecs.value = specs; // 确保是新的规格数据
    groupSpecs();
};

const groupSpecs = () => {
    const grouped = {};
    const specOneSet = new Set(); // 用于追踪已添加的 specOne 值
    const specTwoSet = new Set(); // 用于追踪已添加的 specTwo 值

    productSpecs.value.forEach(spec => {
        // 处理 specOne
        if (spec.specOneName) {
            if (!grouped[spec.specOneName]) {
                grouped[spec.specOneName] = [];
            }
            if (!specOneSet.has(spec.specOne)) {
                specOneSet.add(spec.specOne);
                grouped[spec.specOneName].push({ 
                    specOne: spec.specOne,
                    specImg: spec.specImg // 添加 specImg 属性存储图片
                });
            }
        }

        // 处理 specTwo
        if (spec.specTwoName) {
            if (!grouped[spec.specTwoName]) {
                grouped[spec.specTwoName] = [];
            }
            if (!specTwoSet.has(spec.specTwo)) {
                specTwoSet.add(spec.specTwo);
                grouped[spec.specTwoName].push({ 
                    specTwo: spec.specTwo,
                    specImg: spec.specImg // 添加 specImg 属性存储图片
                });
            }
        }
    });

    groupedSpecs.value = grouped;
};

const combineImages = () => {
    combinedImages.value = [];

    productImages.value.forEach(img => {
        combinedImages.value.push({ ...img, type: 'product' }); // 标记为产品图片
    });

    specImages.value.forEach(img => {
        combinedImages.value.push({ ...img, type: 'spec' }); // 标记为规格图片
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

const updateCurrentImage = (image) => {
    if (image) {
        currentImage.value = `data:image/jpeg;base64,${image}`;
    }
};

const selectSpecOne = (spec, image) => {
    if (selectedSpecOne.value === spec) {
        selectedSpecOne.value = '';
        selectedSpecImage.value = '';
    } else {
        selectedSpecOne.value = spec;
        selectedSpecImage.value = image || ''; // 保存选择的规格图片
    }
    filterSpecs();
};

const selectSpecTwo = (spec, image) => {
    if (selectedSpecTwo.value === spec) {
        selectedSpecTwo.value = '';
        selectedSpecImage.value = '';
    } else {
        selectedSpecTwo.value = spec;
        selectedSpecImage.value = image || ''; // 保存选择的规格图片
    }
    filterSpecs();
};

const filterSpecs = () => {
    const filteredSpecs = productSpecs.value.filter(spec => {
        const matchesSpecOne = selectedSpecOne.value ? spec.specOne === selectedSpecOne.value : true;
        const matchesSpecTwo = selectedSpecTwo.value ? spec.specTwo === selectedSpecTwo.value : true;
        return matchesSpecOne && matchesSpecTwo;
    });

    groupSpecs(filteredSpecs);
};

const decreaseQuantity = () => {
    if (quantity.value > 1) quantity.value -= 1;
};

const increaseQuantity = () => {
    if (selectedSpecOne.value || selectedSpecTwo.value) {
        const selectedSpec = productSpecs.value.find(spec => {
            const matchesSpecOne = selectedSpecOne.value ? spec.specOne === selectedSpecOne.value : true;
            const matchesSpecTwo = selectedSpecTwo.value ? spec.specTwo === selectedSpecTwo.value : true;
            return matchesSpecOne && matchesSpecTwo;
        });
        if (quantity.value < selectedSpec.specQt) {
            quantity.value += 1;
        }
    } else {
        quantity.value += 1;
    }
};

const addToFavorite = async () => {
    let data = {
            memberID: user.value.id,
            productID: product.value.productID,
            sellerID: product.value.member
        }
    console.log(data);
    try {
        await axiosapi.post('/ajax/favorite/insert', {
            memberID: user.value.id,
            productID: product.value.productID,
            sellerID: product.value.member
        });
        Swal.fire('成功', '已將該商品加入最愛', 'success');
    } catch (error) {
        console.error('加入最愛失敗', error);
        Swal.fire('失敗', '加入最愛失敗', 'error');
    }
};

const addToCart = async () => {
    try {
        const selectedSpec = productSpecs.value.find(spec => {
            const matchesSpecOne = selectedSpecOne.value ? spec.specOne === selectedSpecOne.value : true;
            const matchesSpecTwo = selectedSpecTwo.value ? spec.specTwo === selectedSpecTwo.value : true;
            return matchesSpecOne && matchesSpecTwo;
        });

        await axiosapi.post('/private/pages/cart/create', {
            member: user.value.id,
            product: product.value.productID,
            productSpec: selectedSpec.specID,
            seller: product.value.sellerID,
            quantity: quantity.value
        });
        Swal.fire('成功', '已將該商品加入購物車', 'success');
    } catch (error) {
        console.error('加入購物車失敗', error);
        Swal.fire('失敗', '加入購物車失敗', 'error');
    }
};

const buyNow = async () => {
    try {
        const selectedSpec = productSpecs.value.find(spec => {
            const matchesSpecOne = selectedSpecOne.value ? spec.specOne === selectedSpecOne.value : true;
            const matchesSpecTwo = selectedSpecTwo.value ? spec.specTwo === selectedSpecTwo.value : true;
            return matchesSpecOne && matchesSpecTwo;
        });

        await axiosapi.post('/private/pages/cart/create', {
            member: user.value.id,
            product: product.value.productID,
            productSpec: selectedSpec.specID,
            seller: product.value.sellerID,
            quantity: quantity.value
        });
        router.push("/cart");
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

// 监视路由参数变化并重新获取数据
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
    width: 100%;
    max-height: 400px;
    object-fit: contain;
}

.image-thumbnails-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    margin-top: 1rem;
}

.thumbnails {
    display: flex;
    align-items: center;
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
    background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
    border: none;
    color: white; /* 白色字体 */
    font-size: 1.5rem; /* 减小字体大小 */
    cursor: pointer;
    padding: 0 5px; /* 减小按钮的内边距，缩小宽度 */
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    width: 30px; /* 确保按钮条更窄 */
    height: 100%; /* 按钮高度与缩略图一致 */
    z-index: 1; /* 保证按钮在图片前面 */
}

.carousel-control-prev {
    left: 65px; /* 控制按钮与缩略图的距离 */
}

.carousel-control-next {
    right: 65px; /* 控制按钮与缩略图的距离 */
}

.carousel-control-prev:hover,
.carousel-control-next:hover {
    background-color: rgba(0, 0, 0, 0.7); /* 悬停时背景色更深 */
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
    font-size: 0rem; /* 增大箭头图标的大小 */
    color: white; /* 白色箭头 */
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
</style>
