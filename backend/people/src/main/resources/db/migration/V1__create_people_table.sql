CREATE TABLE people
(
    id         UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    firstname  VARCHAR(100) NOT NULL,
    lastname   VARCHAR(100) NOT NULL,
    nickname   VARCHAR(100),
    email      VARCHAR(255) NOT NULL UNIQUE,
    status     VARCHAR(20)  NOT NULL DEFAULT 'CREATED',
    role       VARCHAR(20)  NOT NULL DEFAULT 'USER',
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW()
);
