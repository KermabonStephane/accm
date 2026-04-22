CREATE TABLE region (
    code INTEGER      PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE sub_region (
    code        INTEGER      PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    region_code INTEGER      NOT NULL REFERENCES region (code)
);
