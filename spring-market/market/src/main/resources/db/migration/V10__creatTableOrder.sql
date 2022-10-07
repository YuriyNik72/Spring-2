drop table if exists orders cascade;

CREATE TABLE orders
(
    id                      INT(15) NOT NULL  AUTO_INCREMENT,
    user_id                 INT(15) NOT NULL,
    price                   DOUBLE NOT NULL,
    delivery_price          DOUBLE NOT NULL,
    delivery_address_id     INT(15) NOT NULL,
    phone_number            VARCHAR(15) NOT NULL,
    status_id               INT(15) NOT NULL,
    delivery_date           TIMESTAMP NOT NULL,
    create_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (id),
                            CONSTRAINT FK_USER_ID FOREIGN KEY (user_id)
                            REFERENCES users(id),
                            CONSTRAINT FK_STATUS_ID FOREIGN KEY (status_id)
                            REFERENCES orders_statuses (id),
                            CONSTRAINT FK_DELIVERY_ADDRESS_ID FOREIGN KEY (delivery_address_id)
                            REFERENCES delivery_addresses(id)
)AUTO_INCREMENT=1 DEFAULT charset=UTF8;