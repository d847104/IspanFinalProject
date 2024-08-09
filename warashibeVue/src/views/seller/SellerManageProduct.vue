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
                <img :src="product.imageUrl || '/default-product.png'" alt="商品圖片" class="product-image" @click="gotoProductPage(product)"/>
                <div class="product-details">
                <div>
                    商品名稱:
                    <input type="text" v-model="product.productName" :disabled="!product.isEditing" />
                </div>
                <div class="quantity">
                    <label class="col-sm-2 mb-2">
                        數量:
                    </label>
                    <button @click="decreaseQuantity(product)" :disabled="!product.isEditing">-</button>
                    <input type="number" v-model.number="product.stock" :disabled="!product.isEditing" />
                    <button @click="increaseQuantity(product)" :disabled="!product.isEditing">+</button>
                </div>
                <div class="price">
                    <label class="col-sm-2 mb-2">
                        定價:
                    </label>
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
            <!-- 規格編輯區域 -->
            <div v-if="product.specs" class="specs">
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">規格一：</label>
                    <div class="col-sm-8 mb-2">
                        <input type="text" class="form-control" v-model="product.specs.specOneName" :disabled="!product.isEditing" @change="updateSpecOneName(product)" />
                    </div>
                </div>
                <div v-for="specOne in product.specs.specOnes" :key="specOne.specOneID" class="spec-one">
                    <div class="form-group row">
                        <label class="col-sm-4 col-form-label">規格值：</label>
                        <div class="col-sm-8 mb-2">
                            <input type="text" class="form-control" v-model="specOne.specOne" :disabled="!product.isEditing" @change="updateSpecOne(product, specOne)" />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-4 col-form-label">數量：</label>
                        <div class="col-sm-8 mb-2">
                            <input type="number" class="form-control" v-model.number="specOne.specOneQt" :disabled="!product.isEditing" @change="updateSpecOne(product, specOne)" />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-4 col-form-label">圖片：</label>
                        <div class="col-sm-8 mb-2">
                            <input type="file" class="form-control-file" @change="uploadSpecOneImage(specOne, $event)" :disabled="!product.isEditing" />
                            <img v-if="specOne.imgUrl" :src="specOne.imgUrl" alt="規格圖片" class="spec-image" />
                        </div>
                    </div>
                    <div v-for="specTwoName in specOne.specTwoNames" :key="specTwoName.specTwoNameID">
                        <div class="form-group row">
                            <label class="col-sm-4 col-form-label">規格二：</label>
                            <div class="col-sm-8 mb-2">
                                <input type="text" class="form-control" v-model="specTwoName.specTwoName" :disabled="!product.isEditing" @change="updateSpecTwoName(specOne, specTwoName)" />
                            </div>
                        </div>
                        <div v-for="specTwo in specTwoName.specTwos" :key="specTwo.specTwoID">
                            <div class="form-group row">
                                <label class="col-sm-4 col-form-label">規格值：</label>
                                <div class="col-sm-8 mb-2">
                                    <input type="text" class="form-control" v-model="specTwo.specTwo" :disabled="!product.isEditing" @change="updateSpecTwo(specTwoName, specTwo)" />
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-4 col-form-label">數量：</label>
                                <div class="col-sm-8 mb-2">
                                    <input type="number" class="form-control" v-model.number="specTwo.specTwoQt" :disabled="!product.isEditing" @change="updateSpecTwo(specTwoName, specTwo)" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                <label class="col-sm-2 mb-2">
                    數量:
                </label>
                <button @click="decreaseQuantity(product)" :disabled="!product.isEditing">-</button>
                <input type="number" v-model.number="product.stock" :disabled="!product.isEditing" />
                <button @click="increaseQuantity(product)" :disabled="!product.isEditing">+</button>
            </div>
            <div class="price">
                <label class="col-sm-2 mb-2">
                    定價:
                </label>
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
            <label class="col-sm-4" >上傳圖片：</label><br>
            <input type="file" @change="uploadImage(product, $event)" :disabled="!product.isEditing" />
            </div>
            <div v-if="product.secondHand" style="padding-top: 10px;">
            <label>願望商品：</label>
            <input type="text" @change="updateWishItem(product)" v-model="product.wishItem" :disabled="!product.isEditing" />
            </div>
        </ul>
        <!-- 規格編輯區域 -->
        <div v-if="product.specs" class="specs">
            <div class="form-group row">
                <label class="col-sm-4 col-form-label">規格一：</label>
                <div class="col-sm-8 mb-2">
                    <input type="text" class="form-control" v-model="product.specs.specOneName" :disabled="!product.isEditing" @change="updateSpecOneName(product)" />
                </div>
            </div>
            <div v-for="specOne in product.specs.specOnes" :key="specOne.specOneID" class="spec-one">
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">規格值：</label>
                    <div class="col-sm-8 mb-2">
                        <input type="text" class="form-control" v-model="specOne.specOne" :disabled="!product.isEditing" @change="updateSpecOne(product, specOne)" />
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">數量：</label>
                    <div class="col-sm-8 mb-2">
                        <input type="number" class="form-control" v-model.number="specOne.specOneQt" :disabled="!product.isEditing" @change="updateSpecOne(product, specOne)" />
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">圖片：</label>
                    <div class="col-sm-8 mb-2">
                        <input type="file" class="form-control-file" @change="uploadSpecOneImage(specOne, $event)" :disabled="!product.isEditing" />
                        <img v-if="specOne.imgUrl" :src="specOne.imgUrl" alt="規格圖片" class="spec-image" />
                    </div>
                </div>
                <div v-for="specTwoName in specOne.specTwoNames" :key="specTwoName.specTwoNameID">
                    <div class="form-group row">
                        <label class="col-sm-4 col-form-label">規格二：</label>
                        <div class="col-sm-8 mb-2">
                            <input type="text" class="form-control" v-model="specTwoName.specTwoName" :disabled="!product.isEditing" @change="updateSpecTwoName(specOne, specTwoName)" />
                        </div>
                    </div>
                    <div v-for="specTwo in specTwoName.specTwos" :key="specTwo.specTwoID">
                        <div class="form-group row">
                            <label class="col-sm-4 col-form-label">規格值：</label>
                            <div class="col-sm-8 mb-2">
                                <input type="text" class="form-control" v-model="specTwo.specTwo" :disabled="!product.isEditing" @change="updateSpecTwo(specTwoName, specTwo)" />
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-4 col-form-label">數量：</label>
                            <div class="col-sm-8 mb-2">
                                <input type="number" class="form-control" v-model.number="specTwo.specTwoQt" :disabled="!product.isEditing" @change="updateSpecTwo(specTwoName, specTwo)" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
