DROP TABLE books IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE loans IF EXISTS;

CREATE TABLE books (
  id         INTEGER IDENTITY PRIMARY KEY,
  title VARCHAR(200),
  author VARCHAR(100),
  isbn VARCHAR(13)

);
CREATE INDEX books_title ON books (title);

CREATE TABLE users (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(50),
  last_name  VARCHAR_IGNORECASE(50),
  telephone  VARCHAR(20),
  email      VARCHAR(100)
);
CREATE INDEX users_last_name ON users (last_name);

CREATE TABLE loans (
  id          INTEGER IDENTITY PRIMARY KEY,
  due_date    DATE,
  user_id     INTEGER NOT NULL,
  book_id     INTEGER NOT NULL
);
ALTER TABLE loans ADD CONSTRAINT fk_loans_users FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE loans ADD CONSTRAINT fk_loans_books FOREIGN KEY (book_id) REFERENCES books (id);
CREATE INDEX loans_user ON loans (user_id);
