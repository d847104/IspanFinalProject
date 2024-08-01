<template>
<div class="container">
    <div class="seller-manage-product">
    <h1>上架商品</h1>
    <div class="product-list">
        <div v-if="onSaleProducts.length === 0" class="no-products">
        <h3>查無商品</h3>
        <br>
        </div>
        <div class="product-item" v-for="product in onSaleProducts" :key="product.productID">
        <div class="product-info">
            <img :src="product.imageUrl || '/default-product.png'" alt="商品圖片" class="product-image" />
            <div class="product-details">
            <div>
                商品名稱:
                <input type="text" v-model="product.productName" :disabled="!product.isEditing" />
            </div>
            <div class="quantity">
                數量:
                <button @click="decreaseQuantity(product)" :disabled="!product.isEditing">-</button>
                <input type="number" v-model.number="product.stock" :disabled="!product.isEditing" />
                <button @click="increaseQuantity(product)" :disabled="!product.isEditing">+</button>
            </div>
            <div class="price">
                定價:
                <button @click="decreasePrice(product)" :disabled="!product.isEditing">-</button>
                <input type="number" v-model.number="product.price" :disabled="!product.isEditing" />
                <button @click="increasePrice(product)" :disabled="!product.isEditing">+</button>
            </div>
            <p class="quantity">總數量: {{ product.stock }}</p>
            <textarea v-model="product.description" :disabled="!product.isEditing"></textarea>
            </div>
        </div>
        <ul>
            <div>
            <label>上傳圖片：</label><br>
            <input type="file" @change="uploadImage(product, $event)" :disabled="!product.isEditing" />
            </div>
            <div v-if="product.secondHand" style="padding-top: 10px;">
            <label>願望商品：</label>
            <input type="text" @change="updateWishItem(product)" v-model="product.wishItem" :disabled="!product.isEditing" />
            </div>
        </ul>
        <div class="action-buttons">
            <button
            class="action-button"
            :class="{ 'inactive-button': product.secondHand === false }"
            @click="setProductSecondHand(product, false)"
            :disabled="!product.isEditing"
            >拍賣</button>
            <button
            class="action-button"
            :class="{ 'inactive-button': product.secondHand === true }"
            @click="setProductSecondHand(product, true)"
            :disabled="!product.isEditing"
            >交換</button>
        </div>
        <div>
            <button class="action-button" @click="takeOffProduct(product)">下架</button>
            <button class="action-button" @click="toggleEditing(product)">
            {{ product.isEditing ? '保存' : '修改' }}
            </button>
            <button class="action-button" @click="confirmDelete(product)">刪除</button>
        </div>
        </div>
    </div>
    <hr />
    <h1>下架商品</h1>
    <div class="product-list">
        <div v-if="offSaleProducts.length === 0" class="no-products">
        <h3>查無商品</h3>
        <br>
        </div>
        <div class="product-item" v-for="product in offSaleProducts" :key="product.productID">
        <div class="product-info">
            <img :src="product.imageUrl || '/default-product.png'" alt="商品圖片" class="product-image" />
            <div class="product-details">
            <div>
                商品名稱:
                <input type="text" v-model="product.productName" :disabled="!product.isEditing" />
            </div>
            <div class="quantity">
                數量:
                <button @click="decreaseQuantity(product)" :disabled="!product.isEditing">-</button>
                <input type="number" v-model.number="product.stock" :disabled="!product.isEditing" />
                <button @click="increaseQuantity(product)" :disabled="!product.isEditing">+</button>
            </div>
            <div class="price">
                定價:
                <button @click="decreasePrice(product)" :disabled="!product.isEditing">-</button>
                <input type="number" v-model.number="product.price" :disabled="!product.isEditing" />
                <button @click="increasePrice(product)" :disabled="!product.isEditing">+</button>
            </div>
            <p class="quantity">總數量: {{ product.stock }}</p>
            <textarea v-model="product.description" :disabled="!product.isEditing"></textarea>
            </div>
        </div>
        <ul>
            <div>
            <label>上傳圖片：</label><br>
            <input type="file" @change="uploadImage(product, $event)" :disabled="!product.isEditing" />
            </div>
            <div v-if="product.secondHand" style="padding-top: 10px;">
            <label>願望商品：</label>
            <input type="text" @change="updateWishItem(product)" v-model="product.wishItem" :disabled="!product.isEditing" />
            </div>
        </ul>
        <div class="action-buttons">
            <button
            class="action-button"
            :class="{ 'inactive-button': product.secondHand === false }"
            @click="setProductSecondHand(product, false)"
            :disabled="!product.isEditing"
            >拍賣</button>
            <button
            class="action-button"
            :class="{ 'inactive-button': product.secondHand === true }"
            @click="setProductSecondHand(product, true)"
            :disabled="!product.isEditing"
            >交換</button>
        </div>
        <div>
            <button class="action-button" @click="putOnProduct(product)">上架</button>
            <button class="action-button" @click="toggleEditing(product)">
            {{ product.isEditing ? '保存' : '修改' }}
            </button>
            <button class="action-button" @click="confirmDelete(product)">刪除</button>
        </div>
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

