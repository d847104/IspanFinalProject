<template>
    <section class="">
        <div class="container">
            <h1>瀏覽紀錄</h1>
            <div class="row">
                <!-- content -->
                <div class="col-lg-12">
                    <BrowsingHistoryItem v-for="item in browsingHistory" :key="item.id" :item="item" />
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import axiosapi from '@/plugins/axios';
import BrowsingHistoryItem from '@/components/BrowsingHistoryItem.vue';

const user = inject("user");
const browsingHistory = ref([]);

const fetchBrowsingHistory = async () => {
    if (user && user.value && user.value.id) {
        try {
            const response = await axiosapi.get(`/api/browsing-history/${user.value.id}`);
            browsingHistory.value = response.data;
        } catch (error) {
            console.error('獲取瀏覽紀錄失敗:', error);
        }
    }
};

onMounted(() => {
    fetchBrowsingHistory();
});
</script>
