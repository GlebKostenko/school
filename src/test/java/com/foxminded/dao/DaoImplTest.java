package com.foxminded.dao;

import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
class DaoImplTest {
    private StudentDao studentDao = new StudentDao();


    @BeforeEach
    void createTables()throws Exception{
        String query= "create table groups(" +
                "    group_id serial primary key," +
                "    group_name varchar(255)" +
                ");";
        String query1 = "create table students(" +
                "    student_id serial primary key ," +
                "    group_id integer references groups(group_id)," +
                "    first_name varchar(255)," +
                "    last_name varchar(255)" +
                ");";
        String query2 = "create table courses(" +
                "  course_id serial primary key," +
                "  course_name varchar(255)," +
                "  course_description text" +
                ");";
        String query3 = "create table student_courses(" +
                "    id serial primary key," +
                "    student_id integer references students(student_id)," +
                "    course_id integer references courses(course_id)" +
                ");";
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute(query);
        statement.execute(query1);
        statement.execute(query2);
        statement.execute(query3);
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
        studentCoursesDao.saveStudentCoursesTable();
    }
    @AfterEach
    void deleteTables()throws Exception{
        String query= "DROP TABLE student_courses;";
        String query1 = "DROP TABLE students;";
        String query2 = "DROP TABLE Test.PUBLIC.groups;";
        String query3 = "DROP TABLE courses;";
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute(query);
        statement.execute(query1);
        statement.execute(query2);
        statement.execute(query3);
    }
    @Test
    void addNewStudent() throws SQLException {
        studentDao.addNewStudent("Пётр","Капица");
        String query = "SELECT COUNT(1) FROM students " +
                "WHERE first_name = 'Пётр' AND last_name = 'Капица'";
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        assertEquals(1,resultSet.getInt(1));
    }

    @Test
    void addStudentToCourse() throws SQLException{
        studentDao.addStudentToCourse(1,1);
        String query = "SELECT COUNT(1) FROM student_courses " +
                "WHERE student_id=1 AND course_id = 1";
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        assertEquals(true,resultSet.getInt(1) > 0);
    }

    @Test
    void deleteStudentById() throws SQLException{
        studentDao.deleteStudentById(1);
        String query = "SELECT COUNT(1) FROM students " +
                "WHERE student_id=1";
        String query1 = "SELECT * FROM students";
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        assertEquals(0,resultSet.getInt(1));
    }

    @Test
    void removeStudentFromCourse() throws Exception{
        String courseName = studentDao.removeStudentFromCourse(1);
        String query = "SELECT COUNT(1) FROM student_courses sc LEFT JOIN courses cs ON cs.course_id = sc.course_id " +
                "WHERE student_id=1 AND course_name = ?";
        DataSource dataSource = new DataSource();
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);
        preparedStatement.setString(1,courseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(0,resultSet.getInt(1));
    }

}