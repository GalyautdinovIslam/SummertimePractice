create table teacher
(
    t_id         serial primary key,
    t_first_name varchar(30)                                            not null,
    t_last_name  varchar(30)                                            not null,
    t_experience integer check ( t_experience >= 0 and t_experience <= 100) not null default 0
);

create table student
(
    s_id           serial primary key,
    s_first_name   varchar(30) not null,
    s_last_name    varchar(30) not null,
    s_group_number varchar(10) not null
);

create table course
(
    c_id         serial primary key,
    c_name       varchar(30) not null,
    c_date_start varchar(30) not null,
    c_date_end   varchar(30) not null check ( c_date_end > course.c_date_start ),
    c_teacher_id integer,
    foreign key (c_teacher_id) references teacher (t_id)
);

create table lesson
(
    l_id        serial primary key,
    l_name      varchar(30) not null,
    l_date      varchar(30) not null,
    l_course_id integer,
    foreign key (l_course_id) references course (c_id)
);

create table course_student
(
    course_id  integer,
    student_id integer,
    foreign key (course_id) references course (c_id),
    foreign key (student_id) references student (s_id)
);
