<template>
<div ref="exampleRef" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">商品詳情</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
        <h5>{{ product.productName }}</h5>
        <img :src="productImageSrc" class="card-img-top" :alt="product.productName" />
        <p class="price">NT$ {{ product.price }}</p>
        <p class="description">{{ product.description }}</p>
        <div class="specs">
            <div v-for="spec in productSpecs" :key="spec.specID" class="spec">
            <h5>{{ spec.specOneName }}: {{ spec.specOne }}</h5>
            <h5>{{ spec.specTwoName }}: {{ spec.specTwo }}</h5>
            <img :src="`data:image/jpeg;base64,${spec.specImg}`" class="spec-img" alt="Spec Image">
            </div>
        </div>
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
        </div>
    </div>
    </div>
</div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axiosapi from '@/plugins/axios';
import bootstrap from 'bootstrap/dist/js/bootstrap.bundle.min.js';

const props = defineProps(["product"]);
const emits = defineEmits(["update:product"]);

const productImageSrc = computed(() => {
    return props.product.productImgs && props.product.productImgs.length
    ? `data:image/jpeg;base64,${props.product.productImgs[0].img}`
    : 'default-image-path'; // 替換為默認圖片的路徑
});

const productSpecs = ref([]);

const exampleRef = ref(null);
const exampleModal = ref(null);

onMounted(() => {
    exampleModal.value = new bootstrap.Modal(exampleRef.value);
    fetchProductSpecs(props.product.productSpecs);
});

const fetchProductSpecs = async (specIDs) => {
    const specs = [];
    for (let specID of specIDs) {
        const { data } = await axiosapi.get(`/api/productSpec/${specID}`);
        specs.push(data.list);
    }
    productSpecs.value = specs;
};

const showModal = () => {
    exampleModal.value.show();
};

defineExpose({
    showModal
});
</script>

<style scoped>
.spec-img {
width: 50px;
height: auto;
}
</style>
