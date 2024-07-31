<template>
    <h3>product upload page</h3>


    <form style="width: 38rem;">

        <div class="form-floating mb-3">
            <input type="file" @change="handleFileChange" multiple />
            <div class="image-preview" v-for="(image, index) in images" :key="index">
                <div class="image-container">
                    <img :src="image.url" alt="Preview" class="preview-img" />
                    <div @click="removeImage(index)" class="remove-btn">刪除</div>
                </div>
            </div>
        </div>




        <div class="form-floating mb-3">
            <input v-model="productName" type="text" class="form-control" id="flInputProductName" placeholder="productName">
            <label for="flInputProductName">商品名稱</label>
        </div>

        <div class="form-floating mb-3">
            <input v-model="price" type="number" class="form-control" id="flInputPrice" placeholder="price">
            <label for="flInputPrice">金額</label>
        </div>

        <div class="form-floating mb-3">
            <input v-model="stock" type="number" class="form-control" id="flInputStock" placeholder="stock">
            <label for="flInputStock">庫存數量</label>
        </div>

        <div class="form-floating mb-3">
            <textarea v-model="description" class="form-control" id="flInputDes" placeholder="description"></textarea>
            <label for="flInputDes">商品說明</label>
        </div>

        <!-- 資料庫欄位 商品上下架productStatus 預設上架true-->


        <div class="form-floating mb-3">
            <span style="margin-right: 75px;" class="col-md-3">是二手商品嗎：</span>
            <div class="form-check form-check-inline">
                <input v-model="isSecondHand" class="form-check-input" type="radio" name="options1" id="inlineRadio1" value="false" checked>
                <label style="margin-right: 30px;" class="form-check-label" for="inlineRadio1">否</label>
            </div>
            <div class="form-check form-check-inline">
                <input v-model="isSecondHand" class="form-check-input" type="radio" name="options2" id="inlineRadio2" value="true">
                <label style="margin-right: 30px;" class="form-check-label" for="inlineRadio2">是</label>
            </div>
        </div>

        <div class="form-floating mb-3">
            <div class="btn-group w-100" role="group" aria-label="Basic checkbox toggle button group">
                <span style="margin-right: 30px;" class="col-md-3">付款方式：</span>
                <input v-model="payRequest1" type="checkbox" class="btn-check" id="btncheck1" autocomplete="off">
                <label v-if="payMethods[0]" style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50" for="btncheck1">{{ payMethods[0].payMethod }}</label>

                <input v-model="payRequest2" type="checkbox" class="btn-check" id="btncheck2" autocomplete="off">
                <label v-if="payMethods[1]" style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50" for="btncheck2">{{ payMethods[1].payMethod }}</label>

                <input v-model="payRequest3" type="checkbox" class="btn-check" id="btncheck3" autocomplete="off">
                <label v-if="payMethods[2]" style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50" for="btncheck3">{{ payMethods[2].payMethod }}</label>
            </div>
        </div>

        <div class="form-floating mb-3">
            <span style="margin-right: 105px;" class="col-md-3">運送方式：</span>
            <div class="form-check form-check-inline">
                <input v-model="delRequest1" class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1" ></input>
                <label v-if="deliverys[0]" style="margin-right: 30px;" class="form-check-label" for="inlineCheckbox1">
                    {{ deliverys[0].delivery }}</label>
            </div>
            <div class="form-check form-check-inline">
                <input v-model="delRequest2" class="form-check-input" type="checkbox" id="inlineCheckbox2" value="option2">
                <label v-if="deliverys[1]" style="margin-right: 30px;" class="form-check-label " for="inlineCheckbox2">
                    {{ deliverys[1].delivery }}</label>
            </div>
            <div class="form-check form-check-inline">
                <input v-model="delRequest3" class="form-check-input" type="checkbox" id="inlineCheckbox3" value="option3">
                <label v-if="deliverys[2]" class="form-check-label " for="inlineCheckbox2">
                    {{ deliverys[2].delivery }}</label>
            </div>
        </div>

        <div class="form-floating mb-3">
            <div class="row">
                <span style="margin-right: 30px;" class="col-md-3">商品分類：</span>
                <div class="col-md-3">
                    <div class="form-group">
                        <select v-model="selectedMain" @change="onMainCategoryChange" class="form-control">
                            <option value="" disabled selected>請選擇主分類</option>
                            <option v-for="category in allMainCate" :key="category.mainCategoryID" :value="category.mainCategoryID">
                                {{ category.mainCategory }}
                            </option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <select v-model="selectedSub" class="form-control">
                            <option value="" disabled selected>請選擇子分類</option>
                            <option v-for="subcate in subCategories" :key="subcate.subCategoryID" :value="subcate.subCategoryID">
                                {{ subcate.subCategory }}
                            </option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <div class="row justify-content-end">
            <div @click="upload" class="btn btn-outline-primary col-md-3 rounded-pill ">送出</div>
            <div  class="btn btn-outline-primary col-md-3 rounded-pill ">新增下一筆</div>
            </div>
        </div>
    </form>


