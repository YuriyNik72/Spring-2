drop table if exists products cascade;

CREATE TABLE products
(
    id                      INT(15) NOT NULL  AUTO_INCREMENT,
    category_id             INT(15) NOT NULL,
    vendor_code             INT(15) NOT NULL,
    title                   VARCHAR(255) NOT NULL,
    short_description       VARCHAR(1500) NOT NULL,
    full_description        VARCHAR(2550) NOT NULL,
    price                   DOUBLE NOT NULL,
    create_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT FK_CATEGORY_ID FOREIGN KEY (category_id)
    REFERENCES categories (id)
)  DEFAULT charset=UTF8;

insert into products (category_id, vendor_code,title,short_description,full_description,price,
                      create_at,update_at)
    values (1,00000008, "Телевизор","'SAMSUNG'"," Цветной Диагональ 20'" , 5.00, '2022-05-19T10:15:30', '2022-05-19T10:20:30'),
           (1,00000088, "Телевизор","'SAMSUNG'"," Цветной Диагональ 40'" , 7.00, '2022-05-19T10:15:30', '2022-05-19T10:20:30'),
           (2,00000888, "Ноутбук","'HP'",  " Диагональ 15'" ,         4.00, '2022-05-19T10:15:30', '2022-05-19T10:20:30'),
           (3,00008888, "Смартфон","'SONY'","Диагональ 5'" , 3.00, '2022-05-19T10:15:30', '2022-05-19T10:20:30'),
           (3,00088888, "Смартфон","'Iphon'","Диагональ 7'" , 6.00, '2022-05-19T10:15:30', '2022-05-19T10:20:30');