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
| 最後登入 | lastLogin  | datetime       |     |          | Not null | getDate() |
| 帳號狀態 | status     | nvarchar(255)  |     |          | Not null | 'active'  |

status:
* active -- 活躍帳號
* ban -- 被禁用


## Wallet 錢包  (會員-錢包: One to One)

| 欄位   | Column   | Type | Key | Identity | Nullable |
|--------|----------|------|-----|----------|----------|
| 錢包ID | walletID | int  | PK  | (1,1)    | Not null |
| 會員ID | memberID | int  | FK  |          | Not null |
| 餘額   | balance  | int  |     |          | Not null |


## WalletRecord 錢包紀錄  (錢包-錢包紀錄: One to Many)

| 欄位      | Column        | Type | Key | Identity | Nullable |
|-----------|---------------|------|-----|----------|----------|
| 錢包紀錄ID | recordID      | int  | PK  | (1,1)    | Not null |
| 錢包ID     | walletID      | int  | FK  |          | Not null |
| 收支紀錄   | balanceChange | int  |     |          | Not null |


## BankAcc 銀行帳號  (錢包-銀行帳號: One to Many)

| 欄位      | Column     | Type          | Key | Identity | Nullable |
|-----------|------------|---------------|-----|----------|----------|
| 銀行帳號ID | bankAccID  | int           | PK  | (1,1)    | Not null |
| 錢包ID     | walletID   | int           | FK  |          | Not null |
| 銀行代碼   | bankCode   | nvarchar(255) |     |          | Not null |
| 分行代碼   | branchCode | nvarchar(255) |     |          | Not null |
| 銀行帳號   | bankAcc    | nvarchar(255) |     |          | Not null |


## Recepient 常用收件人 (會員-常用收件人: One to Many)
| 欄位        | Column      | Type          | Key |
|-------------|-------------|---------------|-----|
| 常用收件人ID | recepientID | int           | PK  |
| 會員ID       | membwrID    | int           | FK  |
| 收件人姓名    | name        | nvarchar(255) |     |
| 收件人手機    | mobile      | nvarchar(255) |     |
| 收件地址      | address     | nvarchar(255) |     |


## Products 商品  (會員-商品: One to Many | 子分類-商品: One to Many)

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


## ProductSpec 商品規格  (商品-商品規格: One to Many)

| 欄位   | column      | type          | Key | Identity | Nullable |
|--------|-------------|---------------|-----|----------|----------|
| 規格ID | specID      | int           | PK  | (1,1)    | Not null |
| 商品ID | productID   | int           | FK  |          | Not null |
| 規格   | spec        | nvarchar(255) |     |          | Not null |


## ProductImg 商品圖片  (商品-商品圖片: One to Many)

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


## ProductPayMethod 商品付款方式 (中介表) (商品-付款方式: Many to Many)
| 欄位      | Column      | Type | Key | Nullable |
|-----------|-------------|------|-----|----------|
| 付款方式ID | payMethodID | int  | FK  | Not null |
| 商品ID     | productID   | int  | FK  | Not null |


## Delivery 配送方式
| 欄位      | Column     | Type          | Key | Identity | Nullable |
|-----------|------------|---------------|-----|----------|----------|
| 配送方式ID | deliveryID | int           | PK  | (1,1)    | Not null |
| 配送方式   | delivery   | nvarchar(255) |     |          | Not null |


## ProductDelivery 商品配送方式 (中介表) (商品-商品配送方式:  Many to Many)
| 欄位      | Column         | Type | Key | Nullable |
|-----------|----------------|------|-----|----------|
| 配送方式ID | transportWayID | int  | FK  | Not null |
| 商品ID     | productID      | int  | FK  | Not null |


## MainCategory 主分類
| 欄位     | Column         | Type         | Key | Identity | Nullable |
|----------|----------------|--------------|-----|----------|----------|
| 主分類ID | mainCategoryID | int           | PK  | (1,1)    | Not null |
| 主分類   | mainCategory   | nvarchar(255) |     |          | Not null |


## SubCategory 子分類  (主分類-子分類: One to Many)
| 欄位     | Column        | Type          | Key | Identity | Nullable |
|----------|---------------|---------------|-----|----------|----------|
| 子分類ID | subCategoryID  | int           | PK  | (1,1)    | Not null |
| 子分類   | subCategory    | nvarchar(255) |     |          | Not null |
| 主分類ID | mainCategoryID | int           | FK  |          | Not null |


## Rank 商品評價 (商品-商品評價: One to Many | 會員-商品評價: One to Many)
| 欄位        | Column    | Type         | Key | Identity | Nullable |
|-------------|-----------|--------------|-----|----------|----------|
| 商品評價ID   | rankID    | int          | PK  | (1,1)    | Not null |
| 商品ID       | productID | int          | FK  |          | Not null |
| 會員ID       | memberID  | int          | FK  |          | Not null |
| 商品評價     | ranking   | int           |     |          | Not null |
| 商品評價留言 | message   | nvarchar(max) |     |          |          |


## Favorite 收藏商品 (中介表) (會員-收藏商品: Many to Many)
| 欄位   | Column   | Type | Key | Nullable |
|--------|----------|------|-----|----------|
| 會員ID | memberID  | int  | FK  | Not null |
| 商品ID | productID | int  | FK  | Not null |


## Cart 購物車  (會員-購物車: One to Many)
| 欄位    | Column   | Type | Key | Identity | Nullable |
|---------|----------|------|-----|----------|----------|
| 購物車ID | cartID   | int  | PK  | (1,1)    | Not null |
| 會員ID   | membwrID | int  | FK  |          | Not null |


## CartProduct 購物車商品 (中介表) (購物車-商品: Many to Many)
| 欄位    | Column    | Type | Key | Nullable |
|---------|-----------|------|-----|----------|
| 購物車ID | cartID    | int  | FK  | Not null |
| 商品ID   | productID | int  | FK  | Not null |
| 商品數量  | quantity  | int  |     | Not null |


## Order 訂單
|  column      |     type     |
|--------------|--------------|
| transport    | int          |FK
| paymentWay   | int          |FK


OrderProduct (中介表)
|  column      |     type     |
|--------------|--------------|
| orderID      | int          |FK
| productID    | int          |FK
| quantity     | int          |nn

Notification
|  column      |     type     |
|--------------|--------------|
| notificationID| int         |PK
| senderID     | int          |FK
| receiverID   | int          |FK
| content      | nvarchar     |

SecondHand
|  column      |     type     |
|--------------| -------------|
| secondHandID | int          |PK
| wishItem     | nvarchar     |

Messenger
|  column      |     type     |
|--------------|--------------|
| msgID        | int          |PK
| senderID     | int          |FK
| receiverID   | int          |FK
| content      | nvarchar     |
| sendTime     | timestamp    |
