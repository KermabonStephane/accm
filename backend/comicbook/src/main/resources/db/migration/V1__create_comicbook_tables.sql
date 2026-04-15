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

CREATE TABLE author
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    firstname  VARCHAR(100) NOT NULL,
    lastname   VARCHAR(100) NOT NULL,
    middlename VARCHAR(100)
);

CREATE TABLE comicbook_author
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comicbook_id UUID        NOT NULL REFERENCES comicbook (id) ON DELETE CASCADE,
    author_id    UUID        NOT NULL REFERENCES author (id) ON DELETE CASCADE,
    role         VARCHAR(20) NOT NULL,
    UNIQUE (comicbook_id, author_id, role)
);
