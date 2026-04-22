CREATE TABLE editor
(
    id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name   VARCHAR(200) NOT NULL,
    status VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE'
);

ALTER TABLE comicBook
    ADD COLUMN editor_id UUID REFERENCES editor (id) ON DELETE SET NULL;
