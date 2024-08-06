<template>
    <br><br><br><br><br>
    <h3 style="color: azure;">結帳</h3>
    <!-- 顯示訂單列表 -->
    <div>
        <div class="alert alert-light" role="alert">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-10">
                        <h5>HI:{{ BuyerID }}</h5>
                        <!-- <h5>HI:{{ BuyerID }}</h5> -->
                        <!-- <h5 style="color:#6C6C6C;">賣家：{{ order.seller }}</h5> -->
                        <div class="card mb-3">
                            <div class="row g-0">
                                <div class="col-md-4 image-container">
                                    <img src="..." alt="..." />
                                    <!-- <img :src="productImgMap[order.orderID] || '/path/to/default/image.png'"
                                        alt="產品圖片" />     -->
                                </div>
                                <div class="col-md-9">
                                    <div class="card-body">
                                        <h5 style="color:#6C6C6C;">賣家：</h5>
                                        <!-- <h5>訂單編號：{{ order.orderID }}</h5> -->
                                        <h5 class="card-title">品名: </h5>
                                        <div class="container text-left">
                                            <div class="row">
                                                <div class="col">
                                                    商品規格:
                                                </div>
                                                <div class="col">
                                                    單價:
                                                </div>
                                                <div class="col">
                                                    數量:
                                                </div>
                                                <div class="col">
                                                    小計:
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="margin-left: 50px;">
                <div class="form-check">
                    <h5> 運送方式:</h5>
                    <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
                    <label class="form-check-label" for="flexRadioDefault1">
                        宅配(迴圈)
                    </label>
                </div>

                <br>
                <div class="col-md-2">
                    <label class="form-label">收件人姓名</label>
                    <input type="text" class="form-control">
                </div><br>
                <div class="col-md-2">
                    <label class="form-label">聯絡手機</label>
                    <input type="text" class="form-control">
                </div><br>
                <div class="col-md-5">
                    <label class="form-label">收件地址</label>
                    <input type="text" class="form-control">
                </div>
                <br>
                <div class="col-md-3">
                    <label class="form-label">備註</label>
                    <input type="text" class="form-control">
                </div>
                <br><br>
                <form class="row g-3">
                    <div class="form-check col-md-3">
                        <h5> 付款方式:</h5>
                        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
                        <label class="form-check-label col-md3" for="flexRadioDefault1">
                            貨到付款(迴圈)
                        </label>
                    </div>

                    <div class="col-md-3">
                        <fieldset disabled>
                            <label abel for="disabledTextInput" class="form-label">總金額</label>
                            <input type="text" id="disabledTextInput" class="form-control" placeholder="訂單總金額">
                        </fieldset>
                    </div>
                    <div class="col-md-3 ">
                        <button type="button" class="btn btn-secondary align-items-right" :style="{
                            position: 'absolute', margin: '30px', right: '250px'
                        }">付款</button>
                    </div>

                </form>
            </div>



        </div>
    </div>
</template>

<script setup>
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';
import { ref } from 'vue';
let BuyerID = sessionStorage.getItem("memberID");

// const selectedOrder = ref(null);
// const orders = ref([]);
// const productNameMap = ref({}); // 存產品名稱
// const productDescriptionMap = ref({});// 存訂單描述
// const productImgMap = ref({});

// onMounted(() => {
//     fetchOrders(BuyerID);

// });

// async function fetchOrders(buyerID) {
//     Swal.fire({
//         text: "處理中.....",
//         allowOutsideClick: false,
//         showConfirmButton: false
//     });

//     try {
//         const response = await axiosapi.get(`/private/pages/orders/buyer/${buyerID}`);
//         const fetchedOrders = response.data.list || [];

