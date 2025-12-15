create table students(
    id int auto_increment primary key,
    full_name varchar(64) not null,
    phone_number int unique,
    email varchar(128) unique,
    year int,
    major varchar(64)
    user_id int,
    constraint students_userID foreign key (user_id) references user(id)
);