import router from '@/router/router';

const isLogin = inject('isLogin');
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

const gotoProductPage = (product) => {
    router.push(`/pages/productpage?productID=${product.productID}`);
}

const fetchMemberProducts = async () => {
if (isLogin.value) {
    try {
    const response = await axiosapi.get(`/api/products/member/${sessionStorage.getItem("memberID")}`);
    const products = response.data.list || [];
    
    for (let product of products) {
        product.isEditing = false; // 默认不可编辑

            if (product.productImgs.length > 0) {
                const imgID = product.productImgs[0];
                product.imageUrl = await fetchProductImage(imgID);
            } else {
                product.imageUrl = '/default-product.png';
            }

            if (product.specs && product.specs.specOnes) {
                for (let specOne of product.specs.specOnes) {
                    if (specOne.specOneImg) {
                    specOne.imgUrl = await fetchSpecImage(specOne.specOneImg);
                    }
                }
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

const fetchSpecImage = async (imgID) => {
    try {
        const response = await axiosapi.get(`/api/specImg/${imgID}`);
        if (response.data && response.data.list && response.data.list.specImg) {
        return `data:image/png;base64,${response.data.list.specImg}`;
        }
    } catch (error) {
        console.error('Error fetching spec image:', error);
    }
    return '/default-product.png';
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
        saveProductsToLocalStorage(); // 保存更新到本地存储
    } catch (error) {
        console.error('Error updating wish item:', error);
    }
};

const toggleEditing = (product) => {
    product.isEditing = !product.isEditing;
    if (!product.isEditing) {
        updateProduct(product);
    }
};

const updateSpecOneName = async (product) => {
    if (product.specs && product.specs.specOneNameID && product.specs.specOneName) {
        const updatedSpecOneName = {
        specOneNameID: product.specs.specOneNameID,
        specOneName: product.specs.specOneName,
        product: product.productID
        };
        try {
        await axiosapi.put(`/api/spec/onename/modify`, updatedSpecOneName);
        saveProductsToLocalStorage();
        } catch (error) {
        console.error('Error updating specOneName:', error);
        }
    }
};

const updateSpecOne = async (product, specOne) => {
    if (specOne.specOneID && specOne.specOne && specOne.specOneQt !== undefined) {
        const updatedSpecOne = {
        specOneID: specOne.specOneID,
        specOne: specOne.specOne,
        specOneQt: specOne.specOneQt,
        specOneName: product.specs.specOneNameID
        };
        try {
        await axiosapi.put(`/api/spec/one/modify`, updatedSpecOne);
        saveProductsToLocalStorage();
        } catch (error) {
        console.error('Error updating specOne:', error);
        }
    }
};

const uploadSpecOneImage = async (specOne, event) => {
    const file = event.target.files[0];
    if (file) {
        const formData = new FormData();
        formData.append('image', file);
        try {
            const response = await axiosapi.put(`/api/specImg/${specOne.specOneImg}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            if (response.data.success) {
                // 更新前端顯示的圖片
                const reader = new FileReader();
                reader.onload = (e) => {
                    specOne.imgUrl = e.target.result;
                    saveProductsToLocalStorage(); // 保存更新到本地存储
                };
                reader.readAsDataURL(file);
                alert('規格圖片上傳成功');
            } else {
                alert('規格圖片上傳失敗');
            }
        } catch (error) {
            console.error('Error uploading spec image:', error);
            alert('規格圖片上傳失敗');
        }
    }
};

const updateSpecTwoName = async (specOne, specTwoName) => {
    if (specTwoName.specTwoNameID && specTwoName.specTwoName) {
        const updatedSpecTwoName = {
        specTwoNameID: specTwoName.specTwoNameID,
        specTwoName: specTwoName.specTwoName,
        specOne: specOne.specOneID
        };
        try {
            await axiosapi.put(`/api/spec/twoname/modify`, updatedSpecTwoName);
            alert('第三層規格名稱更新成功');
            saveProductsToLocalStorage();
        } catch (error) {
            console.error('Error updating specTwoName:', error);
            alert('第三層規格名稱更新失敗');
        }
    }
};

const updateSpecTwo = async (specTwoName, specTwo) => {
    if (specTwo.specTwoID && specTwo.specTwo && specTwo.specTwoQt !== undefined) {
        const updatedSpecTwo = {
        specTwoID: specTwo.specTwoID,
        specTwo: specTwo.specTwo,
        specTwoQt: specTwo.specTwoQt,
        specTwoName: specTwoName.specTwoNameID
        };
        try {
            await axiosapi.put(`/api/spec/two/modify`, updatedSpecTwo);
            saveProductsToLocalStorage();
        } catch (error) {
            console.error('Error updating specTwo:', error);
        }
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
    cursor: pointer; /* 添加此行以更改鼠标光标 */
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

.spec-image {
    height: 50px;
    width: 50px;
    object-fit: cover;
    margin-top: 10px;
}
</style>


