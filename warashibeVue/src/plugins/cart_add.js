import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

function addCartApi(memberId, productId, sellerId, specOne, specTwo, quantity) {
        // 檢查是否為 Vue 的 ref 對象，並提取其值
        memberId = memberId?.value || memberId;
        productId = productId?.value || productId;
        sellerId = sellerId?.value || sellerId;
        specOne = specOne?.value || specOne;
        specTwo = specTwo?.value || specTwo;
        quantity = quantity?.value || quantity;

        let request = {
                "member": memberId,
                "product": productId,
                "seller": sellerId,
                "specOne": specOne,
                "specTwo": specTwo,
                "quantity": quantity
        }

        return axiosapi.post("/api/cart/create",request)
        .then(function(result){
                if(result.data.success){
                        console.log(`購物車新增成功,會員:${memberId}, 商品:${productId}, 規格一:${specOne}, 規格二:${specTwo}, 數量:${quantity}`)
                        Swal.fire({
                                position: "center",
                                icon: "success",
                                title: "已加入購物車",
                                showConfirmButton: false,
                                timer: 800
                        });
                        return result;
                }else{
                        console.log(result.data.message);
                        Swal.fire({
                                icon: "error",
                                text: "錯誤",
                                allowOutsideClick: false,
                        })
                        throw new Error(result.data.message);
                }
        })
        .catch(function(error){
                console.log("error",error)
                console.log(error.message);
                Swal.fire({
                        icon: "error",
                        text: "錯誤",
                        allowOutsideClick: false,
                })
                throw error;
        })
}

export default addCartApi;
