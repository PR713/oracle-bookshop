-- TABLES
-- TABLE: ACCESSORIES_DETAILS
CREATE TABLE ACCESSORIES_DETAILS (
                                     ACCESSORY_ID NUMBER NOT NULL,
                                     DESCRIPTION VARCHAR2(100) NOT NULL,
                                     CONSTRAINT ACCESSORIES_DETAILS_PK PRIMARY KEY (ACCESSORY_ID)
);

-- TABLE: AUTHORS
CREATE TABLE AUTHORS (
                         AUTHOR_ID NUMBER NOT NULL,
                         FIRST_NAME VARCHAR2(100) NOT NULL,
                         LAST_NAME VARCHAR2(100) NOT NULL,
                         BIOGRAPHY VARCHAR2(100) NOT NULL,
                         CONSTRAINT AUTHORS_PK PRIMARY KEY (AUTHOR_ID)
);

-- TABLE: BOOK_AUTHORS
CREATE TABLE BOOK_AUTHORS (
                              BOOK_ID NUMBER NOT NULL,
                              AUTHOR_ID NUMBER NOT NULL,
                              CONSTRAINT BOOK_AUTHORS_PK PRIMARY KEY (BOOK_ID, AUTHOR_ID)
);

-- TABLE: BOOK_DETAILS
CREATE TABLE BOOK_DETAILS (
                              BOOK_ID NUMBER NOT NULL,
                              ISBN VARCHAR2(20) NOT NULL,
                              PUBLICATION_YEAR NUMBER NOT NULL,
                              PUBLISHER_ID NUMBER NOT NULL,
                              LANGUAGE VARCHAR2(50) NOT NULL,
                              PAGE_COUNT NUMBER NOT NULL,
                              DESCRIPTION VARCHAR2(100) NOT NULL,
                              CONSTRAINT PAGES CHECK (PAGE_COUNT > 0),
                              CONSTRAINT YEAR CHECK (PUBLICATION_YEAR >= 1900),
                              CONSTRAINT BOOK_DETAILS_PK PRIMARY KEY (BOOK_ID)
);

-- TABLE: CATEGORIES
CREATE TABLE CATEGORIES (
                            CATEGORY_ID NUMBER NOT NULL,
                            CATEGORY_NAME VARCHAR2(50) NOT NULL,
                            CONSTRAINT CATEGORIES_PK PRIMARY KEY (CATEGORY_ID)
);

-- TABLE: CUSTOMERS
CREATE TABLE CUSTOMERS (
                           CUSTOMER_ID NUMBER NOT NULL,
                           FIRST_NAME VARCHAR2(100) NOT NULL,
                           LAST_NAME VARCHAR2(100) NOT NULL,
                           EMAIL VARCHAR2(100) NOT NULL,
                           PHONE VARCHAR2(20) NOT NULL,
                           ADDRESS VARCHAR2(200) NOT NULL,
                           CITY VARCHAR2(50) NOT NULL,
                           POSTAL_CODE VARCHAR2(10) NOT NULL,
                           COUNTRY VARCHAR2(50) NOT NULL,
                           CONSTRAINT CUSTOMERS_PK PRIMARY KEY (CUSTOMER_ID)
);

-- TABLE: GAME_DETAILS
CREATE TABLE GAME_DETAILS (
                              GAME_ID NUMBER NOT NULL,
                              PLATFORM VARCHAR2(50) NOT NULL,
                              DEVELOPER VARCHAR2(50) NOT NULL,
                              RELEASE_YEAR NUMBER NOT NULL,
                              DESCRIPTION VARCHAR2(100) NOT NULL,
                              CONSTRAINT R_YEAR CHECK (RELEASE_YEAR >= 1900),
                              CONSTRAINT GAME_DETAILS_PK PRIMARY KEY (GAME_ID)
);

-- TABLE: MOVIE_DETAILS
CREATE TABLE MOVIE_DETAILS (
                               MOVIE_ID NUMBER NOT NULL,
                               DIRECTOR VARCHAR2(100) NOT NULL,
                               DURATION_IN_MINUTES NUMBER NOT NULL,
                               RELEASE_YEAR NUMBER NOT NULL,
                               LANGUAGE VARCHAR2(50) NOT NULL,
                               GENRE VARCHAR2(50) NOT NULL,
                               DESCRIPTION VARCHAR2(100) NOT NULL,
                               CONSTRAINT TIME CHECK (DURATION_IN_MINUTES > 0),
                               CONSTRAINT MOVIE_DETAILS_PK PRIMARY KEY (MOVIE_ID)
);

