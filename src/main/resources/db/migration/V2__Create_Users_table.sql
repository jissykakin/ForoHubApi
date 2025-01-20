CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    avatar VARCHAR(255),
    status TINYINT NOT NULL,
    profession VARCHAR(100),
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);