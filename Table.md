Member
|  column   |     type     |
|  ------   | -----------  |
| memberID  | int          |PK
| account   | nvarchar(50) |
| username  | nvarchar(50) |
| mobile    | nvarchar(50) |
| intro     | nvarchar     |

Wallet
|  column   |     type     |
|  ------   | -----------  |
| walletID  | int          |PK
| balance   | int          |
| memberID  | int          |FK

WalletRecord
|  column   |     type     |
|  ------   | -----------  |
| recordID  | int          |PK
| walletID  | int          |FK
| balanceVar| int          |

BankAcc
|  column     |     type     |
|  ------     | -----------  |
| bankAccID   | int          |PK
| walletID    | int          |FK
| bankCode    | nvarchar     |
| branchCode  | nvarchar     |
| bankAcc     | nvarchar     |

Product


ProductSpec
|  column     |     type     |
|  ------     | -----------  |
| specID      | int          |PK
| productID   | int          |FK
| spec        | nvarchar     |

ProductImg
|  column     |     type     |
|  ------     | -----------  |
| imgID       | int          |PK
| productID   | int          |FK
| img         | bit          |

PaymentWay
|  column      |     type     |
|  -----       | -----------  |
| paymentWayID | int          |PK
| paymentWay   | nvarchar     |

ProductPaymentWay (中介表)
|  column      |     type     |
|  -----       | -----------  |
| paymentWayID | int          |FK
| productID    | int          |FK

TransportWay
|  column      |     type     |
|  -----       | -----------  |
| transportWayID | int          |PK
| transportWay   | nvarchar     |

ProductTransportWay (中介表)
|  column      |     type     |
|  -----       | -----------  |
| transportWayID | int        |PK
| productID    | int          |FK

SubCategory
|  column          |   type     |
|  -----           | -----------|
| subCategoryID    | int        |PK
| mainCategoryID   | int        |FK
| productID        | int        |FK

MainCategory
|  column          |   type     |
|  -----           | -----------|
| mainCategoryID   | int        |PK

Rank
|  column          |   type     |
|  -----           | -----------|
| rankID           | int        |
| productID        | int        |FK
| memberID         | int        |FK
| ranking          | int        |
| message          | text       |

Favorite(中介表)
|  column      |     type     |
|  -----       | -----------  |
| memberID     | int          |FK
| productID    | int          |FK

Cart
|  column      |     type     |
|  -----       | -----------  |
| cartID      | int           |Pk
| membwrID    | int           |FK

CartProduct (中介表)
|  column      |     type     |
|  -----       | -----------  |
| cartID       | int          |FK
| productID    | int          |FK
| quantity     | int          |nn

UsualContact
|  column      |     type     |
|  -----       | -----------  |
| contactID    | int           |Pk
| membwrID     | int           |FK
| name         | nvarchar      |nn
| mobile       | nvarchar      |nn
| address      | nvarchar      |

Order
|  column      |     type     |
|  -----       | -----------  |
| transport    | int          |FK
| paymentWay   | int          |FK


OrderProduct (中介表)
|  column      |     type     |
|  -----       | -----------  |
| orderID      | int          |FK
| productID    | int          |FK
| quantity     | int          |nn

Notification
|  column      |     type     |
|  -----       | -----------  |
| notificationID| int         |PK
| senderID     | int          |FK
| receiverID   | int          |FK
| content      | nvarchar     |

SecondHand
|  column      |     type     |
|  -----       | -----------  |
| secondHandID | int          |PK
| wishItem     | nvarchar     |

Messenger
|  column      |     type     |
|  -----       | -----------  |
| msgID        | int          |PK
| senderID     | int          |FK
| receiverID   | int          |FK
| content      | nvarchar     |
| sendTime     | timestamp    |
