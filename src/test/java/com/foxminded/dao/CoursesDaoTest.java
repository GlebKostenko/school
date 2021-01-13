package com.foxminded.dao;

import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentCoursesService;
import com.foxminded.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CoursesDaoTest {
    private CoursesDao coursesDao = new CoursesDao();
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
        GroupsDao groupsDao = new GroupsDao();
        GroupsService groupsService = new GroupsService(groupsDao);
        groupsService.saveGroupsTable();
        StudentDao studentDao = new StudentDao();
        StudentService studentService = new StudentService(studentDao);
        studentService.saveStudentsTable();
        CoursesDao coursesDao = new CoursesDao();
        CoursesService coursesService = new CoursesService(coursesDao);
        coursesService.saveCoursesTable();
        StudentCoursesDao studentCoursesDao = new StudentCoursesDao();
        StudentCoursesService studentCoursesService = new StudentCoursesService(studentCoursesDao);
        studentCoursesService.saveStudentCoursesTable();
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
    void saveCoursesTable_WhenEverythingGoesRight_thenShouldBeDataInATable() {
        try {
            DataSource dataSource = new DataSource();
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(course_id) FROM courses ");
            resultSet.next();
            assertEquals(true,resultSet.getInt(1) > 5);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void showAllCourses_WhenTablesAreFilled_thenShouldBeNotEmptyResultList() {
        assertEquals(true,!coursesDao.showAllCourses().isEmpty());
    }

    @Test
    void findStudentsRelatedToCourse_WhenTablesAreFilled_thenShouldBeNotEmptyResultList() {
        assertEquals(true,!coursesDao.findStudentsRelatedToCourse("Math").isEmpty());
    }
    @Test
    void showAllCourses_WhenTableAreEmpty_thenShouldBeEmptyResultList() throws SQLException{
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DELETE FROM student_courses");
        statement.execute("DELETE FROM courses");
        assertEquals(Arrays.asList(),coursesDao.showAllCourses());
    }

    @Test
    void findStudentsRelatedToCourse_WhenTableAreEmpty_thenShouldBeEmptyResultList() throws SQLException{
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DELETE FROM student_courses");
        assertEquals(Arrays.asList(),coursesDao.findStudentsRelatedToCourse(Mockito.anyString()));
    }
}