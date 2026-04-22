ALTER TABLE comicBook
    DROP COLUMN isbn;

CREATE TABLE edition
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comic_book_id UUID        NOT NULL REFERENCES comicBook (id) ON DELETE CASCADE,
    editor_id     UUID REFERENCES editor (id) ON DELETE SET NULL,
    isbn          VARCHAR(20),
    date          DATE
);
