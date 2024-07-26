import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

function callSecondHand(products,start,rows,currentPage,totalPages,total,lastPageRows,page) {
        if(page){
                start.value = (page-1) * rows.value;
                currentPage.value = page;
        }else{
                start.value = 0;
                currentPage.value = 1;
        }
        let request = {
                "start": start.value,
                "max": rows.value,
                "order": "productID",
                "dir": false,
        }
        axiosapi.post("/api/products/second-hand",request)
        .then(function(response){
                products.value = response.data.list;
                total.value = response.data.count;
                totalPages.value = Math.ceil(total.value/rows.value);
                lastPageRows.value = total.value % rows.value;
        })
        .catch(function(error){
                console.log("error",error)
                Swal.fire({
                        icon: "error",
                        text: "錯誤"+error.message,
                        allowOutsideClick: false,
                })
        });
}

export default callSecondHand;