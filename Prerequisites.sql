CREATE DATABASE atm_machine;

USE atm_machine;

CREATE TABLE IF NOT EXISTS user(
	user_id INT NOT NULL AUTO_INCREMENT,
	account_number VARCHAR(255),
	title VARCHAR(255),
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	address  VARCHAR(255),
	email  VARCHAR(255),
	dob  VARCHAR(255),
	gender  VARCHAR(255),
	marriagestatus  VARCHAR(255),
	password  VARCHAR(255),
	photo BLOB,
	atm_service Boolean,
	balance INT,
    PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS atm_users(
	user_id TEXT NOT NULL,
    atm_number VARCHAR(255),
    invalid_tries INT,
    pin TEXT
);

CREATE TABLE IF NOT EXISTS transactions(
	transaction_id TEXT NOT NULL,
	user_id TEXT NOT NULL,
	user_name VARCHAR(255),
	debit_credit VARCHAR(10),
	date_performed VARCHAR(255),
	amount VARCHAR(255),
	remarks VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS invalid_tries(
	atm_number VARCHAR(255),
    invalid INT
);