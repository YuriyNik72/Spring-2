drop table if exists delivery_addresses cascade;

CREATE TABLE delivery_addresses
(
    id           INT(15) NOT NULL  AUTO_INCREMENT,
    user_id      INT(15) NOT NULL,
    address      VARCHAR(355) NOT NULL,
                 PRIMARY KEY (id),
                 CONSTRAINT FK_USER_ID_DEL_ADR FOREIGN KEY (user_id)
                REFERENCES users (id)
) DEFAULT charset=UTF8;

INSERT INTO delivery_addresses (user_id, address)
    VALUES (1,"Rossia"), (2, "Moldova"), (3,"Kazah");