</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import axiosapi from '@/plugins/axios';
    import Swal from 'sweetalert2';

    const payMethods = ref([]);
    const deliverys = ref([]);
    const allMainCate = ref([]);
    const allSubCate = ref([]);
    const selectedMain = ref('');
    const selectedSub = ref('');
    const subCategories = ref([]);

    const payRequest1 = ref('')
    const payRequest2 = ref('')
    const payRequest3 = ref('')
    const delRequest1 = ref('')
    const delRequest2 = ref('')
    const delRequest3 = ref('')

    const images = ref([]);
    const productName = ref('');
    const price = ref(0);
    const stock = ref(0);
    const description = ref('');
    const isSecondHand = ref(false);
    const onMainCategoryChange = () => {
        subCategories.value = allSubCate.value.filter((subcate) => subcate.mainCategory === selectedMain.value);

        selectedSub.value = '';
    }

    onMounted(function () {
        callPayMethod();
        callDelivery();
        callCategory();
    });


    // 上傳功能
    async function upload() {
        let requestProduct = {
            "productName": productName.value,
            "price": price.value,
            "stock": stock.value,
            "description": description.value,
            "isSecondHand": isSecondHand.value,
            "subCategory": selectedSub.value,
            "member": 1,
            "productStatus": true,
        }

        try {
            const productResponse = await axiosapi.post("/api/products", requestProduct);
            console.log(productResponse.data);
            if(productResponse.data.success) {
                const productID = productResponse.data.productID;
                await payPost(productID);
                await delPost(productID);
                await imagePost(productID);
                Swal.fire({
                    icon: "success",
                    text: "成功",
                })
            }else {
                Swal.fire({
                    icon: "warning",
                    text: "失敗",
                });
            }
        } catch (error){
            console.log(error);
            Swal.fire({
                icon: "error",
                text: "上傳失敗: " + error.message,
            });
        }
    }

    // 處理上傳圖片功能
    async function imagePost(productID) {
        
            const formData = new FormData();
            formData.append('jsonProduct', JSON.stringify({productID : productID}));

            images.value.forEach((image, index) => {

                console.log(index);
                formData.append('image', image.file);
            });

            // console.log('formData',formData.data);
            try {
                await axiosapi.post("/api/productImg", formData, {
                    headers: {
                        'Content-Type' : 'multipart/form-data'
                    }
                }).then(function(response) {
                    console.log(response.data);
                }).catch(function(error) {
                    console.log(error.message);
                });
            } catch(error) {
                console.error('Error uploading image:', error);
                alert('圖片上傳失敗');
            }
        
        
    

    }

    // 處理圖片預覽(新增、刪除)
    const handleFileChange = (event) => {
    const files = event.target.files;
        for (let i = 0; i < files.length; i++) {
                const reader = new FileReader();
                reader.onload = (e) => {
                images.value.push({ file: files[i], url: e.target.result });
            };
            reader.readAsDataURL(files[i]);
        }
    };

    const removeImage = (index) => {
        images.value.splice(index, 1);
    };


    // 處理上傳支付方式
    async function payPost (productID) {
        if(payRequest1.value) {
            const payMethodID = 1;
            uploadPayMethods(productID, payMethodID);
        }
        if(payRequest2.value) {
            const payMethodID = 3;
            uploadPayMethods(productID, payMethodID);
        }
        if(payRequest3.value) {
            const payMethodID = 2;
            uploadPayMethods(productID, payMethodID);
        }
    }
    async function uploadPayMethods(productID, payMethodID) {   
        let requestPayMethod = {
            "payMethodID" : payMethodID,
            "productID" : productID
        };

        try{
            await axiosapi.post("/productPayMethod", requestPayMethod).then(function(response){
            }).catch(function(error){
                console.log(response.error);    
            });

        }catch (error) {
            Swal.fire({
                icon: "error",
                text: "上傳支付方式失敗: " + error.message,
            });
        }
    }

