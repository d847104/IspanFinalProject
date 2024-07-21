import { ref, onMounted } from "vue";
import axiosapi from "@/plugins/axios.js";
import Swal from "sweetalert2";

const products = ref([]);
const start = ref(0);
const rows = ref(4);
const currentPage = ref(0);
const totalPages = ref(0);
const lastPageRows = ref(0);

function callFind(page) {
        if(page){
                start.value = (page-1) * rows.value;
                currentPage.value = page;
        }else{
                start.value = 0;
                currentPage.value = 1;
        }
        let request = {
                "name": "",
                "start": start.value,
                "max": rows.value,
                "order": "productID",
                "dir": false,
        }
        axiosapi.post("/api/products/search",request)
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

export default callFind;