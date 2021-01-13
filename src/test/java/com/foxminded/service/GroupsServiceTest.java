package com.foxminded.service;

import com.foxminded.dao.*;
import com.foxminded.model.Group;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;



class GroupsServiceTest {
    private GroupsDao groupsDao = new GroupsDao();
    private GroupsService groupsService = new GroupsService(groupsDao);
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

    GroupsServiceTest(){
        groupsService = new GroupsService(groupsDao);
    }

    @Test
    void saveGroupsTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() {
        try {
            GroupsDao groupsDao = new GroupsDao();
            GroupsService groupsService = new GroupsService(groupsDao);
            groupsService.saveGroupsTable();
            DataSource dataSource = new DataSource();
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(group_id) FROM groups ");
            resultSet.next();
            assertEquals(true,resultSet.getInt(1) > 5);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void searchGroupsWithLessOrEqualsStudentCount_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn() throws IOException, URISyntaxException
    {
        GroupsDao groupsDao = new GroupsDao();
        GroupsService groupsService = new GroupsService(groupsDao);
        groupsService.saveGroupsTable();
        StudentDao studentDao = new StudentDao();
        StudentService studentService = new StudentService(studentDao);
        studentService.saveStudentsTable();
        assertEquals(true,!groupsDao.searchGroupsWithLessOrEqualsStudentCount(20).isEmpty());
    }

}