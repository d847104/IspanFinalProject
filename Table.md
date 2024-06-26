## 1. Members 會員

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


## 2. Wallet 錢包  (會員-錢包: One to One)

| 欄位   | Column   | Type | Key | Identity | Nullable |
|--------|----------|------|-----|----------|----------|
| 錢包ID | walletID | int  | PK  | (1,1)    | Not null |
| 會員ID | memberID | int  | FK  |          | Not null |
| 餘額   | balance  | int  |     |          | Not null |


## 3. WalletRecord 錢包紀錄  (錢包-錢包紀錄: One to Many)

| 欄位      | Column        | Type | Key | Identity | Nullable |
|-----------|---------------|------|-----|----------|----------|
| 錢包紀錄ID | recordID      | int  | PK  | (1,1)    | Not null |
| 錢包ID     | walletID      | int  | FK  |          | Not null |
| 收支紀錄   | balanceChange | int  |     |          | Not null |


## 4. BankAcc 銀行帳號  (錢包-銀行帳號: One to Many)

| 欄位      | Column     | Type          | Key | Identity | Nullable |
|-----------|------------|---------------|-----|----------|----------|
| 銀行帳號ID | bankAccID  | int           | PK  | (1,1)    | Not null |
| 錢包ID     | walletID   | int           | FK  |          | Not null |
| 銀行代碼   | bankCode   | nvarchar(255) |     |          | Not null |
| 分行代碼   | branchCode | nvarchar(255) |     |          | Not null |
| 銀行帳號   | bankAcc    | nvarchar(255) |     |          | Not null |


## 5. Products 商品  (會員-商品: One to Many | 子分類-商品: One to Many)

| Column        | Type          | Key | Identity | Nullable | Default   |
|---------------|---------------|-----|----------|----------|-----------|
| productID     | int           | PK  | (1,1)    | Not null |           |
| memberID      | int           | FK  |          | Not null |           |
| productName   | nvarchar(255) |     |          | Not null |           |
| price         | int           |     |          | Not null |           |
| stock         | int           |     |          | Not null |           |
| subCategoryID | int           | FK  |          | Not null |           |
| description   | nvarchar(max) |     |          |          |           |
| uploadDate    | datetime      |     |          | Not null | getDate() |
| updateTime    | timestamp     |     |          | Not null |           |


## 6. ProductSpec 商品規格  (商品-商品規格: One to Many)

|  column     |     type     |
|-------------|--------------|
| specID      | int          |PK
| productID   | int          |FK
| spec        | nvarchar     |


## 7. ProductImg 商品圖片  (商品-商品圖片: One to Many)

|  column     |     type     |
|-------------|--------------|
| imgID       | int          |PK
| productID   | int          |FK
| img         | bit          |

PaymentWay
|  column      |     type     |
|--------------| -------------|
| paymentWayID | int          |PK
| paymentWay   | nvarchar     |

ProductPaymentWay (中介表)
|  column      |     type     |
|--------------|--------------|
| paymentWayID | int          |FK
| productID    | int          |FK

TransportWay
|  column        |     type     |
|----------------|--------------|
| transportWayID | int          |PK
| transportWay   | nvarchar     |

ProductTransportWay (中介表)
|  column        |     type     |
|----------------|--------------|
| transportWayID | int          |PK
| productID      | int          |FK

SubCategory
|  column          |   type     |
|------------------|------------|
| subCategoryID    | int        |PK
| mainCategoryID   | int        |FK
| productID        | int        |FK

MainCategory
|  column          |   type     |
|------------------|------------|
| mainCategoryID   | int        |PK

Rank
|  column          |   type     |
|------------------|------------|
| rankID           | int        |
| productID        | int        |FK
| memberID         | int        |FK
| ranking          | int        |
| message          | text       |

Favorite(中介表)
|  column      |     type     |
|--------------|--------------|
| memberID     | int          |FK
| productID    | int          |FK

Cart
|  column     |     type     |
|-------------|--------------|
| cartID      | int          |PK
| membwrID    | int          |FK

CartProduct (中介表)
|  column      |     type     |
|--------------|--------------|
| cartID       | int          |FK
| productID    | int          |FK
| quantity     | int          |nn

UsualContact
|  column      |     type     |
|--------------| -------------|
| contactID    | int          |PK
| membwrID     | int          |FK
| name         | nvarchar     |nn
| mobile       | nvarchar     |nn
| address      | nvarchar     |

Order
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
