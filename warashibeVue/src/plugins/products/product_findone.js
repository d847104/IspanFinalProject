import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

function callFindOne(productID,product){
        axiosapi.get("/api/products/"+productID)
        .then(function(response){
                product.value = response.data.list;
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

export default callFindOne;