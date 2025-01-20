CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);

INSERT INTO courses (name) VALUES ('Curso Backend Especialidad Spring Boot'), ('Curso de Frontend Especialidad React');