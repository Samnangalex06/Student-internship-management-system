create table user_role(
    user_id int not null, 
    role_id int not null,
    primary key (user_id, role_id),
    constraint user_role_userID foreign key (user_id) references users(id),
    constraint user_role_roleID foreign key (role_id) references roles(id)
);