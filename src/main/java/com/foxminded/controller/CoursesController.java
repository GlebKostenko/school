package com.foxminded.controller;

import com.foxminded.dao.DataSource;
import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class CoursesController {
    private CoursesService coursesService;
    public CoursesController(CoursesService coursesService){
        this.coursesService = coursesService;
    }

    public String findStudentsRelatedToCourse(String courseName) throws SQLException {
        return coursesService.findStudentsRelatedToCourse(courseName).stream().map(x -> {
            return x + "\n";
        }).collect(Collectors.joining());
    }

    public String showAllCourses() throws SQLException {
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT course_name FROM courses");
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getString(1) +"\n");
        }
        return result.toString();
    }

    public void saveCoursesTable() {
        coursesService.saveCoursesTable();
    }
}
