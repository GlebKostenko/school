package com.foxminded.controller;

import com.foxminded.model.Course;
import com.foxminded.service.CoursesService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class CoursesController {
    private CoursesService coursesService;
    public CoursesController(CoursesService coursesService){
        this.coursesService = coursesService;
    }

    public String findStudentsRelatedToCourse(String courseName) throws SQLException {
        return coursesService.findStudentsRelatedToCourse(courseName).stream().map(x -> {
            return x.getFirstName()+" "+ x.getLastName() + "\n";
        }).collect(Collectors.joining());
    }

    public String showAllCourses() throws SQLException {
        StringBuilder result = new StringBuilder();
        for(Course course : coursesService.showAllCourses() ){
            result.append(course.getCourseName() + "\n");
        }
        return result.toString();
    }

    public void saveCoursesTable() throws URISyntaxException, IOException {
        coursesService.saveCoursesTable();
    }
}
