ALTER TABLE comicBook
    ADD COLUMN issue_number  INTEGER,
    ADD COLUMN volume_number INTEGER;

CREATE UNIQUE INDEX uq_comicbook_series_issue_volume
    ON comicBook (series_id, issue_number, volume_number)
    WHERE series_id IS NOT NULL
      AND issue_number IS NOT NULL
      AND volume_number IS NOT NULL;
