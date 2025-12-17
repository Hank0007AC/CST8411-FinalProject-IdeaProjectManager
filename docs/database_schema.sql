-- Optional DB schema (MySQL) if you decide to replace JSON with JDBC later.
CREATE TABLE users (
  username VARCHAR(20) PRIMARY KEY,
  password_hash VARCHAR(255) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ideas (
  id VARCHAR(50) PRIMARY KEY,
  username VARCHAR(20) NOT NULL,
  title VARCHAR(200) NOT NULL,
  category VARCHAR(100) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE tasks (
  id VARCHAR(80) PRIMARY KEY,
  idea_id VARCHAR(50) NOT NULL,
  title VARCHAR(300) NOT NULL,
  done BOOLEAN NOT NULL DEFAULT FALSE,
  FOREIGN KEY (idea_id) REFERENCES ideas(id)
);
