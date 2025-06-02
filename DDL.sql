
-- tables
-- Table: AccessoriesDetails
CREATE TABLE AccessoriesDetails (
    AccessoryID number  NOT NULL,
    Description varchar2(100)  NOT NULL,
    CONSTRAINT AccessoriesDetails_pk PRIMARY KEY (AccessoryID)
) ;

-- Table: Authors
CREATE TABLE Authors (
    AuthorID number  NOT NULL,
    FirstName varchar2(100)  NOT NULL,
    LastName varchar2(100)  NOT NULL,
    Biography varchar2(100)  NOT NULL,
    CONSTRAINT Authors_pk PRIMARY KEY (AuthorID)
) ;

-- Table: BookAuthors
CREATE TABLE BookAuthors (
    BookID number  NOT NULL,
    AuthorID number  NOT NULL,
    CONSTRAINT BookAuthors_pk PRIMARY KEY (BookID,AuthorID)
) ;

-- Table: BookDetails
CREATE TABLE BookDetails (
    BookID number  NOT NULL,
    ISBN varchar2(20)  NOT NULL,
    PublicationYear number  NOT NULL,
    PublisherID number  NOT NULL,
    Language varchar2(50)  NOT NULL,
    PageCount number  NOT NULL,
    Description varchar2(100)  NOT NULL,
    CONSTRAINT Pages CHECK (PageCount > 0),
    CONSTRAINT Year CHECK (PublicationYear >= 1900 ),
    CONSTRAINT BookDetails_pk PRIMARY KEY (BookID)
) ;

-- Table: Categories
CREATE TABLE Categories (
    CategoryID number  NOT NULL,
    CategoryName varchar2(50)  NOT NULL,
    CONSTRAINT Categories_pk PRIMARY KEY (CategoryID)
) ;

-- Table: Customers
CREATE TABLE Customers (
    CustomerID number  NOT NULL,
    FirstName varchar2(100)  NOT NULL,
    LastName varchar2(100)  NOT NULL,
    Email varchar2(100)  NOT NULL,
    Phone varchar2(20)  NOT NULL,
    Address varchar2(200)  NOT NULL,
    City varchar2(50)  NOT NULL,
    PostalCode varchar2(10)  NOT NULL,
    Country varchar2(50)  NOT NULL,
    CONSTRAINT Customers_pk PRIMARY KEY (CustomerID)
) ;

-- Table: GameDetails
CREATE TABLE GameDetails (
    GameID number  NOT NULL,
    Platform varchar2(50)  NOT NULL,
    Developer varchar2(50)  NOT NULL,
    ReleaseYear number  NOT NULL,
    Description varchar2(100)  NOT NULL,
    CONSTRAINT RYear CHECK (ReleaseYear >= 1900 ),
    CONSTRAINT GameDetails_pk PRIMARY KEY (GameID)
) ;

-- Table: MovieDetails
CREATE TABLE MovieDetails (
    MovieID number  NOT NULL,
    Director varchar2(100)  NOT NULL,
    DurationInMinutes number  NOT NULL,
    ReleaseYear number  NOT NULL,
    Language varchar2(50)  NOT NULL,
    Genre varchar2(50)  NOT NULL,
    Description varchar2(100)  NOT NULL,
    CONSTRAINT Time CHECK (DurationInMinutes > 0 ),
    CONSTRAINT MovieDetails_pk PRIMARY KEY (MovieID)
) ;

-- Table: OrderDetails
CREATE TABLE OrderDetails (
    OrderID number  NOT NULL,
    ProductID number  NOT NULL,
    Quantity number  NOT NULL,
    UnitPrice number(10,2)  NOT NULL,
    Discount number(10,2)  NOT NULL,
    CONSTRAINT Discount CHECK (Discount >= 0 AND Discount <= 1 ),
    CONSTRAINT OrderDetails_pk PRIMARY KEY (ProductID,OrderID)
) ;

-- Table: Orders
CREATE TABLE Orders (
    OrderID number  NOT NULL,
    CustomerID number  NOT NULL,
    OrderDate date  NOT NULL,
    OrderStatus varchar2(20)  NOT NULL,
    ShipVia number  NOT NULL,
    CONSTRAINT OrderStatus CHECK (OrderStatus IN ('NEW', 'CANCELLED', 'COMPLETED', 'PROCESSING')),
    CONSTRAINT Orders_pk PRIMARY KEY (OrderID)
) ;

