import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";


function callCart(memberId,cartList){
        axiosapi.get("/private/pages/cart/member/"+memberId)
        .then(function(response){
                cartList.value = response.data.list;
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

export default callCart;