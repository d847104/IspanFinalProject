import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

function addCart(memberId,productId,productSpecId,sellerId,quantity){
        let request = {
                "member": memberId,
                "product": productId,
                "productSpec": productSpecId,
                "seller": sellerId,
                "quantity": quantity
        }

        axiosapi.put("/private/pages/cart/create",request)
        .then(function(result){
                if(result.data.success){
                        console.log("購物車新增成功")
                }else{
                        Swal.fire({
                                icon: "error",
                                text: "錯誤"+error.message,
                                allowOutsideClick: false,
                        })
                }
        })
        .catch(function(error){
                console.log("error",error)
                Swal.fire({
                        icon: "error",
                        text: "錯誤"+error.message,
                        allowOutsideClick: false,
                })
        })

}

export default addCart;