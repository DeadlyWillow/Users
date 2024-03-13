-- liquibase formatted sql
-- changeset set:1

CREATE TABLE users
(
    id                  BIGSERIAL PRIMARY KEY,
    first_name          TEXT NOT NULL,
    last_name           TEXT NOT NULL,
    middle_name         TEXT,
    email               TEXT UNIQUE,
    date_of_birth       TIMESTAMP,
    password            TEXT NOT NULL
);