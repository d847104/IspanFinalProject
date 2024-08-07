import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

function addFav(memberId,productId,sellerId) {
        let request = {
                "memberID": memberId,
                "productID": productId,
                "sellerID": sellerId,
        }
        axiosapi.post("/ajax/favorite/insert", request)
        .then(function(result){
                if(result.data.success){
                        console.log("收藏商品成功")
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
                        text: "錯誤:"+error.message,
                        allowOutsideClick: false,
                })
        })
}

export default addFav;