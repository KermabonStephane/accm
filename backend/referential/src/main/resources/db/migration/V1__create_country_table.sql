CREATE TABLE country (
    id           UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(100) NOT NULL,
    alpha2       CHAR(2)      NOT NULL UNIQUE,
    alpha3       CHAR(3)      NOT NULL UNIQUE,
    country_code INTEGER      NOT NULL UNIQUE
);
