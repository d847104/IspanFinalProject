<template>
        <template v-if="selectedItems.length > 0">
                <div >
                        傳送成功
                </div>
        </template>
</template>
        
<script setup>
        import { useRouter, useRoute } from 'vue-router';
        import { computed, onMounted } from 'vue';

        const router = useRouter();
        const route = router.currentRoute.value;
        const selectedItems = computed(() => route.state.selectedItems || []); // 獲取傳遞過來的商品資料
</script>
        
<style>
        
</style>