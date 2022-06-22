drop table if exists users_roles cascade;

CREATE TABLE users_roles
(
    user_id   INT(15) NOT NULL,
    role_id   INT(15) NOT NULL,
              PRIMARY KEY (user_id, role_id),
              CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
              REFERENCES users (id)
            ON DELETE NO ACTION ON UPDATE NO ACTION ,
            CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
              REFERENCES roles (id)
            ON DELETE NO ACTION ON UPDATE NO ACTION
) DEFAULT charset=UTF8;

insert into users_roles (user_id, role_id)
values (1,2),(2,3), (3,1), (3,3);