-- TABLE: ORDER_DETAILS
CREATE TABLE ORDER_DETAILS (
                               ORDER_ID NUMBER NOT NULL,
                               PRODUCT_ID NUMBER NOT NULL,
                               QUANTITY NUMBER NOT NULL,
                               UNIT_PRICE NUMBER(10,2) NOT NULL,
                               DISCOUNT NUMBER(10,2) NOT NULL,
                               CONSTRAINT DISCOUNT CHECK (DISCOUNT >= 0 AND DISCOUNT <= 1),
                               CONSTRAINT ORDER_DETAILS_PK PRIMARY KEY (PRODUCT_ID, ORDER_ID)
);

-- TABLE: ORDERS
CREATE TABLE ORDERS (
                        ORDER_ID NUMBER NOT NULL,
                        CUSTOMER_ID NUMBER NOT NULL,
                        ORDER_DATE DATE NOT NULL,
                        ORDER_STATUS VARCHAR2(20) NOT NULL,
                        SHIP_VIA NUMBER,
                        CONSTRAINT ORDER_STATUS CHECK (ORDER_STATUS IN ('NEW', 'CANCELLED', 'COMPLETED', 'PROCESSING')),
                        CONSTRAINT ORDERS_PK PRIMARY KEY (ORDER_ID)
);

-- TABLE: PAYMENTS
CREATE TABLE PAYMENTS (
                          PAYMENT_ID NUMBER NOT NULL,
                          ORDER_ID NUMBER NOT NULL,
                          PAYMENT_DATE DATE NOT NULL,
                          PAYMENT_STATUS VARCHAR2(20) NOT NULL,
                          CONSTRAINT PAYMENT_STATUS CHECK (PAYMENT_STATUS IN ('NEW', 'PAID', 'FAILED')),
                          CONSTRAINT PAYMENTS_PK PRIMARY KEY (PAYMENT_ID)
);

-- TABLE: PRODUCTS
CREATE TABLE PRODUCTS (
                          PRODUCT_ID NUMBER NOT NULL,
                          PRODUCT_NAME VARCHAR2(50) NOT NULL,
                          PRICE NUMBER(10,2) NOT NULL,
                          STOCK_QUANTITY NUMBER NOT NULL,
                          CATEGORY_ID NUMBER NOT NULL,
                          RATING NUMBER(1) NOT NULL,
                          CONSTRAINT RATING CHECK (RATING BETWEEN 1 AND 5),
                          CONSTRAINT PRODUCTS_PK PRIMARY KEY (PRODUCT_ID)
);

-- TABLE: PUBLISHERS
CREATE TABLE PUBLISHERS (
                            PUBLISHER_ID NUMBER NOT NULL,
                            PUBLISHER_NAME VARCHAR2(100) NOT NULL,
                            COUNTRY VARCHAR2(50) NOT NULL,
                            CONSTRAINT PUBLISHERS_PK PRIMARY KEY (PUBLISHER_ID)
);

-- TABLE: SHIPPERS
CREATE TABLE SHIPPERS (
                          SHIPPER_ID NUMBER NOT NULL,
                          COMPANY_NAME VARCHAR2(100) NOT NULL,
                          PHONE NUMBER(20) NOT NULL,
                          CONSTRAINT SHIPPERS_PK PRIMARY KEY (SHIPPER_ID)
);

-- FOREIGN KEYS
-- REFERENCE: AUTHORS_BOOK_AUTHORS (TABLE: BOOK_AUTHORS)
ALTER TABLE BOOK_AUTHORS ADD CONSTRAINT AUTHORS_BOOK_AUTHORS
    FOREIGN KEY (AUTHOR_ID)
        REFERENCES AUTHORS (AUTHOR_ID);

-- REFERENCE: BOOK_DETAILS_BOOK_AUTHORS (TABLE: BOOK_AUTHORS)
ALTER TABLE BOOK_AUTHORS ADD CONSTRAINT BOOK_DETAILS_BOOK_AUTHORS
    FOREIGN KEY (BOOK_ID)
        REFERENCES BOOK_DETAILS (BOOK_ID);

