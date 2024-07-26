<template>
    <div class="row">
        <Card v-for="item in products" :key="item.id" :item="item" @custom-open-update="showDialog"
            @custom-delete="callRemote">
        </Card>
    </div>

    <Modal ref="modal" :is-show-button="isShowButton" v-model:id="product.id" v-model:name="product.name"
        v-model:price="product.price" v-model:make="product.make" v-model:expire="product.expire"
        @custom-insert="callCreate" @custom-update="callModify"></Modal>

</template>

<script setup>
//card start
import Card from '../../components/cards/product-card-modal.vue'
import { ref, onMounted } from 'vue'

const products = ref({})
onMounted(function () {
    products.value = sources
})
function showDialog(action, data) {
    if (action === "insert") {
        isShowButton.value = true;
        product.value = {};
    } else {
        isShowButton.value = false;
        product.value = {
            id: 100,
            name: "hahaha",
            price: 123,
            make: "2024-01-25",
            expire: 456,
        }
    }
    modal.value.openModal();
}
function callRemote(data) {
    console.log("callRemote", data)
}
//card end

//modal start
import Modal from '../../components/cards/model.vue'
const isShowButton = ref(false);
const product = ref({})
const modal = ref(null)
function callCreate() {
    console.log("callCreate", product.value);
    modal.value.closeModal();
}
function callModify() {
    console.log("callModify", product.value);
    modal.value.closeModal();
}
//modal end

</script>

<style></style>