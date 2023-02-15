DROP TABLE if exists to_dos;
CREATE TABLE to_dos (
   id SERIAL PRIMARY KEY,
   description VARCHAR(255),
   user_id bigint,
   FOREIGN KEY (user_id) REFERENCES users (id)
);
