drop table if exists roles cascade;

CREATE TABLE roles
(
    id        INT(15) NOT NULL  AUTO_INCREMENT,
    name      VARCHAR(155) NOT NULL,
              PRIMARY KEY (id)
)AUTO_INCREMENT=1 DEFAULT charset=UTF8;

insert into roles (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_EMPLOYEE'),
       ('ROLE_MANAGER');