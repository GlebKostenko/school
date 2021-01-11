package com.foxminded.dao;

import com.foxminded.model.Student;
import com.foxminded.service.GroupsService;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Arrays;

public class DaoEmptyTest {
    private StudentDao studentDao = new StudentDao();
    private CoursesDao coursesDao = new CoursesDao();
    private GroupsDao groupsDao = new GroupsDao();
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
    void showAllStudents(){
        try {
            assertEquals(Arrays.asList(),studentDao.showAllStudents());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void showAllCourses(){
        assertEquals(Arrays.asList(),coursesDao.showAllCourses());
    }

    @Test
    void findStudentsRelatedToCourse(){
        assertEquals(Arrays.asList(),coursesDao.findStudentsRelatedToCourse(Mockito.anyString()));
    }

    @Test
    void searchGroupsWithLessOrEqualsStudentCount(){
        assertEquals(Arrays.asList(),groupsDao.searchGroupsWithLessOrEqualsStudentCount(Mockito.anyInt()));
    }
}
