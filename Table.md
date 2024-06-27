1. [Members 會員](#members-會員)
2. [Wallet 錢包](#wallet-錢包)
3. [WalletRecord 錢包紀錄](#walletrecord-錢包紀錄)
4. [BankAcc 銀行帳號](#bankacc-銀行帳號)
5. [Recepient 常用收件人](#recepient-常用收件人)
6. [Products 商品](#products-商品)
7. [ProductSpec 商品規格](#productspec-商品規格)
8. [ProductImg 商品圖片](#productimg-商品圖片)
9. [PayMethod 付款方式](#paymethod-付款方式)
10. [ProductPayMethod 商品付款方式 (中介表)](#productpaymethod-商品付款方式-中介表)
11. [Delivery 配送方式](#delivery-配送方式)
12. [ProductDelivery 商品配送方式 (中介表)](#productdelivery-商品配送方式-中介表)
13. [MainCategory 商品主分類](#maincategory-商品主分類)
14. [SubCategory 商品子分類](#subcategory-商品子分類)
15. [Rank 商品評價](#rank-商品評價)
16. [Favorite 收藏商品 (中介表)](#favorite-收藏商品-中介表)
17. [Cart 購物車](#cart-購物車)
18. [CartProduct 購物車商品 (中介表)](#cartproduct-購物車商品-中介表)
19. [Order 訂單](#order-訂單)
20. [OrderProduct 訂單商品 (中介表)](#orderproduct-訂單商品-中介表)
21. [Notification 通知小鈴鐺](#notification-通知小鈴鐺)
22. [SecondHand 二手換物](#secondhand-二手換物)
23. [Messenger 聊天室](#messenger-聊天室)

## Members 會員

| 欄位     | Column     | Type           | Key | Identity | Nullable | Default   |
|----------|------------|----------------|-----|----------|----------|-----------|
| 會員ID   | memberID   | int            | PK  | (1,1)    | Not null |           |
| 帳號     | account    | nvarchar(255)  |     |          | Not null |           |
| 密碼     | password   | nvarchar(255)  |     |          | Not null |           |
| 名字     | username   | nvarchar(255)  |     |          | Not null |           |
| 手機     | mobile     | nvarchar(255)  |     |          | Not null |           |
| 性別     | gender     | nvarchar(255)  |     |          |          |           |
| 照片     | profileImg | varbinary(max) |     |          |          |           |
| 賣場介紹 | intro      | nvarchar(max)  |     |          |          |           |
| 創建時間 | createTime | datetime       |     |          | Not null | getDate() |
| 最後登入 | lastLogin  | datetime       |     |          |          | getDate() |
| 帳號狀態 | status     | nvarchar(255)  |     |          | Not null | 'active'  |

status:
* active -- 活躍帳號
* ban -- 被禁用


## Wallet 錢包
(會員-錢包: One to One)

| 欄位   | Column   | Type | Key | Identity | Nullable |
|--------|----------|------|-----|----------|----------|
| 錢包ID | walletID | int  | PK  | (1,1)    | Not null |
| 會員ID | memberID | int  | FK  |          | Not null |
| 餘額   | balance  | int  |     |          | Not null |


## WalletRecord 錢包紀錄
(錢包-錢包紀錄: One to Many)

| 欄位      | Column            | Type      | Key | Identity | Nullable |
|-----------|-------------------|-----------|-----|----------|----------|
| 錢包紀錄ID | recordID          | int       | PK  | (1,1)    | Not null |
| 錢包ID     | walletID          | int       | FK  |          | Not null |
| 收支金額   | balanceChange     | int       |     |          | Not null |
| 異動時間   | balanceChangeTime | timestamp |     |          | Not null |


## BankAcc 銀行帳號
(錢包-銀行帳號: One to Many)

| 欄位      | Column     | Type          | Key | Identity | Nullable |
|-----------|------------|---------------|-----|----------|----------|
| 銀行帳號ID | bankAccID  | int           | PK  | (1,1)    | Not null |
| 錢包ID     | walletID   | int           | FK  |          | Not null |
| 銀行代碼   | bankCode   | nvarchar(255) |     |          | Not null |
| 分行代碼   | branchCode | nvarchar(255) |     |          | Not null |
| 銀行帳號   | bankAcc    | nvarchar(255) |     |          | Not null |


## Recepient 常用收件人
(會員-常用收件人: One to Many)

| 欄位        | Column      | Type          | Key | Identity | Nullable |
|-------------|-------------|---------------|-----|----------|----------|
| 常用收件人ID | recepientID | int           | PK  | (1,1)    | Not null |
| 會員ID       | membwrID    | int           | FK  |          | Not null |
| 收件人姓名    | name        | nvarchar(255) |     |          | Not null |
| 收件人手機    | mobile      | nvarchar(255) |     |          | Not null |
| 收件地址      | address     | nvarchar(255) |     |          |          |


## Products 商品
(會員-商品: One to Many | 子分類-商品: One to Many)

| 欄位      | Column        | Type          | Key | Identity | Nullable | Default   |
|-----------|---------------|---------------|-----|----------|----------|-----------|
| 商品ID    | productID     | int           | PK  | (1,1)    | Not null |           |
| 會員ID    | memberID      | int           | FK  |          | Not null |           |
| 商品名稱  | productName   | nvarchar(255) |     |          | Not null |           |
| 價格      | price         | int           |     |          | Not null |           |
| 庫存量    | stock         | int           |     |          | Not null |           |
| 子分類    | subCategoryID | int           | FK  |          | Not null |           |
| 商品描述  | description   | nvarchar(max) |     |          |          |           |
| 上架時間  | uploadDate    | datetime      |     |          | Not null | getDate() |
| 更新時間  | updateTime    | timestamp     |     |          | Not null |           |


## ProductSpec 商品規格
(商品-商品規格: One to Many)

| 欄位   | column      | type          | Key | Identity | Nullable |
|--------|-------------|---------------|-----|----------|----------|
| 規格ID | specID      | int           | PK  | (1,1)    | Not null |
| 商品ID | productID   | int           | FK  |          | Not null |
| 規格   | spec        | nvarchar(255) |     |          | Not null |


## ProductImg 商品圖片
(商品-商品圖片: One to Many)

| 欄位      | Column      | Type           | Key | Identity | Nullable |
|-----------|-------------|----------------|-----|-----------|----------|
| 商品圖片ID | imgID       | int            | PK  | (1,1)     | Not null |
| 商品ID     | productID   | int            | FK  |           | Not null |
| 圖片       | img         | varbinary(max) |     |           | Not null |


## PayMethod 付款方式
| 欄位      | Column       | Type         | Key | Identity | Nullable |
|-----------|-------------| --------------|-----|----------|----------|
| 付款方式ID | payMethodID | int           | PK  | (1,1)    | Not null |
| 付款方式   | payMethod   | nvarchar(255) |     |          | Not null |


## ProductPayMethod 商品付款方式 (中介表)
(商品-付款方式: Many to Many)

| 欄位      | Column      | Type | Key | Nullable |
|-----------|-------------|------|-----|----------|
| 付款方式ID | payMethodID | int  | FK  | Not null |
| 商品ID     | productID   | int  | FK  | Not null |


## Delivery 配送方式
| 欄位      | Column      | Type          | Key | Identity | Nullable |
|-----------|-------------|---------------|-----|----------|----------|
| 配送方式ID | deliveryID  | int           | PK  | (1,1)    | Not null |
| 配送方式   | delivery    | nvarchar(255) |     |          | Not null |
| 運費       | deliveryFee | int           |     |          | Not null |


## ProductDelivery 商品配送方式 (中介表)
(商品-商品配送方式:  Many to Many)

| 欄位      | Column     | Type | Key | Nullable |
|-----------|------------|------|-----|----------|
| 配送方式ID | deliveryID | int  | FK  | Not null |
| 商品ID     | productID  | int  | FK  | Not null |


## MainCategory 商品主分類
| 欄位     | Column         | Type         | Key | Identity | Nullable |
|----------|----------------|--------------|-----|----------|----------|
| 主分類ID | mainCategoryID | int           | PK  | (1,1)    | Not null |
| 主分類   | mainCategory   | nvarchar(255) |     |          | Not null |


## SubCategory 商品子分類
(主分類-子分類: One to Many)

| 欄位     | Column        | Type          | Key | Identity | Nullable |
|----------|---------------|---------------|-----|----------|----------|
| 子分類ID | subCategoryID  | int           | PK  | (1,1)    | Not null |
| 子分類   | subCategory    | nvarchar(255) |     |          | Not null |
| 主分類ID | mainCategoryID | int           | FK  |          | Not null |


## Rank 商品評價
(商品-商品評價: One to Many | 會員-商品評價: One to Many)

| 欄位      | Column    | Type         | Key | Identity | Nullable | Default   | 
|-----------|-----------|--------------|-----|----------|----------|-----------|
| 商品評價ID | rankID    | int          | PK  | (1,1)    | Not null |           |
| 商品ID     | productID | int          | FK  |          | Not null |           |
| 會員ID     | memberID  | int          | FK  |          | Not null |           |
| 商品評價   | ranking   | int           |     |          | Not null |           |
| 評價留言   | rankmsg   | nvarchar(max) |     |          |          |           |
| 評價日期   | rankDate  | datetime      |     |          | Not null | getDate() |


## Favorite 收藏商品 (中介表)
(會員-收藏商品: Many to Many)

| 欄位   | Column   | Type | Key | Nullable |
|--------|----------|------|-----|----------|
| 會員ID | memberID  | int  | FK  | Not null |
| 商品ID | productID | int  | FK  | Not null |


## Cart 購物車
(會員-購物車: One to Many)

| 欄位    | Column   | Type | Key | Identity | Nullable |
|---------|----------|------|-----|----------|----------|
| 購物車ID | cartID   | int  | PK  | (1,1)    | Not null |
| 會員ID   | membwrID | int  | FK  |          | Not null |


## CartProduct 購物車商品 (中介表)
(購物車-商品: Many to Many)

| 欄位    | Column    | Type | Key | Nullable |
|---------|-----------|------|-----|----------|
| 購物車ID | cartID    | int  | FK  | Not null |
| 商品ID   | productID | int  | FK  | Not null |
| 商品數量  | quantity  | int  |     | Not null |


## Order 訂單
(賣家會員-訂單: One to Many | 買家會員-訂單: One to Many | 配送方式-訂單: One to Many | 付款方式-訂單: One to Many)

| 欄位         | Column      | Type          | Key | Identity | Nullable | Default    | 
|--------------|-------------|---------------|-----|----------|----------|------------|
| 訂單ID       | orderID     | int           | PK  | (1,1)    | Not null |            |
| 賣家會員ID   | sellerID    | int           | FK  |          | Not null |            |
| 買家會員ID   | buyerID     | int           | FK  |          | Not null |            |
| 配送方式ID   | deliveryID  | int           | FK  |          | Not null |            |
| 運費         | deliveryFee | int           |     |          | Not null |            |
| 付款方式ID   | payMethodID | int           | FK  |          | Not null |            |
| 下單時間     | orderTime   | timestamp     |     |          | Not null |            |
| 訂單狀態     | orderStatus | nvarchar(255) |     |          | Not null | 'newOrder' |
| 狀態更新時間 | orderUpdate | timestamp     |     |          |          |            |

orderStatus:
* newOrder -- 買家下單
* paid -- 已付款
* confirmed -- 賣家接單
* shipped -- 已出貨
* completed -- 已到貨
* returned -- 退貨中
* canceled -- 已取消


## OrderProduct 訂單商品 (中介表)
(訂單-商品 Many to Many)

| 欄位     | Column    | Type | Key | Nullable |
|----------|-----------|------|----|-----------|
| 訂單ID   | orderID   | int  | FK | Not null  |
| 商品ID   | productID | int  | FK | Not null  |
| 商品數量 | quantity  | int  |    | Not null  |
| 商品價格 | price     | int  |    | Not null  |


## Notification 通知小鈴鐺
| 欄位       | Column         | Type         | Key | Identity | Nullable | Default   | 
|------------|----------------|--------------|-----|----------|----------|-----------|
| 通知ID     | notificationID | int           | PK | (1,1)    | Not null |           |
| 收訊會員ID | receiverID     | int           | FK |          | Not null |           |
| 發訊會員ID | senderID       | int           | FK |          |          |           |
| 訂單ID     | orderID        | int           | FK |          |          |           |
| 通知內容   | content        | nvarchar(max) |    |          | Not null |           |
| 通知日期   | notifyDate     | datetime      |    |          | Not null | getDate() |


## SecondHand 二手換物
| 欄位      | Column       | Type          | Key | Identity | Nullable | Default   |
|-----------|--------------| --------------|-----|----------|----------|-----------|
| 二手物品ID | secondHandID | int           | PK  | (1,1)    | Not null |           |
| 會員ID    | memberID      | int           | FK  |          | Not null |           |
| 商品名稱  | productName   | nvarchar(255) |     |          | Not null |           |
| 庫存量    | stock         | int           |     |          | Not null |           |
| 子分類    | subCategoryID | int           | FK  |          | Not null |           |
| 商品描述  | description   | nvarchar(max) |     |          |          |           |
| 許願物品  | wishItem      | nvarchar(max) |     |          |          |           |
| 上架時間  | uploadDate    | datetime      |     |          | Not null | getDate() |
| 更新時間  | updateTime    | timestamp     |     |          |          |           |


## Messenger 聊天室
| 欄位       | Column     | Type         | Key | Identity | Nullable | 
|------------|------------|--------------|-----|----------|----------|
| 訊息ID     | msgID      | int           | PK | (1,1)    | Not null |
| 發訊會員ID | senderID   | int           | FK |          | Not null |
| 收訊會員ID | receiverID | int           | FK |          | Not null |
| 訊息內容   | msg        | nvarchar(max) |    |          | Not null |
| 發訊時間   | msgTime    | timestamp     |    |          | Not null |
