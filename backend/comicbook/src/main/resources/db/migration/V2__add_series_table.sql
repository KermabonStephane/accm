CREATE TABLE series
(
    id        UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    name      VARCHAR(500) NOT NULL,
    status    VARCHAR(20)  NOT NULL DEFAULT 'ONGOING',
    parent_id UUID REFERENCES series (id) ON DELETE SET NULL
);

ALTER TABLE comicBook
    ADD COLUMN series_id UUID REFERENCES series (id) ON DELETE SET NULL;
