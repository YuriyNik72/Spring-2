drop table if exists orders_item cascade;

CREATE TABLE orders_item
(
    id              INT(15) NOT NULL  AUTO_INCREMENT,
    product_id      INT(15) NOT NULL,
    order_id        INT(15) NOT NULL,
    quantity        INT(155) NOT NULL,
    item_price      DOUBLE NOT NULL,
    total_price     DOUBLE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_ORDER_ID FOREIGN KEY (order_id)
    REFERENCES orders (id),
    CONSTRAINT FK_PRODUCT_ID_ORD_IT FOREIGN KEY (product_id)
    REFERENCES products (id)
)AUTO_INCREMENT=1 DEFAULT charset=UTF8;