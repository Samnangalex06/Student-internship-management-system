create table supervisors (
    id serial primary key,
    name varchar(100) not null,
    department varchar(100) not null,
    created_at timestamp default current_timestamp
);