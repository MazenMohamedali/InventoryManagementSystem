-- SQLite
INSERT INTO client (id, name, email, address, password, balance) VALUES
(1, 'John Doe', 'john.doe@example1.com', '123 Elm Street, Cityville', 'password123', 500.00),
(2, 'Jane Smith', 'jane.smith@example2.com', '456 Oak Avenue, Townsville', 'securepass456', 1200.50),
(3, 'Michael Brown', 'michael.brown@exa3mple.com', '789 Pine Road, Villagetown', 'mypassword789', 750.25);
Select * from client;
Select * from supplier;
Select * from orders;
Select * from product;

-- Insert data into `supplier` table
INSERT INTO supplier (id, name, email) VALUES
(7000, 'Fresh Produce Co.', 'info@freshproduce.com'),
(7001, 'Tech Supplies Ltd.', 'support@techsupplies.com'),
(7002, 'Clothing Mart', 'contact@clothingmart.com');

INSERT INTO phone_numbers (id, phone_number) VALUES
(1, '123-456-7890'),
(2, '987-654-3210'),
(3, '555-555-5555');

INSERT INTO orders (id, client_id, price, arrival_date) VALUES
(1, 1, 50.00, '20-12-2024'),
(2, 2, 1200.00, '15-12-2024'),
(3, 3, 45.75, '18-12-2024');

SELECT id
FROM orders
WHERE MONTH(STR_TO_DATE(arrival_date, '%d/%m/%Y')) = 12;

-- Insert data into `order_prod` table
INSERT INTO order_prod (prod_id, order_id, quantity) VALUES
(1, 1, 20),
(2, 2, 1),
(3, 3, 3);

-- Insert data into `complaints` table
INSERT INTO complaints (complaint_id, client_id, supplier_id, category, order_id, details) VALUES
(1, 1, 7000, 'Product Quality', 1, 'Milk was spoiled upon arrival.'),
(2, 2, 7001, 'Late Delivery', 2, 'Laptop was delivered 3 days late.'),
(3, 3, 7002, 'Wrong Item', 3, 'Received the wrong T-Shirt size.');