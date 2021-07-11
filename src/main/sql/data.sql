insert into teacher(t_first_name, t_last_name)
values ('ИмяПрепод1', 'ФамилияПрепод1');
insert into teacher(t_first_name, t_last_name, t_experience)
values ('ИмяПрепод2', 'ФамилияПрепод2', 10);
insert into teacher(t_first_name, t_last_name)
values ('ИмяПрепод3', 'ФамилияПрепод3');
insert into teacher(t_first_name, t_last_name, t_experience)
values ('ИмяПрепод4', 'ФамилияПрепод4', 8);
insert into teacher(t_first_name, t_last_name)
values ('ИмяПрепод5', 'ФамилияПрепод5');

insert into student(s_first_name, s_last_name, s_group_number)
values ('ИмяСтудент1', 'ФамилияСтудент1', '11-001');
insert into student(s_first_name, s_last_name, s_group_number)
values ('ИмяСтудент2', 'ФамилияСтудент2', '11-001');
insert into student(s_first_name, s_last_name, s_group_number)
values ('ИмяСтудент3', 'ФамилияСтудент3', '11-002');
insert into student(s_first_name, s_last_name, s_group_number)
values ('ИмяСтудент4', 'ФамилияСтудент4', '11-002');
insert into student(s_first_name, s_last_name, s_group_number)
values ('ИмяСтудент5', 'ФамилияСтудент5', '11-003');

insert into course(c_name, c_date_start, c_date_end, c_teacher_id)
values ('Курс1', '2020-09-01', '2021-05-25', 1);
insert into course(c_name, c_date_start, c_date_end, c_teacher_id)
values ('Курс2', '2020-09-01', '2021-05-26', 1);
insert into course(c_name, c_date_start, c_date_end, c_teacher_id)
values ('Курс3', '2020-09-01', '2021-05-27', 2);
insert into course(c_name, c_date_start, c_date_end, c_teacher_id)
values ('Курс4', '2020-09-01', '2021-05-28', 3);
insert into course(c_name, c_date_start, c_date_end, c_teacher_id)
values ('Курс5', '2020-09-01', '2021-05-29', 5);

insert into lesson(l_name, l_date, l_course_id)
values ('Урок1', 'Вторник, 14:00', 4);
insert into lesson(l_name, l_date, l_course_id)
values ('Урок2', 'Вторник, 15:00', 2);
insert into lesson(l_name, l_date, l_course_id)
values ('Урок3', 'Понедельник, 15:00', 1);
insert into lesson(l_name, l_date, l_course_id)
values ('Урок4', 'Понедельник, 14:00', 5);
insert into lesson(l_name, l_date, l_course_id)
values ('Урок5', 'Понедельник, 13:00', 3);

insert into course_student(course_id, student_id)
values (1, 3);
insert into course_student(course_id, student_id)
values (1, 4);
insert into course_student(course_id, student_id)
values (1, 5);
insert into course_student(course_id, student_id)
values (2, 3);
insert into course_student(course_id, student_id)
values (2, 1);
insert into course_student(course_id, student_id)
values (3, 1);
insert into course_student(course_id, student_id)
values (3, 2);
insert into course_student(course_id, student_id)
values (3, 3);
insert into course_student(course_id, student_id)
values (3, 4);
insert into course_student(course_id, student_id)
values (4, 2);
insert into course_student(course_id, student_id)
values (5, 2);
insert into course_student(course_id, student_id)
values (5, 4);
insert into course_student(course_id, student_id)
values (5, 5);