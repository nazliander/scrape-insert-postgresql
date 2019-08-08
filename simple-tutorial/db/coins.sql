CREATE SEQUENCE coin_id_seq START 1000;


CREATE TABLE IF NOT EXISTS coins
(
    id INT8 DEFAULT nextval('coin_id_seq') NOT NULL,
    code VARCHAR(255) NOT NULL,
    price DECIMAL(15, 2) NOT NULL,
    logTimestamp TIMESTAMP NOT NULL
);