-- Table: Payments
CREATE TABLE Payments (
    PaymentID number  NOT NULL,
    OrderID number  NOT NULL,
    PaymentDate date  NOT NULL,
    PaymentStatus varchar2(20)  NOT NULL,
    CONSTRAINT PaymentStatus CHECK (PaymentStatus IN ('NEW', 'PAID', 'FAILED')),
    CONSTRAINT Payments_pk PRIMARY KEY (PaymentID)
) ;

-- Table: Products
CREATE TABLE Products (
    ProductID number  NOT NULL,
    ProductName varchar2(50)  NOT NULL,
    Price number(10,2)  NOT NULL,
    StockQuantity number  NOT NULL,
    CategoryID number  NOT NULL,
    Rating number(1)  NOT NULL,
    CONSTRAINT Rating CHECK (Rating BETWEEN 1 AND 5 ),
    CONSTRAINT Products_pk PRIMARY KEY (ProductID)
) ;

-- Table: Publishers
CREATE TABLE Publishers (
    PublisherID number  NOT NULL,
    PublisherName varchar2(100)  NOT NULL,
    Country varchar2(50)  NOT NULL,
    CONSTRAINT Publishers_pk PRIMARY KEY (PublisherID)
) ;

-- Table: Shippers
CREATE TABLE Shippers (
    ShipperID number  NOT NULL,
    CompanyName varchar2(100)  NOT NULL,
    Phone number(20)  NOT NULL,
    CONSTRAINT Shippers_pk PRIMARY KEY (ShipperID)
) ;

-- foreign keys
-- Reference: Authors_BookAuthors (table: BookAuthors)
ALTER TABLE BookAuthors ADD CONSTRAINT Authors_BookAuthors
    FOREIGN KEY (AuthorID)
    REFERENCES Authors (AuthorID);

-- Reference: BookDetails_BookAuthors (table: BookAuthors)
ALTER TABLE BookAuthors ADD CONSTRAINT BookDetails_BookAuthors
    FOREIGN KEY (BookID)
    REFERENCES BookDetails (BookID);

-- Reference: BookDetails_Publishers (table: BookDetails)
ALTER TABLE BookDetails ADD CONSTRAINT BookDetails_Publishers
    FOREIGN KEY (PublisherID)
    REFERENCES Publishers (PublisherID);

ALTER TABLE BookDetails ADD CONSTRAINT unique_isbn UNIQUE (ISBN);

-- Reference: MovieDetails_Products (table: MovieDetails)
ALTER TABLE MovieDetails ADD CONSTRAINT MovieDetails_Products
    FOREIGN KEY (MovieID)
    REFERENCES Products (ProductID);

-- Reference: OrderDetails_Orders (table: OrderDetails)
ALTER TABLE OrderDetails ADD CONSTRAINT OrderDetails_Orders
    FOREIGN KEY (OrderID)
    REFERENCES Orders (OrderID);

-- Reference: Orders_Customers (table: Orders)
ALTER TABLE Orders ADD CONSTRAINT Orders_Customers
    FOREIGN KEY (CustomerID)
    REFERENCES Customers (CustomerID);

-- Reference: Payments_Orders (table: Payments)
ALTER TABLE Payments ADD CONSTRAINT Payments_Orders
    FOREIGN KEY (OrderID)
    REFERENCES Orders (OrderID);

-- Reference: Products_AccessoriesDetails (table: AccessoriesDetails)
ALTER TABLE AccessoriesDetails ADD CONSTRAINT Products_AccessoriesDetails
    FOREIGN KEY (AccessoryID)
    REFERENCES Products (ProductID);

-- Reference: Products_BookDetails (table: BookDetails)
ALTER TABLE BookDetails ADD CONSTRAINT Products_BookDetails
    FOREIGN KEY (BookID)
    REFERENCES Products (ProductID);

-- Reference: Products_Categories (table: Products)
ALTER TABLE Products ADD CONSTRAINT Products_Categories
    FOREIGN KEY (CategoryID)
    REFERENCES Categories (CategoryID);

-- Reference: Products_GameDetails (table: GameDetails)
ALTER TABLE GameDetails ADD CONSTRAINT Products_GameDetails
    FOREIGN KEY (GameID)
    REFERENCES Products (ProductID);

-- Reference: Products_OrderDetails (table: OrderDetails)
ALTER TABLE OrderDetails ADD CONSTRAINT Products_OrderDetails
    FOREIGN KEY (ProductID)
    REFERENCES Products (ProductID);

-- Reference: Shippers_Orders (table: Orders)
ALTER TABLE Orders ADD CONSTRAINT Shippers_Orders
    FOREIGN KEY (ShipVia)
    REFERENCES Shippers (ShipperID);

-- End of file.

