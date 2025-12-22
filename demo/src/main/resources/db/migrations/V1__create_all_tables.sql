create table users (
    id int auto_increment primary key,
    email varchar(128) unique not null,
    username varchar(64),
    password varchar(128) not null,
    enable boolean default true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table roles(
    id int auto_increment primary key,
    role_name varchar(64) unique not null
);

create table user_role(
    user_id int, 
    role_id int,
    primary key (user_id, role_id)
);

create table students(
    id int auto_increment primary key,
    full_name varchar(64) not null,
    phone_number int unique,
    email varchar(128) unique,
    year int,
    major varchar(64),
    user_id int
);

create table supervisors(
    id int auto_increment primary key,
    user_id int,
    full_name varchar(64) not null,
    email varchar(128) not null,
    phone_number int,
    department varchar(128)
);

create table internship_application(
    id int auto_increment primary key,
    student_id int,
    supervisor_id int,
    company_id int,
    title varchar(255) not null,
    description varchar(255),
    status enum('Pending', 'Approved', 'Rejected') default 'Pending' not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE company (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(100),
    phone_number int
);

CREATE TABLE document (
    id int PRIMARY KEY AUTO_INCREMENT,
    student_id int,
    internship_app_id int,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table evaluations(
    id int primary key auto_increment,
    internship_app_id int,
    supervisor_id int,
    comment varchar(255),
    score int,
    evalutions_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);