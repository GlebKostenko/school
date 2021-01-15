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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private StudentDao studentDao = mock(StudentDao.class);
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
        doNothing().when(studentDao).saveStudentsTable(any());
        studentService.saveStudentsTable();
        verify(studentDao,times(1)).saveStudentsTable(any());
    }

    @Test
    void addNewStudent_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws SQLException {
        doNothing().when(studentDao).addNewStudent("Петр","Капицы");
        studentService.addNewStudent("Петр","Капицы");
        verify(studentDao,times(1)).addNewStudent("Петр","Капицы");
    }

    @Test
    void addStudentToCourse_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws SQLException,URISyntaxException,IOException{
        doNothing().when(studentDao).addStudentToCourse(1,1);
        studentService.addStudentToCourse(1,1);
        verify(studentDao,times(1)).addStudentToCourse(1,1);
    }

    @Test
    void deleteStudentById_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws URISyntaxException,IOException,SQLException{
        doNothing().when(studentDao).deleteStudentById(1);
        studentService.deleteStudentById(1);
        verify(studentDao,times(1)).deleteStudentById(1);
    }

    @Test
    void removeStudentFromCourse_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws SQLException,IOException,URISyntaxException{
        doNothing().when(studentDao).removeStudentFromCourse(1,1);
        studentService.removeStudentFromCourse(1,1);
        verify(studentDao,times(1)).removeStudentFromCourse(1,1);
    }

    @Test
    void showAllStudents_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn()throws IOException,URISyntaxException,SQLException{
        given(studentDao.showAllStudents()).willReturn(Arrays.asList(new StudentInf(1,"Ivan","Ivanov")));
        List<StudentInf> expected = Arrays.asList(new StudentInf(1,"Ivan","Ivanov"));
        assertEquals(expected,studentService.showAllStudents());
    }

}