// 處理上傳運送方式
    async function delPost (productID) {
        if(delRequest1.value) {
            const deliveryID = 1;
            uploadDelivery(productID, deliveryID);
        }
        if(delRequest2.value) {
            const deliveryID = 2;
            uploadDelivery(productID, deliveryID);
        }
        if(delRequest3.value) {
            const deliveryID = 3;
            uploadDelivery(productID, deliveryID);
        }
    }
    
    async function uploadDelivery(productID, deliveryID) {
        let requestDelivery = {
            "deliveryID" : deliveryID,
            "productID" : productID
        };
        try{
            const response = axiosapi.post("/productDelivery",requestDelivery);
            if(!response.data.success) {
                throw new Error(response.data.message)
            }
        }catch (error) {
            Swal.fire({
                icon: "error",
                text: "上傳貨運方式失敗: " + error.message,
            });

        }
    }

    function callCategory () {
        axiosapi.get("/api/categories/mainCategory/all").then(function(response) {
            allMainCate.value = response.data.list;
            // console.log(allMainCate.value[0].mainCategory);
        }).catch(function() {
            console.log("讀取主分類錯誤");
        });

        axiosapi.get("/api/categories/subCategory/all").then(function(response) {
            allSubCate.value = response.data.list;
            // console.log(allSubCate.value[0].subCategory);
        }).catch(function() {
            console.log("讀取子分類錯誤");
        });
    }

    function callPayMethod () {
        let request = {"dir" : true}
        axiosapi.post("/payMethod/find", request).then(function(response) {
            payMethods.value = response.data.list;
            // console.log(payMethods.value[0].payMethod);
        }).catch(function(error) {
            console.log("讀取支付方法錯誤");
        });
    }

    function callDelivery () {
        let request = {"dir" : true}
        axiosapi.post("/delivery/find", request).then(function(response) {
            deliverys.value = response.data.list;
            // console.log(deliverys.value[0].delivery);
        }).catch(function(error) {
            console.log("讀取貨運方式錯誤");
        });
    }


</script>

<style>
    .image-preview {
        display: flex;
        flex-wrap: wrap;
        margin-top: 10px;
    }
    .image-container {
        position: relative;
        margin: 10px;
        border: 1px solid #ccc;
        padding: 10px;
        width: 150px;
        height: 150px;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f8f8f8;
    }
    .preview-img {
        max-width: 100%;
        max-height: 100%;
    }
    .remove-btn {
        position: absolute;
        top: 5px;
        right: 5px;
        background-color: rgba(255, 0, 0, 0.7);
        color: white;
        border: none;
        border-radius: 50%;
        width: 20px;
        height: 20px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
    }
</style>