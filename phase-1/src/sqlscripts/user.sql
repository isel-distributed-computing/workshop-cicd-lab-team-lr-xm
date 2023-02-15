drop table if exists users;
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    salted_password VARCHAR(255)
);