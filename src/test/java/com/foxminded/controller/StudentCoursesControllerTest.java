package com.foxminded.controller;

import com.foxminded.service.StudentCoursesService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class StudentCoursesControllerTest {

    private StudentCoursesService studentCoursesService = mock(StudentCoursesService.class);
    private StudentCoursesController studentCoursesController = new StudentCoursesController(studentCoursesService);

    @Test
    void saveCoursesTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() {
        doNothing().when(studentCoursesService).saveStudentCoursesTable();
        studentCoursesController.saveStudentCoursesTable();
        verify(studentCoursesService,times(1)).saveStudentCoursesTable();
    }

}