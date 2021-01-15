package com.foxminded.service;

import com.foxminded.dao.DataSource;
import com.foxminded.dao.StudentCoursesDao;
import com.foxminded.dao.StudentDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentCoursesServiceTest {
    StudentCoursesDao studentCoursesDao = mock(StudentCoursesDao.class);
    StudentCoursesService studentCoursesService = new StudentCoursesService(studentCoursesDao);
    @BeforeEach
    void createTables()throws Exception{
        String createGroupsTable= "create table groups(" +
                "    group_id serial primary key," +
                "    group_name varchar(255)" +
                ");";
        String createStudentsTable = "create table students(" +
                "    student_id serial primary key ," +
                "    group_id integer references groups(group_id)," +
                "    first_name varchar(255)," +
                "    last_name varchar(255)" +
                ");";
        String createCoursesTable = "create table courses(" +
                "  course_id serial primary key," +
                "  course_name varchar(255)," +
                "  course_description text" +
                ");";
        String createStudentCoursesTable = "create table student_courses(" +
                "    id serial primary key," +
                "    student_id integer references students(student_id)," +
                "    course_id integer references courses(course_id)" +
                ");";
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute(createGroupsTable);
        statement.execute(createStudentsTable);
        statement.execute(createCoursesTable);
        statement.execute(createStudentCoursesTable);
    }
    @AfterEach
    void deleteTables()throws Exception{
        String dropStudentCoursesTable = "DROP TABLE student_courses;";
        String dropStudentsTable = "DROP TABLE students;";
        String dropGroupsTable = "DROP TABLE groups;";
        String dropCoursesTable = "DROP TABLE courses;";
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute(dropStudentCoursesTable);
        statement.execute(dropStudentsTable);
        statement.execute(dropGroupsTable);
        statement.execute(dropCoursesTable);
    }
    @Test
    void saveCoursesTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws IOException, URISyntaxException,SQLException {
        doNothing().when(studentCoursesDao).saveStudentCoursesTable(any());
        studentCoursesService.saveStudentCoursesTable();
        verify(studentCoursesDao,times(1)).saveStudentCoursesTable(any());
    }

}