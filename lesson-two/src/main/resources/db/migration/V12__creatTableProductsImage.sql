drop table if exists products_images cascade;

CREATE TABLE products_images
(
    id        INT(15) NOT NULL  AUTO_INCREMENT,
    name      VARCHAR(155) NOT NULL,
    PRIMARY KEY (id)
)AUTO_INCREMENT=1 DEFAULT charset=UTF8;