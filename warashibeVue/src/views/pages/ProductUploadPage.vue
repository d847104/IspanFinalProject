<template>
    <h3>product upload page</h3>


    <form style="width: 38rem;">

        <div>
            <input type="file" @change="handleFileChange" multiple />
            <div class="image-preview" v-for="(image, index) in images" :key="index">
                <div class="image-container">
                    <img :src="image.url" alt="Preview" class="preview-img" />
                    <button @click="removeImage(index)" class="remove-btn">刪除</button>
                </div>
            </div>
        </div>



        <div class="form-floating mb-3">
            <input  type="text" class="form-control" id="flInputProductName" placeholder="productName">
            <label for="flInputProductName">商品名稱</label>
        </div>

        <div class="form-floating mb-3">
            <input  type="number" class="form-control" id="flInputPrice" placeholder="price">
            <label for="flInputPrice">金額</label>
        </div>

        <div class="form-floating mb-3">
            <input  type="number" class="form-control" id="flInputStock" placeholder="stock">
            <label for="flInputStock">庫存數量</label>
        </div>

        <div class="form-floating mb-3">
            <textarea  class="form-control" id="flInputDes" placeholder="description"></textarea>
            <label for="flInputDes">商品說明</label>
        </div>

        <!-- 資料庫欄位 商品上下架productStatus 預設上架true-->


        <div class="form-floating mb-3">
            <span style="margin-right: 75px;" class="col-md-3">是二手商品嗎：</span>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked>
                <label style="margin-right: 30px;" class="form-check-label" for="inlineRadio1">否</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
                <label style="margin-right: 30px;" class="form-check-label" for="inlineRadio2">是</label>
            </div>
        </div>

        <div class="form-floating mb-3">
            <div class="btn-group w-100" role="group" aria-label="Basic checkbox toggle button group">
                <span style="margin-right: 30px;" class="col-md-3">付款方式：</span>
                <input type="checkbox" class="btn-check" id="btncheck1" autocomplete="off">
                <label style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50" for="btncheck1">貨到付款</label>

                <input type="checkbox" class="btn-check" id="btncheck2" autocomplete="off">
                <label style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50" for="btncheck2">信用卡</label>

                <input type="checkbox" class="btn-check" id="btncheck3" autocomplete="off">
                <label style="margin-right: 30px;" class="btn btn-outline-secondary col-md-3 rounded-pill w-50" for="btncheck3">行動支付</label>
            </div>
        </div>

        <div class="form-floating mb-3">
            <span style="margin-right: 105px;" class="col-md-3">運送方式：</span>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1">
                <label style="margin-right: 30px;" class="form-check-label" for="inlineCheckbox1">宅配</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="option2">
                <label style="margin-right: 30px;" class="form-check-label " for="inlineCheckbox2">超商取貨</label>
            </div>
        </div>

        <div class="form-floating mb-3">
            <div class="row">
                <span style="margin-right: 30px;" class="col-md-3">商品分類：</span>
                <div class="col-md-3">
                    <div class="form-group">
                        <select class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <select class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <div class="row justify-content-end">
            <button  class="btn btn-outline-primary col-md-3 rounded-pill ">送出</button>
            <button  class="btn btn-outline-primary col-md-3 rounded-pill ">新增下一筆</button>
            </div>
        </div>
    </form>


</template>

<script setup>
    import { ref } from 'vue';

    const images = ref([]);

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