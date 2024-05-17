CREATE TABLE IF NOT EXISTS countries (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS performers (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL,
    country_id INTEGER REFERENCES countries(id) NOT NULL
    );

CREATE TABLE IF NOT EXISTS genres (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS records (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    publication_year INTEGER,
    description VARCHAR(512),
    track_list VARCHAR(1024),
    performer_id BIGINT REFERENCES performers(id) NOT NULL,
    genre_id BIGINT NOT NULL
    );

CREATE TABLE IF NOT EXISTS records_genres (
    record_id BIGINT REFERENCES records(id) NOT NULL,
    genre_id BIGINT REFERENCES genres(id) NOT NULL,
    PRIMARY KEY(record_id, genre_id)
    );

CREATE TABLE IF NOT EXISTS units (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    record_id BIGINT REFERENCES records(id) NOT NULL,
    price FLOAT NOT NULL,
    quantity INTEGER NOT NULL,
    added_on DATE NOT NULL
    );