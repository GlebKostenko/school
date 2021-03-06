create database sql_jdbc_school;
create user root with password 'root';
alter user root with superuser;
create table groups(
    group_id serial primary key,
    group_name varchar(255)
);
create table students(
    student_id serial primary key ,
    group_id integer references groups(group_id),
    first_name varchar(255),
    last_name varchar(255)
);
create table courses(
  course_id serial primary key,
  course_name varchar(255),
  course_description text
);
create table student_courses(
    id serial primary key,
    student_id integer references students(student_id),
    course_id integer references courses(course_id)
);
