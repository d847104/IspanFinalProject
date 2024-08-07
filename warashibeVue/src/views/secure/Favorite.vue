<template>
    <div class="container mt-4">
        <h2>收藏訂單</h2>
        <div v-if="favorites.length">
            <FavoriteItem v-for="favorite in favorites" :key="favorite.product" :item="favorite" />
        </div>
        <div v-else>
            <p>沒有收藏的商品。</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import axiosapi from '@/plugins/axios';
import FavoriteItem from '@/components/FavoriteItem.vue';

const user = inject("user");
const favorites = ref([]);

const fetchFavorites = async () => {
    try {
        const { data } = await axiosapi.get(`/ajax/favorite/member/${user.value.id}`);
        favorites.value = data.list;
    } catch (error) {
        console.error('獲取收藏訂單失敗:', error);
    }
};

onMounted(fetchFavorites);
</script>

<style scoped>
.container {
    margin-top: 2rem;
}
</style>
