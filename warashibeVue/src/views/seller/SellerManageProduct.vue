<template>
<div class="container">
<div class="seller-manage-product">
    <h1>上架商品</h1>
    <div class="product-list">
    <div class="product-item" v-for="product in onSaleProducts" :key="product.productID">
        <div class="product-info">
        <img :src="product.imageUrl || '/default-product.png'" alt="商品圖片" class="product-image" />
        <div class="product-details">
            <p>商品名稱: {{ product.productName }}</p>
            <div class="quantity">
            數量: 
            <button @click="decreaseQuantity(product)">-</button>
            <input type="number" v-model.number="product.stock" />
            <button @click="increaseQuantity(product)">+</button>
            </div>
            <div class="price">
            定價: 
            <button @click="decreasePrice(product)">-</button>
            <input type="number" v-model.number="product.price" />
            <button @click="increasePrice(product)">+</button>
            </div>
            <p>已售出量: {{ product.sold }}</p>
            <p>總數量: {{ product.stock }}</p>
        </div>
        </div>
        <input type="file" @change="uploadImage(product, $event)"/>
        <button class="action-button" @click="takeOffProduct(product)">下架</button>
        <button class="action-button" @click="deleteProduct(product)">刪除</button>
    </div>
    </div>
    <hr />
    <h1>下架商品</h1>
    <div class="product-list">
    <div class="product-item" v-for="product in offSaleProducts" :key="product.productID">
        <div class="product-info">
        <img :src="product.imageUrl || '/default-product.png'" alt="商品圖片" class="product-image" />
        <div class="product-details">
            <p>商品名稱: {{ product.productName }}</p>
            <div class="quantity">
            數量: 
            <button @click="decreaseQuantity(product)">-</button>
            <input type="number" v-model.number="product.stock" />
            <button @click="increaseQuantity(product)">+</button>
            </div>
            <div class="price">
            定價: 
            <button @click="decreasePrice(product)">-</button>
            <input type="number" v-model.number="product.price" />
            <button @click="increasePrice(product)">+</button>
            </div>
            <p>未售出量: {{ product.unsold }}</p>
            <p>總數量: {{ product.stock }}</p>
        </div>
        </div>
        <input type="file" @change="uploadImage(product, $event)" />
        <button class="action-button" @click="putOnProduct(product)">上架</button>
        <button class="action-button" @click="deleteProduct(product)">刪除</button>
    </div>
    </div>
</div>
</div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import axiosapi from '@/plugins/axios';

const user = inject('user');
const onSaleProducts = ref([]);
const offSaleProducts = ref([]);

const fetchMemberProducts = async () => {
if (user.value) {
    try {
    const response = await axiosapi.get(`/api/products/member/${user.value.id}`);
    const products = response.data.list || [];
    
    // 获取图片并存储在产品对象中
    for (let product of products) {
        if (product.productImgs.length > 0) {
        const imgID = product.productImgs[0];
        product.imageUrl = await fetchProductImage(imgID);
        } else {
        product.imageUrl = '/default-product.png';
        }
    }

    onSaleProducts.value = products.filter(product => product.productStatus);
    offSaleProducts.value = products.filter(product => !product.productStatus);
    } catch (error) {
    console.error('Error fetching products:', error);
    }
}
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

const decreaseQuantity = (product) => {
if (product.stock > 0) product.stock--;
updateProduct(product);
};

const increaseQuantity = (product) => {
product.stock++;
updateProduct(product);
};

const decreasePrice = (product) => {
if (product.price > 0) product.price--;
updateProduct(product);
};

const increasePrice = (product) => {
product.price++;
updateProduct(product);
};

const takeOffProduct = (product) => {
product.productStatus = false;
updateProduct(product);
};

const putOnProduct = (product) => {
product.productStatus = true;
updateProduct(product);
};

const deleteProduct = async (product) => {
try {
    await axiosapi.delete(`/api/products/${product.productID}`);
    fetchMemberProducts(); // 刷新列表
} catch (error) {
    console.error('Error deleting product:', error);
}
};

const updateProduct = async (product) => {
try {
    const updatedProduct = {
    productName: product.productName,
    price: product.price,
    stock: product.stock,
    productStatus: product.productStatus
    };
    await axiosapi.put(`/api/products/${product.productID}`, updatedProduct);
} catch (error) {
    console.error('Error updating product:', error);
}
};

const uploadImage = async (product, event) => {
const file = event.target.files[0];
if (file) {
    const formData = new FormData();
    formData.append('jsonProduct', JSON.stringify({ productID: product.productID }));
    formData.append('image', file);

    try {
    await axiosapi.put(`/api/productImg/${product.productID}`, formData, {
        headers: {
        'Content-Type': 'multipart/form-data'
        }
    });
    alert('圖片上傳成功');
    fetchMemberProducts(); // 刷新列表以显示新上传的图片
    } catch (error) {
    console.error('Error uploading image:', error);
    alert('圖片上傳失敗');
    }
}
};

onMounted(fetchMemberProducts);
</script>

<style scoped>
.seller-manage-product {
padding: 20px;
}

.product-list {
margin-bottom: 20px;
}

.product-item {
border: 1px solid #ddd;
padding: 10px;
margin-bottom: 10px;
display: flex;
align-items: center;
justify-content: space-between;
}

.product-info {
display: flex;
align-items: center;
}

.product-image {
width: 100px;
height: 100px;
margin-right: 10px;
}

.product-details {
display: flex;
flex-direction: column;
}

.quantity, .price {
display: flex;
align-items: center;
}

.quantity button, .price button {
margin: 0 5px;
}

.action-button, .upload-button {
margin-left: 10px;
}
</style>
