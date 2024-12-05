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

CREATE TABLE prod_sup
(
      supplier_id INT NOT NULL,
      prod_id INT NOT NULL,
      FOREIGN KEY (supplier_id) REFERENCES supplier(id) ON DELETE CASCADE,
      FOREIGN KEY (prod_id) REFERENCES product(id) ON DELETE CASCADE,
      PRIMARY KEY (supplier_id, prod_id)
);

CREATE TABLE phone_numbers
(
      id INT NOT NULL, 
      phone_number VARCHAR(15) NOT NULL UNIQUE,
	PRIMARY KEY (id, phone_number)
      --FOREIGN KEY (id) REFERENCES client(id) ON DELETE CASCADE,
      --FOREIGN KEY (id) REFERENCES supplier(id) ON DELETE CASCADE
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