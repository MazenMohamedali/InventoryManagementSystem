CREATE TABLE client
(
      id INT PRIMARY KEY NOT NULL,
      name VARCHAR(50) NOT NULL,
      email VARCHAR(50) UNIQUE NOT NULL,
      address VARCHAR(100) NOT NULL,
      password VARCHAR(20) NOT NULL,
	balance DECIMAL(10,2)
);


CREATE TABLE supplier
(
      id INT PRIMARY KEY NOT NULL,
      name VARCHAR(50) NOT NULL,
      email VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE product
(
      id INT PRIMARY KEY NOT NULL,
      name VARCHAR(50) NOT NULL,
      price DECIMAL(10, 2) NOT NULL,
      quantity INT NOT NULL,
      category VARCHAR(50) NOT NULL,
      sup_id INT
);

SELECT * FROM product

INSERT INTO product (id, name, price, quantity, category, sup_id, expireDate, productionDate)
VALUES 
(10, 'Milk', 2.50, 100, 'Dairy', 101, '15-01-2025', '15-01-2024'),
(11, 'Cheese', 4.75, 50, 'Dairy', 102, '10-12-2024', '10-12-2023'),
(12, 'Bread', 1.20, 200, 'Bakery', 103, '25-02-2024', '25-02-2023'),
(13, 'Apple Juice', 3.00, 80, 'Beverages', 104, '01-03-2025', '01-03-2024'),
(14, 'Tomato Ketchup', 2.85, 40, 'Condiments', 105, '20-06-2025', '20-06-2023'),
(15, 'Butter', 5.10, 30, 'Dairy', 106, '05-11-2024', '05-11-2023'),
(16, 'Eggs', 3.50, 300, 'Poultry', 107, '01-01-2024', '01-01-2023'),
(17, 'Yogurt', 1.90, 120, 'Dairy', 108, '30-09-2024', '30-09-2023'),
(18, 'Pasta', 2.00, 60, 'Grains', 109, '31-08-2025', '31-08-2023'),
(19, 'Orange Juice', 3.25, 90, 'Beverages', 110, '15-07-2024', '15-07-2023');


INSERT INTO product (id, name, price, quantity, category, sup_id, expireDate, productionDate)
VALUES 
(20, 'Milk', 2.50, 100, 'Dairy', 201, '31-12-2024', '31-12-2023'), -- Current month
(21, 'Cheese', 4.75, 50, 'Dairy', 202, '20-12-2024', '20-12-2023'), -- Current month
(22, 'Bread', 1.20, 200, 'Bakery', 203, '15-12-2024', '15-12-2023'), -- Current month
(23, 'Apple Juice', 3.00, 80, 'Beverages', 204, '01-01-2025', '01-01-2024'), -- Next month
(24, 'Tomato Ketchup', 2.85, 40, 'Condiments', 205, '20-06-2025', '20-06-2023'),
(25, 'Butter', 5.10, 30, 'Dairy', 206, '31-12-2024', '31-12-2023'), -- Current month
(26, 'Eggs', 3.50, 300, 'Poultry', 207, '01-01-2024', '01-01-2023'), -- Next month
(27, 'Yogurt', 1.90, 120, 'Dairy', 208, '30-11-2024', '30-11-2023'), -- Previous month
(28, 'Pasta', 2.00, 60, 'Grains', 209, '31-12-2024', '31-12-2023'), -- Current month
(29, 'Orange Juice', 3.25, 90, 'Beverages', 210, '15-12-2024', '15-12-2023'); -- Current month
INSERT INTO product (id, name, price, quantity, category, sup_id, expireDate, productionDate)
VALUES 
(30, 'Bananas', 1.10, 8, 'Fruits', 211, '31-12-2024', '31-12-2023'), -- Current month, quantity < 10
(31, 'Rice', 2.50, 150, 'Grains', 212, '20-08-2025', '20-08-2023'),
(32, 'Chicken', 4.50, 5, 'Meat', 213, '10-12-2024', '10-12-2023'), -- Current month, quantity < 10
(33, 'Fish', 6.00, 70, 'Seafood', 214, '15-12-2024', '15-12-2023'), -- Current month
(34, 'Coffee', 3.75, 9, 'Beverages', 215, '31-01-2025', '31-01-2024'), -- Next month, quantity < 10
(35, 'Sugar', 1.50, 200, 'Grocery', 216, '31-12-2025', '31-12-2023'),
(36, 'Salt', 0.90, 8, 'Grocery', 217, '31-12-2024', '31-12-2023'), -- Current month, quantity < 10
(37, 'Tea', 2.25, 25, 'Beverages', 218, '31-12-2024', '31-12-2023'), -- Current month
(38, 'Oil', 6.50, 300, 'Grocery', 219, '31-07-2025', '31-07-2023'),
(39, 'Peanut Butter', 3.95, 7, 'Spreads', 220, '20-12-2024', '20-12-2023'), -- Current month, quantity < 10
(40, 'Jam', 2.50, 90, 'Spreads', 221, '31-12-2024', '31-12-2023'); -- Current month


ALTER TABLE product
      ADD COLUMN expireDate TEXT NOT NULL;
ALTER TABLE product
      ADD COLUMN productionDate TEXT NOT NULL;

ALTER TABLE purchasePrecent
ADD COLUMN purchasePrecent int NOT NULL;



-- CREATE TABLE prod_sup
-- (
--       supplier_id INT NOT NULL,
--       prod_id INT NOT NULL,
--       FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE CASCADE,
--       FOREIGN KEY (prod_id) REFERENCES product(id) ON DELETE CASCADE,
--       PRIMARY KEY (supplier_id, prod_id)
-- );

CREATE TABLE phone_numbers
(
      id INT NOT NULL, 
      phone_number VARCHAR(15) NOT NULL UNIQUE,
	PRIMARY KEY (id, phone_number)
);



CREATE TABLE orders
(
      id INT PRIMARY KEY NOT NULL,
      client_id INT,
      price DECIMAL(10,2),
      order_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      arrival_date DATE,
	FOREIGN KEY (id) REFERENCES client(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_prod
(
      prod_id INT, 
      order_id INT,
      quantity INT NOT NULL,
	FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (prod_id) REFERENCES product(id) ON DELETE CASCADE ON UPDATE CASCADE,
      PRIMARY KEY (prod_id, order_id)
);

CREATE TABLE complaints
(
      complaint_id INT PRIMARY KEY NOT NULL,
      client_id INT, 
      supplier_id INT, 
      category VARCHAR (20),
      order_id INT,
      details VARCHAR(300) NOT NULL,
      complaint_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE
);

SELECT * FROM product