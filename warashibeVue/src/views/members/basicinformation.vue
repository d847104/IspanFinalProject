<template>
        <br><h3 style="color:#CCEEFF;">會員基本資料</h3>
<div class="row">
    <div class="col-md-8">
        <div class="alert alert-light" role="alert">
            <h5>關於我</h5>
        
            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">會員ID</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="floatingInputDisabled" placeholder="warashibe0001" disabled>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">姓名</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="floatingInputDisabled" placeholder="YourName" disabled>
                </div>
                <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon icon="fa-solid fa-pen-clip" /></button>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">帳號</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="floatingInputDisabled" placeholder="自定名稱" disabled>
                </div>
                <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon icon="fa-solid fa-pen" /></button>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">手機</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="floatingInputDisabled" placeholder="0987654321" disabled>
                </div>
                <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon icon="fa-solid fa-pencil" /></button>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">Email</label>
                <div class="col-sm-7">
                    <input type="email" class="form-control" id="floatingInputDisabled" placeholder="abcdefghijklmnopqrstuvwxyz12@example.com" disabled>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">性別</label>
                <div class="col-sm-7">
                    <button type="button" class="btn btn-outline-secondary mx-2" disabled>男</button>
                    <button type="button" class="btn btn-outline-secondary mx-2" disabled>女</button>
                    <button type="button" class="btn btn-secondary mx-2" disabled>不願透露</button>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">註冊時間</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="floatingInputDisabled" placeholder="2024-07-13 00:46" disabled>
                </div>
            </div>

            <div class="row mb-3">
                <label class="col-sm-2 col-form-label text-end">最後登入</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="floatingInputDisabled" placeholder="2024-07-20 19:32" disabled>
                </div>
            </div>
          
        </div>
    </div>

    <div class="col-md-4 ">
        <div class="card" style="width: 18rem;">
            <img src="/src/img/貓頭鷹.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">YourName</h5>
                    <p class="card-text">帳號：</p>
                    <a href="#" class="btn btn-secondary float-end"><font-awesome-icon icon="fa-solid fa-repeat" /></a>
                </div>
        </div>
    </div>
</div>

</template>
    
<script setup>
    
</script>
    
<style>
    
</style>