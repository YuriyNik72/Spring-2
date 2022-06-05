drop table if exists users cascade;

CREATE TABLE users
(
    id                      INT(15) NOT NULL  AUTO_INCREMENT,
    username                VARCHAR(155) NOT NULL,
    password                CHAR(255) NOT NULL,
    first_name              VARCHAR(155) NOT NULL,
    last_name               VARCHAR(155) NOT NULL,
    email                   VARCHAR(155) NOT NULL,
        PRIMARY KEY (id)
) AUTO_INCREMENT=1 DEFAULT charset=UTF8;

insert into USERS (username, password, first_name, last_name, email)
values ('user', '$2a$12$oC2toc9T1pnVsUi4qpoNFuD3etJuykNnXt1XJ8IywDs9RUqgg/CSS', 'Иван', 'Петров', 'user@mail.ru'),
       ('manager', '$2a$12$0WOquyNDtpbbu2QGhmQmqeG64XqoEAV1CbiQTJ2HM1/nxN9Bg921u', 'Сергей', 'Иванов', 'manager@mail.ru'),
       ('admin', '$2a$12$/y5UWab5WZIk79cm3/z/veCtEcBaKtTTwJ4Clum5Rve3Q1F3b3F2u', 'Владимир', 'Николаев', 'admin@mail.ru');

