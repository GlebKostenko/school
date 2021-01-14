package com.foxminded.service;

import com.foxminded.dao.*;
import com.foxminded.model.Student;
import com.foxminded.model.StudentInf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentServiceTest {
    private StudentDao studentDao = new StudentDao();
    private StudentService studentService = new StudentService(studentDao);
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
    void saveStudentsTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws URISyntaxException, IOException {
        GroupsDao groupsDao = new GroupsDao();
        GroupsService groupsService = new GroupsService(groupsDao);
        groupsService.saveGroupsTable();
        StudentDao studentDao = new StudentDao();
        StudentService studentService = new StudentService(studentDao);
        studentService.saveStudentsTable();
        try {
            assertTrue(!studentService.showAllStudents().isEmpty());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void addNewStudent_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws SQLException {
        studentService.addNewStudent("Пётр","Капица");
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        assertTrue(studentService.showAllStudents().contains(new StudentInf(1,"Пётр","Капица")));
    }

    @Test
    void addStudentToCourse_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws SQLException,URISyntaxException,IOException{
        GroupsDao groupsDao = new GroupsDao();
        GroupsService groupsService = new GroupsService(groupsDao);
        groupsService.saveGroupsTable();
        StudentDao studentDao = new StudentDao();
        StudentService studentService = new StudentService(studentDao);
        studentService.saveStudentsTable();
        CoursesDao coursesDao = new CoursesDao();
        CoursesService coursesService = new CoursesService(coursesDao);
        coursesService.saveCoursesTable();
        studentService.addStudentToCourse(1,1);
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
    void deleteStudentById_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws URISyntaxException,IOException,SQLException{
        GroupsDao groupsDao = new GroupsDao();
        GroupsService groupsService = new GroupsService(groupsDao);
        groupsService.saveGroupsTable();
        StudentDao studentDao = new StudentDao();
        StudentService studentService = new StudentService(studentDao);
        studentService.saveStudentsTable();
        studentService.deleteStudentById(1);
        boolean dontExist = true;
        for(StudentInf studentInf : studentService.showAllStudents()){
            if(studentInf.getStudentId() == 1){
                dontExist = false;
            }
        }
        assertTrue(dontExist);
    }

    @Test
    void removeStudentFromCourse_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws SQLException,IOException,URISyntaxException{
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
        studentService.addStudentToCourse(1,1);
        studentService.removeStudentFromCourse(1,1);
        StudentInf studentInf = studentService.showAllStudents().get(0);
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
    void showAllStudents_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn()throws IOException,URISyntaxException,SQLException{
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
        assertEquals(studentService.showAllStudents(),studentDao.showAllStudents());
    }

}