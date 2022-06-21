drop table if exists orders_statuses cascade;

CREATE TABLE orders_statuses
(
    id        INT(15) NOT NULL  AUTO_INCREMENT,
    title     VARCHAR(155) NOT NULL,
              PRIMARY KEY (id)
) DEFAULT charset=UTF8;

INSERT INTO orders_statuses (title)
    VALUES ("Сформирован");