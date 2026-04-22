ALTER TABLE country
    ADD COLUMN region_code     INTEGER REFERENCES region (code),
    ADD COLUMN sub_region_code INTEGER REFERENCES sub_region (code);
