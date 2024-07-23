<template>
    <span>
        <template v-if="total==0">查無資料</template>
        <template v-else>查到{{total}}筆資料</template>
        ,每頁顯示
        <select :value="modelValue" @change="dochange">
            <option v-for="option in options" :key="option" :value="option">{{option}}</option>
        </select>筆
    </span>
</template>
    
<script setup>
    const props = defineProps(["total","options","modelValue"])
    const emits = defineEmits(["rowsChange","update:modelValue"])
    function dochange(event){
        emits('update:modelValue', event.target.value)
        emits('rowsChange')
    }

</script>
    
<style>
    
</style>