-- REFERENCE: BOOK_DETAILS_PUBLISHERS (TABLE: BOOK_DETAILS)
ALTER TABLE BOOK_DETAILS ADD CONSTRAINT BOOK_DETAILS_PUBLISHERS
    FOREIGN KEY (PUBLISHER_ID)
        REFERENCES PUBLISHERS (PUBLISHER_ID);

ALTER TABLE BOOK_DETAILS ADD CONSTRAINT UNIQUE_ISBN UNIQUE (ISBN);

-- REFERENCE: MOVIE_DETAILS_PRODUCTS (TABLE: MOVIE_DETAILS)
ALTER TABLE MOVIE_DETAILS ADD CONSTRAINT MOVIE_DETAILS_PRODUCTS
    FOREIGN KEY (MOVIE_ID)
        REFERENCES PRODUCTS (PRODUCT_ID);

-- REFERENCE: ORDER_DETAILS_ORDERS (TABLE: ORDER_DETAILS)
ALTER TABLE ORDER_DETAILS ADD CONSTRAINT ORDER_DETAILS_ORDERS
    FOREIGN KEY (ORDER_ID)
        REFERENCES ORDERS (ORDER_ID);

-- REFERENCE: ORDERS_CUSTOMERS (TABLE: ORDERS)
ALTER TABLE ORDERS ADD CONSTRAINT ORDERS_CUSTOMERS
    FOREIGN KEY (CUSTOMER_ID)
        REFERENCES CUSTOMERS (CUSTOMER_ID);

-- REFERENCE: PAYMENTS_ORDERS (TABLE: PAYMENTS)
ALTER TABLE PAYMENTS ADD CONSTRAINT PAYMENTS_ORDERS
    FOREIGN KEY (ORDER_ID)
        REFERENCES ORDERS (ORDER_ID);

-- REFERENCE: PRODUCTS_ACCESSORIES_DETAILS (TABLE: ACCESSORIES_DETAILS)
ALTER TABLE ACCESSORIES_DETAILS ADD CONSTRAINT PRODUCTS_ACCESSORIES_DETAILS
    FOREIGN KEY (ACCESSORY_ID)
        REFERENCES PRODUCTS (PRODUCT_ID);

-- REFERENCE: PRODUCTS_BOOK_DETAILS (TABLE: BOOK_DETAILS)
ALTER TABLE BOOK_DETAILS ADD CONSTRAINT PRODUCTS_BOOK_DETAILS
    FOREIGN KEY (BOOK_ID)
        REFERENCES PRODUCTS (PRODUCT_ID);

-- REFERENCE: PRODUCTS_CATEGORIES (TABLE: PRODUCTS)
ALTER TABLE PRODUCTS ADD CONSTRAINT PRODUCTS_CATEGORIES
    FOREIGN KEY (CATEGORY_ID)
        REFERENCES CATEGORIES (CATEGORY_ID);

-- REFERENCE: PRODUCTS_GAME_DETAILS (TABLE: GAME_DETAILS)
ALTER TABLE GAME_DETAILS ADD CONSTRAINT PRODUCTS_GAME_DETAILS
    FOREIGN KEY (GAME_ID)
        REFERENCES PRODUCTS (PRODUCT_ID);

-- REFERENCE: PRODUCTS_ORDER_DETAILS (TABLE: ORDER_DETAILS)
ALTER TABLE ORDER_DETAILS ADD CONSTRAINT PRODUCTS_ORDER_DETAILS
    FOREIGN KEY (PRODUCT_ID)
        REFERENCES PRODUCTS (PRODUCT_ID);

-- REFERENCE: SHIPPERS_ORDERS (TABLE: ORDERS)
ALTER TABLE ORDERS ADD CONSTRAINT SHIPPERS_ORDERS
    FOREIGN KEY (SHIP_VIA)
        REFERENCES SHIPPERS (SHIPPER_ID);


--------------------------------------------



CREATE SEQUENCE PRODUCT_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE AUTHOR_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE CATEGORY_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE CUSTOMER_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE ORDER_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE PAYMENT_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE PUBLISHER_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE SHIPPER_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;


ALTER TABLE products
    ADD CONSTRAINT chk_price_positive CHECK (price > 0);
