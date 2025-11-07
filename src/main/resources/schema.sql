DROP TABLE IF EXISTS ANSWERS;
DROP TABLE IF EXISTS QUESTIONS;
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
    userId          VARCHAR(12)     NOT NULL,
    password        VARCHAR(12)     NOT NULL,
    name            VARCHAR(20)     NOT NULL,
    email           VARCHAR(50),
    PRIMARY KEY (userId)
);

CREATE TABLE QUESTIONS (
    questionId      BIGINT AUTO_INCREMENT,
    writer          VARCHAR(30)     NOT NULL,
    title           VARCHAR(50)     NOT NULL,
    contents        VARCHAR(5000)   NOT NULL,
    createdDate     TIMESTAMP       NOT NULL,
    countOfAnswer   INT,
    PRIMARY KEY (questionId)
);

CREATE TABLE ANSWERS (
    answerId        BIGINT AUTO_INCREMENT,
    writer          VARCHAR(30)     NOT NULL,
    contents        VARCHAR(5000)   NOT NULL,
    createdDate     TIMESTAMP       NOT NULL,
    questionId      BIGINT          NOT NULL,
    PRIMARY KEY (answerId)
);
