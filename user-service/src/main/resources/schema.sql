CREATE TABLE IF NOT EXISTS users
(
    id
    BIGINT
    GENERATED
    BY
    DEFAULT AS
    IDENTITY
    PRIMARY
    KEY,
    created
    TIMESTAMP
    NOT
    NULL,
    customer
    BIGINT
    NOT
    NULL,
    status
    VARCHAR
(
    64
) NOT NULL
    );