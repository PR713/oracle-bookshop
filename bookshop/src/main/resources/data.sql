-- -- Categories
-- INSERT INTO Categories (CATEGORY_ID, CATEGORY_NAME) VALUES (1, 'Books');
-- INSERT INTO Categories (CATEGORY_ID, CATEGORY_NAME) VALUES (2, 'Movies');
-- INSERT INTO Categories (CATEGORY_ID, CATEGORY_NAME) VALUES (3, 'Accessories');
-- INSERT INTO Categories (CATEGORY_ID, CATEGORY_NAME) VALUES (4, 'Games');

-- Authors
INSERT INTO Authors (AUTHOR_ID, FIRST_NAME, LAST_NAME, BIOGRAPHY) VALUES (1, 'George', 'Orwell', 'British author known for dystopian fiction');
INSERT INTO Authors (AUTHOR_ID, FIRST_NAME, LAST_NAME, BIOGRAPHY) VALUES (2, 'Jane', 'Austen', 'English novelist of romantic fiction');
INSERT INTO Authors (AUTHOR_ID, FIRST_NAME, LAST_NAME, BIOGRAPHY) VALUES (3, 'Stephen', 'King', 'American author of horror and supernatural fiction');

-- Publishers
INSERT INTO Publishers (PUBLISHER_ID, PUBLISHER_NAME, COUNTRY) VALUES (1, 'Penguin Random House', 'United States');
INSERT INTO Publishers (PUBLISHER_ID, PUBLISHER_NAME, COUNTRY) VALUES (2, 'HarperCollins', 'United Kingdom');
INSERT INTO Publishers (PUBLISHER_ID, PUBLISHER_NAME, COUNTRY) VALUES (3, 'Simon & Schuster', 'United States');

-- Shippers
INSERT INTO Shippers (SHIPPER_ID, COMPANY_NAME, PHONE) VALUES (1, 'DHL Express', 48123456789);
INSERT INTO Shippers (SHIPPER_ID, COMPANY_NAME, PHONE) VALUES (2, 'UPS Poland', 48987654321);
INSERT INTO Shippers (SHIPPER_ID, COMPANY_NAME, PHONE) VALUES (3, 'FedEx International', 48555123456);
