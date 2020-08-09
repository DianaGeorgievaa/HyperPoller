CREATE SCHEMA IF NOT EXISTS hyperpoller;

CREATE TABLE IF NOT EXISTS hyperpoller.company(
	id int auto_increment,
    address varchar(255),
    name varchar(255),
    uid varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS hyperpoller.store(
	id int auto_increment,
    address varchar(255),
    name varchar(255),
    company_id int,
    PRIMARY KEY (id),
    FOREIGN KEY(company_id) REFERENCES hyperpoller.company(id)
);

CREATE TABLE IF NOT EXISTS hyperpoller.customer(
	id int auto_increment,
    address varchar(255),
    name varchar(255),
    uid varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS hyperpoller.carddetails(
	id int auto_increment,
    card_number varchar(255),
    card_type varchar(255),
    is_contactless boolean,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS hyperpoller.invoice(
	id int auto_increment,
    payment_type varchar(255),
    invoice_date varchar(255),
    total_sum decimal,
    carddetails_id int,
    customer_id int,
    store_id int,
    PRIMARY KEY(id),
    FOREIGN KEY (customer_id) REFERENCES hyperpoller.customer(id),
    FOREIGN KEY (carddetails_id) REFERENCES hyperpoller.carddetails(id),
    FOREIGN KEY (store_id) REFERENCES hyperpoller.store(id)
);

CREATE TABLE IF NOT EXISTS hyperpoller.receipt(
	id int auto_increment,
    payment_type varchar(255),
    receipt_date varchar(255),
    total_sum decimal,
    carddetails_id int,
    store_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (carddetails_id) REFERENCES hyperpoller.carddetails(id),
    FOREIGN KEY (store_id) REFERENCES hyperpoller.store(id)
);
