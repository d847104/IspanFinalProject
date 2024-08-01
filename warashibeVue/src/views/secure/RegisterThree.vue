<template>

    <section class="vh-100">
    <div class="container-fluid">
        <div class="row">
        <div class="col-sm-6 text-black">

            <div class="d-flex align-items-start h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                <form style="width: 23rem;">

                    <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">註冊會員</h3>
                    <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">已完成註冊，請前往信箱收取驗證信件完成註冊</h3>

                </form>
            </div>

        </div>
            <div class="col-sm-6 px-0 d-none d-sm-block">
                <img src="../../assets/images/barter.jpg"
                alt="Login image" class="w-100 vh-90" style="object-fit: cover; object-position: left;">
            </div>
        </div>
    </div>
    </section>
</template>


<script setup>

</script>

<style scoped>
    .bg-image-vertical {
    position: relative;
    overflow: hidden;
    background-repeat: no-repeat;
    background-position: right center;
    background-size: auto 100%;
    }

    @media (min-width: 1025px) {
        .h-custom-2 {
        height: 100%;
        }
    }
</style>
