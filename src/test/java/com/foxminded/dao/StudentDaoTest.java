package com.foxminded.dao;

import com.foxminded.model.Student;
import com.foxminded.model.StudentInf;
import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentCoursesService;
import com.foxminded.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class StudentDaoTest {
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
        StudentCoursesService studentCoursesService = new StudentCoursesService(studentCoursesDao,studentDao);
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
    void addNewStudent_WhenEverythingGoesRight_thenThisStudentExist() throws SQLException {
        studentDao.addNewStudent("Пётр","Капица");
        assertTrue(studentDao.showAllStudents().contains(new StudentInf(201,"Пётр","Капица")));
    }

    @Test
    void addStudentToCourse_WhenEverythingGoesRight_thenShouldBeStudentOnThisCourse() throws SQLException{
        CoursesDao coursesDao = new CoursesDao();
        studentDao.addStudentToCourse(1,1);
        StudentInf studentInf = studentDao.showAllStudents().get(0);
        boolean exist = false;
        for(Student student :  coursesDao.findStudentsRelatedToCourse(1)){
            if(studentInf.getFirstName().equals(student.getFirstName())
                    && studentInf.getLastName().equals(student.getLastName()))
            {
                       exist = true;
            }
        }
        assertTrue(exist);
    }

    @Test
    void deleteStudentById_WhenTablesAreFilled_thenShouldBeNoStudentsWithSameId() throws SQLException{

        studentDao.deleteStudentById(1);
        boolean dontExist = true;
        for(StudentInf studentInf : studentDao.showAllStudents()){
            if(studentInf.getStudentId() == 1){
                dontExist = false;
            }
        }
        assertTrue(dontExist);
    }

    @Test
    void removeStudentFromCourse_WhenTablesAreFilled_thenShouldBeStudentDoesNotHaveThisCourse()throws SQLException {
        studentDao.addStudentToCourse(1,1);
        studentDao.removeStudentFromCourse(1,1);
        StudentInf studentInf = studentDao.showAllStudents().get(0);
        CoursesDao coursesDao = new CoursesDao();
        boolean removedStudent = true;
        for(Student student : coursesDao.findStudentsRelatedToCourse(1)){
            if(student.getFirstName().equals(studentInf.getFirstName())
                    && student.getLastName().equals(studentInf.getLastName()))
            {
                removedStudent = false;
            }
        }
        assertTrue(removedStudent);
    }

    @Test
    void saveStudentsTable_WhenEverythingGoesRight_thenShouldBeDataInATable() {
        try {
            DataSource dataSource = new DataSource();
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet  resultSet = statement.executeQuery("SELECT COUNT(student_id) FROM students");
            resultSet.next();
            assertTrue(resultSet.getInt(1) > 100);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void showAllStudents_WhenTablesAreFilled_thenShouldBeNotEmptyResultList() {
        assertTrue(!studentDao.showAllStudents().isEmpty());
    }

    @Test
    void showAllStudents_WhenTableAreEmpty_thenShouldBeEmptyResultList() throws SQLException{
        studentDao.deleteAll();
        assertTrue(studentDao.showAllStudents().isEmpty());
    }
}