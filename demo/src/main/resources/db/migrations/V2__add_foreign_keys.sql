alter table user_role 
add constraint user_role_userID foreign key (user_id) references users(id) 
on update cascade on delete restrict;

alter table user_role 
add constraint user_role_roleID foreign key (role_id) references roles(id)
on update cascade on delete restrict;

alter table students
add constraint students_userID foreign key (user_id) references users(id)
on update cascade on delete restrict;

alter table supervisors
add constraint supervisors_userID foreign key (user_id) references users(id)
on update cascade on delete restrict;

alter table internship_application
add constraint internship_app_students_id foreign key (student_id) references students(id)
on update cascade on delete restrict;

alter table internship_application 
add constraint internship_app_supervisors_id foreign key (supervisor_id) references supervisors(id)
on update cascade on delete restrict;

alter table internship_application 
add constraint internship_app_company_id foreign key (company_id) references company(id)
on update cascade on delete restrict;

alter table document
add constraint document_student_id foreign key (student_id) references students(id)
on update cascade on delete restrict;

alter table document 
add constraint document_internship_app_id foreign key (internship_app_id) references internship_application(id)
on update cascade on delete restrict;
alter table evaluations 
add constraint evalutions_supervisor_id foreign key(supervisor_id) references supervisors(id)
on update cascade on delete restrict;

alter table evaluations 
add constraint evalutions_internship_app_id foreign key(internship_app_id) references internship_application(id)
on update cascade on delete restrict;

