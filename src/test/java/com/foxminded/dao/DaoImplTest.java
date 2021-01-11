package com.foxminded.dao;

import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentCoursesService;
import com.foxminded.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
class DaoImplTest {
    private StudentDao studentDao = new StudentDao();


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
    void addNewStudent() throws SQLException {
        studentDao.addNewStudent("Пётр","Капица");
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM students " +
                "WHERE first_name = 'Пётр' AND last_name = 'Капица'");
        resultSet.next();
        assertEquals(1,resultSet.getInt(1));
    }

    @Test
    void saveStudentsTable(){
        try {
            DataSource dataSource = new DataSource();
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet  resultSet = statement.executeQuery("SELECT COUNT(student_id) FROM students");
            resultSet.next();
            assertEquals(true,resultSet.getInt(1) > 100);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void saveGroupsTable(){
        try {
            DataSource dataSource = new DataSource();
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet  resultSet = statement.executeQuery("SELECT COUNT(group_id) FROM groups ");
            resultSet.next();
            assertEquals(true,resultSet.getInt(1) > 5);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void saveCoursesTable(){
        try {
            DataSource dataSource = new DataSource();
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet  resultSet = statement.executeQuery("SELECT COUNT(course_id) FROM courses ");
            resultSet.next();
            assertEquals(true,resultSet.getInt(1) > 5);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void saveStudentCoursesTable(){
        try {
            DataSource dataSource = new DataSource();
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet  resultSet = statement.executeQuery("SELECT COUNT(student_id) FROM student_courses ");
            resultSet.next();
            assertEquals(true,resultSet.getInt(1) > 100);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void addStudentToCourse() throws SQLException{
        studentDao.addStudentToCourse(1,1);
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM student_courses " +
                "WHERE student_id=1 AND course_id = 1");
        resultSet.next();
        assertEquals(true,resultSet.getInt(1) > 0);
    }

    @Test
    void deleteStudentById() throws SQLException{
        studentDao.deleteStudentById(1);
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM students " +
                "WHERE student_id=1");
        resultSet.next();
        assertEquals(0,resultSet.getInt(1));
    }

    @Test
    void removeStudentFromCourse() throws Exception{
        studentDao = mock(StudentDao.class);
        doNothing().when(studentDao).removeStudentFromCourse(1,1);
        studentDao.removeStudentFromCourse(1,1);
        verify(studentDao,times(1)).removeStudentFromCourse(1,1);
    }

}