// 从本地存储加载数据
const loadProductsFromLocalStorage = () => {
const onSale = localStorage.getItem('onSaleProducts');
const offSale = localStorage.getItem('offSaleProducts');
if (onSale) onSaleProducts.value = JSON.parse(onSale);
if (offSale) offSaleProducts.value = JSON.parse(offSale);
};

// 保存数据到本地存储
const saveProductsToLocalStorage = () => {
localStorage.setItem('onSaleProducts', JSON.stringify(onSaleProducts.value));
localStorage.setItem('offSaleProducts', JSON.stringify(offSaleProducts.value));
};

const fetchMemberProducts = async () => {
if (user.value) {
    try {
    const response = await axiosapi.get(`/api/products/member/${user.value.id}`);
    const products = response.data.list || [];
    
    for (let product of products) {
        product.isEditing = false; // 默认不可编辑

        if (product.productImgs.length > 0) {
        const imgID = product.productImgs[0];
        product.imageUrl = await fetchProductImage(imgID);
        } else {
        product.imageUrl = '/default-product.png';
        }
    }

    onSaleProducts.value = products.filter(product => product.productStatus);
    offSaleProducts.value = products.filter(product => !product.productStatus);

    // 保存数据到本地存储
    saveProductsToLocalStorage();
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
updateProductStatus(product);
moveProductToOffSale(product);
};

const putOnProduct = (product) => {
product.productStatus = true;
updateProductStatus(product);
moveProductToOnSale(product);
};

const confirmDelete = (product) => {
    if (confirm(`確認刪除商品「${product.productName}」嗎？`)) {
        deleteProduct(product);
    }
};

const deleteProduct = async (product) => {
    try {
        const response = await axiosapi.delete(`/api/products/${product.productID}`);
        const data = JSON.parse(response.data);
        if (data.success) {
            alert(data.message);
            // 从本地状态中移除已删除的产品
            onSaleProducts.value = onSaleProducts.value.filter(p => p.productID !== product.productID);
            offSaleProducts.value = offSaleProducts.value.filter(p => p.productID !== product.productID);
            saveProductsToLocalStorage(); // 保存数据到本地存储
        } else {
            alert(data.message);
        }
    } catch (error) {
        console.error('Error deleting product:', error);
        alert('此商品尚有存在之訂單或圖片，無法刪除。');
    }
};

const updateProduct = async (product) => {
try {
    const updatedProduct = {
    productName: product.productName,
    price: product.price,
    stock: product.stock,
    description: product.description,
    wishItem: product.wishItem,
    productStatus: product.productStatus
    };
    await axiosapi.put(`/api/products/${product.productID}`, updatedProduct);
    saveProductsToLocalStorage(); // 保存更新到本地存储
} catch (error) {
    console.error('Error updating product:', error);
}
};

const updateProductStatus = async (product) => {
try {
    await axiosapi.put(`/api/products/${product.productID}`, { productStatus: product.productStatus });
    saveProductsToLocalStorage(); // 保存更新到本地存储
} catch (error) {
    console.error('Error updating product status:', error);
}
};

const moveProductToOffSale = (product) => {
onSaleProducts.value = onSaleProducts.value.filter(p => p.productID !== product.productID);
offSaleProducts.value.push(product);
saveProductsToLocalStorage(); // 保存更新到本地存储
};

const moveProductToOnSale = (product) => {
offSaleProducts.value = offSaleProducts.value.filter(p => p.productID !== product.productID);
onSaleProducts.value.push(product);
saveProductsToLocalStorage(); // 保存更新到本地存储
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

            // 更新前端显示的图片
            const reader = new FileReader();
            reader.onload = (e) => {
                product.imageUrl = e.target.result;
                product.isEditing = true; // 保持该产品的编辑状态
                saveProductsToLocalStorage(); // 保存更新到本地存储
            };
            reader.readAsDataURL(file);
        } catch (error) {
            console.error('Error uploading image:', error);
            alert('圖片上傳失敗');
        }
    }
};

const setProductSecondHand = async (product, isSecondHand) => {
try {
    await axiosapi.put(`/api/products/${product.productID}`, { secondHand: isSecondHand });
    product.secondHand = isSecondHand;
    saveProductsToLocalStorage(); // 保存更新到本地存储
} catch (error) {
    console.error('Error updating product isSecondHand:', error);
}
};

const updateWishItem = async (product) => {
try {
    const updatedProduct = {
    wishItem: product.wishItem
    };
    await axiosapi.put(`/api/products/${product.productID}`, updatedProduct);
    alert('願望商品更新成功');
    saveProductsToLocalStorage(); // 保存更新到本地存储
} catch (error) {
    console.error('Error updating wish item:', error);
    alert('願望商品更新失敗');
}
};

const toggleEditing = (product) => {
product.isEditing = !product.isEditing;
if (!product.isEditing) {
    updateProduct(product);
}
};

onMounted(() => {
loadProductsFromLocalStorage(); // 尝试从本地存储加载数据
fetchMemberProducts(); // 仍然调用API以确保数据是最新的
});
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
padding-top: 2px;
}

.quantity button, .price button {
margin: 0 6px;
padding-top: 1px;
}

.action-button, .upload-button {
margin-left: 10px;
}

.inactive-button {
background-color: #e0e0e0; /* 灰色背景 */
cursor: not-allowed; /*指示按钮不可点击 */
}
</style>
