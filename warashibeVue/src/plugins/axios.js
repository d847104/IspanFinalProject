import axios from "axios";
import Swal from "sweetalert2";

const axiosapi = axios.create({
    baseURL: import.meta.env.VITE_API_URL
});

// 從 sessionStorage 中讀取 token
const token = sessionStorage.getItem('token');
if (token) {
    axiosapi.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}

axiosapi.interceptors.response.use(
    function (response) {
        return response;
    },
    function (error) {
        if (error.response && error.response.status && error.response.status === 403) {
            sessionStorage.clear();
            Swal.fire({
                text: "失敗(403)：" + error.message,
                icon: "error"
            }).then(function () {
                window.location.href = "/secure/login";
            });
            return Promise.reject();
        }
        return Promise.reject(error);
    }
);
export default axiosapi;