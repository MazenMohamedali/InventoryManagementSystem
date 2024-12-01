CREATE TABLE client
(
      id INT PRIMARY KEY NOT NULL,
      name VARCHAR(50) NOT NULL,
      email VARCHAR(50) UNIQUE NOT NULL,
      address VARCHAR(100) NOT NULL,
      password VARCHAR(20) NOT NULL,
);


CREATE TABLE admin
(
      id INT PRIMARY KEY NOT NULL,
      name VARCHAR(50) NOT NULL,
      email VARCHAR(50) UNIQUE NOT NULL,
      password VARCHAR(20) NOT NULL,
      address VARCHAR(100) NOT NULL
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
      sup_id INT FOREIGN KEY REFERENCES supplier.id
);

CREATE TABLE client_phone_numbers
(
      id INT FOREIGN KEY REFERENCES client.id,
      phone_number VARCHAR(20) NOT NULL,
      PRIMARY KEY (id, phone_number)
);


CREATE TABLE admin_phone_numbers
(
      id INT FOREIGN KEY REFERENCES admin.id,
      phone_number VARCHAR(20) NOT NULL,
      PRIMARY KEY (id, phone_number)
);

CREATE TABLE supplier_phone_numbers
(
      id INT FOREIGN KEY REFERENCES supplier.id,
      phone_number VARCHAR(20) NOT NULL,
      PRIMARY KEY (id, phone_number)
);

CREATE TABLE orders
(
      id INT PRIMARY KEY NOT NULL,
      client_id INT FOREIGN KEY REFERENCES client.id,
      price DECIMAL(10,2),
      order_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      arrival_date DATE,

);

CREATE TABLE order_prod
(
      prod_id INT FOREIGN KEY REFERENCES product.id,
      order_id INT FOREIGN KEY REFERENCES orders.id,
      quantity INT NOT NULL,
      PRIMARY KEY (prod_id, order_id)
);

CREATE TABLE complaints
(
      complaint_id INT PRIMARY KEY NOT NULL,
      client_id INT FOREIGN KEY REFERENCES client.id,
      supplier_id INT FOREIGN KEY REFERENCES supplier.id,
      category VARCHAR (20),
      order_id INT FOREIGN KEY REFERENCES orders.id,
      complaint_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      details VARCHAR(300) NOT NULL,
);