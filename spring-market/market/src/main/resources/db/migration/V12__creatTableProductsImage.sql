drop table if exists products_images cascade;

CREATE TABLE products_images
(
    id             INT(15) NOT NULL  AUTO_INCREMENT,
    product_id     INT(15) NOT NULL,
    path    VARCHAR(155) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_PRODUCT_ID_IMG FOREIGN KEY (product_id)
    REFERENCES products (id)
)AUTO_INCREMENT=1 DEFAULT charset=UTF8;

INSERT INTO products_images (product_id, path)
VALUES
    (1, "2.jpg");