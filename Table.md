Member
|  column   |     type     |
|  ------   | -----------  |
| memberID  | int          |PK
| account   | nvarchar(50) |
| username  | nvarchar(50) |
| mobile    | nvarchar(50) |

Wallet
|  column   |     type     |
|  ------   | -----------  |
| walletID  | int          |PK
| balance   | int          |
| memberID  | int          |FK

BankAcc
|  column     |     type     |
|  ------     | -----------  |
| bankAccID   | int          |PK
| walletID    | int          |FK
| bankCode    | nvarchar     |
| branchCode  | nvarchar     |
| bankAcc     | nvarchar     |

Product


productSpec
|  column     |     type     |
|  ------     | -----------  |
| specID      | int          |PK
| productID   | int          |FK
| spec        | nvarchar     |

productImg
|  column     |     type     |
|  ------     | -----------  |
| imgID       | int          |PK
| productID   | int          |FK
| img         | bit          |

paymentWay
|  column      |     type     |
|  -----       | -----------  |
| paymentWayID | int          |PK
| paymentWay   | nvarchar     |

productPaymentWay (中介表)
|  column      |     type     |
|  -----       | -----------  |
| paymentWayID | int          |FK
| productID    | int          |FK

transportWay


subCategory
|  column          |   type     |
|  -----           | -----------|
| subCategoryID    | int        |PK
| parentCategoryID | int        |FK

parentCategory
