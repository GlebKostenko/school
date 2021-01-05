package com.foxminded.controller;

import com.foxminded.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class StudentControllerTest {
    @Mock
    private StudentService studentService;
    private StudentController studentController;
    StudentControllerTest(){
        MockitoAnnotations.initMocks(this);
        studentController = new StudentController(studentService);
    }

    @Test
    void removeStudentFromCourse() {
        given(studentService.removeStudentFromCourse(15)).willReturn("Biology");
        String expected = "Biology";
        assertEquals(expected,studentController.removeStudentFromCourse(15));
    }
}