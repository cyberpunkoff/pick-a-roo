--liquibase formatted sql

--changeset vasilii:1
CREATE TABLE _user
(
    id        bigint primary key,
    firstname varchar not null,
    surname   varchar
);

--changeset vasilii:2
CREATE TABLE poll
(
    id       serial primary key,
    state    varchar not null,
    title    varchar not null unique,
    owner_id bigint  not null REFERENCES _user (id) ON UPDATE CASCADE
);

--changeset vasilii:3
CREATE TABLE option
(
    id      serial primary key,
    value   varchar not null,
    poll_id integer not null REFERENCES poll (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

--changeset vasilii:4
CREATE TABLE vote
(
    id        serial primary key,
    owner_id  bigint references _user (id),
    option_id integer not null REFERENCES option (id) ON UPDATE CASCADE
);
