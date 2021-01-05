package com.foxminded.controller;

import com.foxminded.dao.DaoStudentCourses;
import com.foxminded.service.StudentCoursesService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class StudentCoursesControllerTest {

    StudentCoursesService studentCoursesService = mock(StudentCoursesService.class);
    private StudentCoursesController studentCoursesController;
    StudentCoursesControllerTest(){
        studentCoursesController = new StudentCoursesController(studentCoursesService);
    }

    @Test
    void saveCoursesTable() {
        doNothing().when(studentCoursesService).saveStudentCoursesTable();
        studentCoursesController.saveStudentCoursesTable();
        verify(studentCoursesService,times(1)).saveStudentCoursesTable();
    }

}