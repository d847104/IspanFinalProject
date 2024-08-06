import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

function addCartApi(memberId,productId,sellerId,specOne,specTwo,quantity){
        let request = {
                "member": memberId,
                "product": productId,
                "seller": sellerId,
                "specOne": specOne,
                "specTwo": specTwo,
                "quantity": quantity
        }

        axiosapi.post("/api/cart/create",request)
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

export default addCartApi;