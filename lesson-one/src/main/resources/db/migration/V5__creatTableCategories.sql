drop table if exists categories cascade;

CREATE TABLE categories
(
    id                  INT(15) NOT NULL  AUTO_INCREMENT,
    title               VARCHAR(155) NOT NULL,
     description         VARCHAR(1555) NOT NULL,
                        PRIMARY KEY (id)
) AUTO_INCREMENT=1 DEFAULT charset=UTF8;

insert into categories (title, description)
        values ("Телевизоры","uyg"),("Ноутбуки","hg");