create table users (
    id int auto_increment primary key,
    email varchar(128) unique not null,
    username varchar(64),
    password varchar(128) not null,
    enable boolean default true
);
