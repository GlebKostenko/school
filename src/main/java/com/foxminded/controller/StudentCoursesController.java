package com.foxminded.controller;

import com.foxminded.service.StudentCoursesService;

public class StudentCoursesController {
    private StudentCoursesService studentCoursesService;
    public StudentCoursesController(StudentCoursesService studentCoursesService) {
        this.studentCoursesService = studentCoursesService;
    }

    public void saveStudentCoursesTable() {
        studentCoursesService.saveStudentCoursesTable();
    }
}
