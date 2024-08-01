<template>


    <div class="form-container">
        <form class="upload-form">
            <div class="form-floating mb-3">
            <input type="file" @change="handleFileChange" multiple />
                <div class="image-preview">
                    <div class="image-container"  v-for="(image, index) in images" :key="index">
                        <img :src="image.url" alt="Preview" class="preview-img" />
                        <div @click="removeImage(index)" class="remove-btn">X</div>
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
            <textarea style="height: 150px;" v-model="description" class="form-control" id="flInputDes" placeholder="description"></textarea>
            <label for="flInputDes">商品說明</label>
        </div>

        <!-- 資料庫欄位 商品上下架productStatus 預設上架true-->

        <div class="form-floating mb-3">
            <div class="btn-group w-100" role="group" aria-label="Basic checkbox toggle button group">
                <span style="margin-right: 30px;" class="col-md-3">是二手商品嗎：</span>
                <div class="form-check form-check-inline">
                    <input v-model="isSecondHand" class="form-check-input" type="radio" name="options1" id="inlineRadio1" value="false" checked>
                    <label style="margin-right: 30px;" class="form-check-label" for="inlineRadio1">否</label>
                </div>
                <div class="form-check form-check-inline">
                    <input v-model="isSecondHand" class="form-check-input" type="radio" name="options2" id="inlineRadio2" value="true">
                    <label style="margin-right: 30px;" class="form-check-label" for="inlineRadio2">是</label>
                </div>
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
            <div class="btn-group w-100" role="group" aria-label="Basic checkbox toggle button group">
                <span style="margin-right: 30px;" class="col-md-3">運送方式：</span>
                <input v-model="delRequest1" class="btn-check" type="checkbox" id="inlineCheckbox1" value="option1" ></input>
                <label v-if="deliverys[0]" style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50" for="inlineCheckbox1">
                    {{ deliverys[0].delivery }}</label>

                <input v-model="delRequest2" class="btn-check" type="checkbox" id="inlineCheckbox2" value="option2">
                <label v-if="deliverys[1]" style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50 " for="inlineCheckbox2">
                    {{ deliverys[1].delivery }}</label>

                <input v-model="delRequest3" class="btn-check" type="checkbox" id="inlineCheckbox3" value="option3">
                <label v-if="deliverys[2]" class="btn btn-outline-secondary col-md-3 rounded-pill w-50 " for="inlineCheckbox3">
                    {{ deliverys[2].delivery }}</label>
            </div>
        </div>

        <div class="form-floating mb-3">
            <div class="row w-100">
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

        <span style="margin-right: 30px;" class="col-md-3">規格ㄧ：</span>
        <div class="form-floating mb-3">
            <div class="input-row">
                <div class="input-group">
                    <input v-model="spec1" type="text" class="form-control" id="specCate" placeholder="請輸入規格種類">
                </div>
                <div class="input-group">
                    <input v-model="specName1" type="text" class="form-control" id="specName" placeholder="請輸入規格名稱">
                </div>
            </div>    

        </div>

        <span style="margin-right: 30px;" class="col-md-3">規格二：</span>
        <div class="form-floating mb-3">
            <div class="input-row">
                <div class="input-group">
                    <input v-model="spec2" type="text" class="form-control" id="specCate" placeholder="請輸入規格種類">
                </div>
                <div class="input-group">
                    <input v-model="specName2" type="text" class="form-control" id="specName" placeholder="請輸入規格名稱">
                </div>
                <div class="input-group">
                    <input v-model="specNum" type="number" class="form-control" id="specQt" placeholder="請輸入數量">
                </div>

            </div> 
                <!-- <input type="file" @change="handleFileChange" multiple /> -->
                <input type="file" @change="specImageChange"  />
                <div v-if="specImage1.url" class="specImage1-preview">
                    <div class="specImage1-container">
                        <img :src="specImage1.url" alt="Preview" class="preview-specImage1" />
                        <div @click="removeSpecImage1" class="remove-btn">X</div>
                    </div>
                </div>
        </div>

        <div class="form-floating mb-3">
            <div class="row justify-content-end">
            <RouterLink :to="{name: 'product-Upload-page-link'}" @click="upload" 
                class="btn btn-outline-primary col-md-3 rounded-pill ">送出</RouterLink>
            <RouterLink :to="{name: 'product-Upload-page-link'}" @click="upload" 
                class="btn btn-outline-primary col-md-3 rounded-pill ">新增下一筆</RouterLink>
            </div>
        </div>
        </form>
    </div>


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

    const payRequest1 = ref('');
    const payRequest2 = ref('');
    const payRequest3 = ref('');
    const delRequest1 = ref('');
    const delRequest2 = ref('');
    const delRequest3 = ref('');

    const spec1 = ref('');
    const spec2 = ref('');
    const specName1 = ref('');
    const specName2 = ref('');
    const specNum = ref(0);
    const specImage1 = ref({});

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
                await specPost(productID);
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

    // 處理規格圖片上傳功能
    async function specPost(productID) {
        const formData = new FormData();
            formData.append('jsonProduct', 
                JSON.stringify({productID : productID,
                                specOne : spec1.value,
                                specOneName : specName1.value,
                                specTwo : spec2.value,
                                specTwoName : specName2.value,
                                specQt : specNum.value,
                                
                }));
            formData.append('image', specImage1.value.file);
            await uploadSpec(formData);
    }

    async function uploadSpec(formData) {
        try {
                await axiosapi.post("/api/productSpec", formData, {
                    headers: {
                        'Content-Type' : 'multipart/form-data'
                    }
                }).then(function(response) {
                    console.log(response.data);
                }).catch(function(error) {
                    console.log(error.message);
                });
            } catch(error) {
                console.error('Error uploading spec:', error);
                alert('規格上傳失敗');
            }
    }



    // 處理上傳圖片功能
    async function imagePost(productID) {  
            const formData = new FormData();
            formData.append('jsonProduct', JSON.stringify({productID : productID}));

            images.value.forEach(async (image, index) => {
                formData.set('image', image.file);
                await uploadImage(formData);
            });
    }

    async function uploadImage(formData) {
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

    // 處理規格單張圖片上傳
    const specImageChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                specImage1.value = { file: file, url: e.target.result };
            };
            reader.readAsDataURL(file);
        }
    }
    const removeSpecImage1 = () => {
        specImage1.value = {};
};

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

    .specImage1-preview {
        flex-wrap: nowrap;
        display: flex;
        overflow-x: auto; /*當圖片數量超過容器寬度時，會顯示水平滾動條*/
        margin-top: 10px;
    }
    .specImage1-container {
        position: relative;
        margin: 5px;
        border: 1px solid #ccc;
        padding: 5px;
        width: 50px;
        height: 50px;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f8f8f8;
    }
    .preview-specImage1 {
        max-width: 100%;
        max-height: 100%;
    }


    .input-row {
        display: flex;
        gap: 10px;
    }

    .input-group {
        display: flex;
        flex-direction: row;
        width: 220px;
    }


    .form-container {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background-color: #f0dddd;
        padding: 20px;
    }

    .upload-form {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        width: 100%;
        max-width: 800px;
    }
    .image-preview {
        flex-wrap: nowrap;
        display: flex;
        overflow-x: auto; /*當圖片數量超過容器寬度時，會顯示水平滾動條*/
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
        color: black;
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