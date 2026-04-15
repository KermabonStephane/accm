CREATE TABLE comicbook
(
    id         UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title      VARCHAR(500) NOT NULL,
    isbn       VARCHAR(20),
    date       DATE,
    status     VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE comicbook_author
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comicbook_id UUID         NOT NULL REFERENCES comicbook (id) ON DELETE CASCADE,
    name         VARCHAR(255) NOT NULL,
    role         VARCHAR(20)  NOT NULL
);
