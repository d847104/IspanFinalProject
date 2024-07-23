<template>
<!-- Modal -->
    <div ref="exampleRef" class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <table>
                    <tr>
                        <td>Id : </td>
                        <td><input type="text" name="id" :value="product.id" @input="doinput('id',$event.target.value)"></td>
                        <!-- <td><input type="text" name="id" :value="modelValue.id" @input="doinput('id',$event)"></td> -->
                    </tr>
                    <tr>
                        <td>Name : </td>
                        <td><input type="text" name="name" :value="product.name" @input="doinput('name',$event.target.value)"></td>
                    </tr>
                    <tr>
                        <td>Price : </td>
                        <td><input type="text" name="price" :value="product.price" @input="doinput('price',$event.target.value)"></td>
                    </tr>
                    <tr>
                        <td>Make : </td>
                        <td><input type="text" name="make" :value="product.make" @input="doinput('make',$event.target.value)"></td>
                    </tr>
                    <tr>
                        <td>Expire : </td>
                        <td><input type="text" name="expire" :value="product.expire" @input="doinput('expire',$event.target.value)"></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" v-show="isShowInsertButton" @click="emits('customInsert')">新增</button>
                <button type="button" class="btn btn-primary" v-show="!isShowInsertButton" @click="emits('customUpdate')">修改</button>
            </div>
            </div>
        </div>
    </div>
</template>
    
<script setup>
    // function doinput(key,event){...
    function doinput(key,eventTargetValue){
        // emits("update:modelValue",{...
        emits("update:product",{
            ...props.product,
            [key]:eventTargetValue
        })
    }
    // const props = defineProps(["isShowInsertButton","modelValue"])
    // const emits = defineEmits(["update:modelValue"])
    const props = defineProps(["isShowInsertButton","product"])
    const emits = defineEmits(["customInsert","customUpdate","update:product"])

    import bootstrap from 'bootstrap/dist/js/bootstrap.bundle.min.js'
    import {ref, onMounted} from 'vue'

    const exampleRef = ref(null);
    const exampleModal = ref(null);

    onMounted(function(){
        exampleModal.value = new bootstrap.Modal(exampleRef.value);
    });

    function showModal(){
        exampleModal.value.show();
    }

    function hideModal(){
        exampleModal.value.hide();
    }
    
    defineExpose({
        showModal,hideModal,
    });
</script>
    
<style>
    
</style>