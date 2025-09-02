DROP DATABASE driving_school;

CREATE DATABASE IF NOT EXISTS driving_school;

USE driving_school;

CREATE TABLE course
(
    course_id       VARCHAR(10) PRIMARY KEY,
    course_name     VARCHAR(200),
    duration        VARCHAR(100),
    fee             DECIMAL(10,2),
    description     VARCHAR(200),
    FOREIGN KEY (instructor_id) REFERENCES instructor(instructor_id)
);

CREATE TABLE instructor
(
    instructor_id   VARCHAR(10) PRIMARY KEY ,
    first_name      VARCHAR(200),
    last_name       VARCHAR(200),
    email           VARCHAR(100),
    phone           VARCHAR(20),
    specialization  VARCHAR(100),
    availability_schedule VARCHAR(100)
);

CREATE TABLE lessons
(
    lesson_id       VARCHAR(10) PRIMARY KEY,
    lesson_date     DATE NOT NULL ,
    start_time      TIME NOT NULL ,
    end_time        TIME NOT NULL ,
    status          VARCHAR(200),
    FOREIGN KEY (student_id) REFERENCES student (student_id),
    FOREIGN KEY (course_id)  REFERENCES course (course_id),
    FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id)
);

CREATE TABLE payment
(
    payment_id      VARCHAR(10) PRIMARY KEY ,
    payment_date    DATE NOT NULL ,
    amount          DECIMAL(10,2),
    payment_method  VARCHAR(100),
    status          VARCHAR(200),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);

CREATE TABLE student
(
    student_id        VARCHAR(10) PRIMARY KEY ,
    first_name        VARCHAR(200),
    last_name         VARCHAR(200),
    email             VARCHAR(100),
    phone             VARCHAR(20),
    address           VARCHAR(200),
    dob               DATE NOT NULL ,
    registration_date DATE NOT NULL
);

CREATE TABLE student_course
(
    student_course_id VARCHAR(10) PRIMARY KEY ,
    enrollment_date DATE NOT NULL ,
    status VARCHAR(200) ,
    grade VARCHAR(10),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (course_id)  REFERENCES course(course_id)
);

CREATE TABLE user
(
    user_id     VARCHAR(10) PRIMARY KEY ,
    user_name   VARCHAR(200),
    password    VARCHAR(200),
    role        VARCHAR(200),
    email       VARCHAR(100),
    status      VARCHAR(200)
);