//         if (fetchedOrders.length > 0) {
//             // 根據 ORDERID 降冪排序
//             orders.value = fetchedOrders.sort((a, b) => b.orderID - a.orderID);
//             // 讀取每個訂單的評價狀態
//             orders.value.forEach(order => {
//                 // 檢查本機儲存中的評價狀態
//                 const isRated = localStorage.getItem(`order-${order.orderID}-rated`) === 'true';
//                 order.isRated = isRated;
//             });
//             // 設置選擇的訂單
//             selectedOrder.value = orders.value[0]; // 假設選擇最新的一個訂單
//             // 查詢每個訂單的產品資料
//             for (const order of orders.value) {
//                 await fetchOrderProducts(order.orderID);
//             }
//         } else {
//             orders.value = [];
//             selectedOrder.value = null; // 沒有訂單時的處理
//         }
//         Swal.close();
//     } catch (error) {
//         console.log("error", error);
//         Swal.fire({
//             text: "查詢失敗：" + error.message,
//             icon: "error"
//         });
//     }
// }

// async function fetchOrderProducts(orderID) {
//     try {
//         const response = await axiosapi.get(`/private/pages/orderProducts/order/${orderID}`);
//         const products = response.data.list || [];

//         if (products.length > 0) {
//             const productID = products[0].product; // 確保提取到正確的產品 ID
//             productNameMap.value[orderID] = products[0].productName || '產品名稱未提供';
//             // 查詢產品描述和圖片
//             if (productID) {
//                 await fetchProductDescription(productID, orderID);
//                 await findProductImg(productID, orderID);
//             } else {
//                 productDescriptionMap.value[orderID] = '產品描述未提供';
//                 productImgMap.value[orderID] = '產品圖未提供';
//             }
//         } else {
//             productNameMap.value[orderID] = '產品名稱未提供'; // 沒有產品時的預設值
//             productDescriptionMap.value[orderID] = '產品描述未提供';
//             productImgMap.value[orderID] = '產品圖未提供';
//         }
//     } catch (error) {
//         console.log("Error fetching products:", error);
//         Swal.fire({
//             text: "查詢產品資料失敗：" + error.message,
//             icon: "error"
//         });
//     }
// }


// async function findProductImg(productID, orderID) {
//     try {
//         const response = await axiosapi.get(`/api/productImg/product/${productID}`);
//         const imgData = response.data || {};
//         if (Array.isArray(imgData.list) && imgData.list.length > 0) {
//             const image = imgData.list.find(item => item.product === productID);
//             if (image) {
//                 productImgMap.value[orderID] = `data:image/png;base64,${image.img}`; // 假設圖片格式為 PNG
//             } else {
//                 productImgMap.value[orderID] = '';
//             }
//         } else {
//             productImgMap.value[orderID] = '';
//         }
//     } catch (error) {
//         console.log("Error fetching product image:", error);
//         Swal.fire({
//             text: "查詢產品圖片失敗：" + error.message,
//             icon: "error"
//         });
//     }
// }

</script>

<style scoped>
/* 調整商品圖大小 */
.image-container {
    display: flex;
    justify-content: center;
    align-items: center;
    max-width: 150px;
    max-height: 150px;
    border: none;
    background-color: transparent;
}

.image-container img {
    max-width: 100%;
    max-height: 100%;
    width: auto;
    height: auto;
    border: none;
    display: block;
}

/* 評分STAR */
.rating-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.rating-container {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
}

.rating-stars {
    font-size: 24px;
    color: lightgray;
}

.rating-stars .active {
    color: gold;
}

.rating-stars span {
    cursor: pointer;
    margin: 0 5px;
}

textarea {
    width: 100%;
    height: 80px;
    margin: 10px 0;
}

.button-container {
    display: flex;
    justify-content: center;
    gap: 10px;
}

button {
    margin: 0;
}

/* 訂單明細彈出視窗 */
.order-details-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.order-details-container {
    position: relative;
    /* 關閉鈕定位 */
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    text-align: left;
}

.close-button {
    position: absolute;
    /* 絕對定位 */
    bottom: 10px;
    /* 距離容器底部 10 像素 */
    right: 10px;
    /* 距離容器右邊 10 像素 */
    border: none;
    /* 移除預設邊框 */
    background: transparent;
    /* 背景透明 */
    cursor: pointer;
    /* 鼠標懸停時顯示手型 */
}
</style>