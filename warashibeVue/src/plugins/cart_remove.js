import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

function removeCart(cartId){
        axiosapi.delete("/private/pages/cart/delete/"+cartId)
        .then(function(result){
                if(result.data.success){
                        console.log("購物車商品移除成功")
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

export default removeCart;