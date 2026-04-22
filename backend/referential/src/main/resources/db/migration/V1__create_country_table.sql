CREATE TABLE country (
    country_code INTEGER      PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    alpha2       CHAR(2)      NOT NULL UNIQUE,
    alpha3       CHAR(3)      NOT NULL UNIQUE
);
