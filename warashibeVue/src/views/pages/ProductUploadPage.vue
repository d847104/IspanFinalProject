<!-- <template>
    <h3>product upload page</h3>


    <form style="width: 38rem;">


        <div id="my-strictly-unique-vue-upload-multiple-image" style="text-align: center;">

            <vue-upload-multiple-image
            @upload-success="uploadImageSuccess"
            @before-remove="beforeRemove"
            @edit-image="editImage"
            @data-change="dataChange"
            ></vue-upload-multiple-image>
        </div>






        <div class="form-floating mb-3">
            <input v-model="待修改" type="text" class="form-control" id="flInputProductName" placeholder="productName">
            <label for="flInputProductName">商品名稱</label>
        </div>

        <div class="form-floating mb-3">
            <input v-model="待修改" type="number" class="form-control" id="flInputPrice" placeholder="price">
            <label for="flInputPrice">金額</label>
        </div>

        <div class="form-floating mb-3">
            <input v-model="待修改" type="number" class="form-control" id="flInputStock" placeholder="stock">
            <label for="flInputStock">庫存數量</label>
        </div>

        <div class="form-floating mb-3">
            <textarea v-model="待修改" class="form-control" id="flInputDes" placeholder="description"></textarea>
            <label for="flInputDes">商品說明</label>
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
    // import VueUploadMultipleImage from '@/components/VueUploadMultipleImage.vue';
    import VueUploadMultipleImage from 'vue-upload-multiple-image';
</script>

<style></style> -->


<template>
    <div>
      <vue-upload-multiple-image
        @upload-success="handleUploadSuccess"
        :max-upload="5"
        :max-size="10485760"
      />
      <div v-if="uploadedImages.length">
        <h3>上傳的圖片預覽：</h3>
        <div v-for="image in uploadedImages" :key="image.id">
          <img :src="image.url" :alt="`Uploaded image ${image.id}`" />
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
//   import VueUploadMultipleImage from 'vue-upload-multiple-image'; // Merge 後此行出錯故先註解掉
  
  const uploadedImages = ref([]);
  
  const handleUploadSuccess = (formData) => {
    formData.forEach((file, index) => {
      uploadedImages.value.push({
        id: index,
        url: URL.createObjectURL(file),
      });
    });
  };
  </script>