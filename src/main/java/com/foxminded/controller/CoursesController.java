package com.foxminded.controller;

import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentService;

import java.sql.SQLException;
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
}