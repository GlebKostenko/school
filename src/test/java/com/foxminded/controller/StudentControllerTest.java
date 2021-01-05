package com.foxminded.controller;

import com.foxminded.service.StudentService;
import com.foxminded.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class StudentControllerTest {
    StudentService studentService = mock(StudentService.class);
    private StudentController studentController;
    StudentControllerTest(){
        studentController = new StudentController(studentService);
    }

    @Test
    void removeStudentFromCourse() {
        given(studentService.removeStudentFromCourse(15)).willReturn("Biology");
        String expected = "Biology";
        assertEquals(expected,studentController.removeStudentFromCourse(15));
    }

    @Test
    void saveStudentsTable() {
        doNothing().when(studentService).saveStudentsTable();
        studentController.saveStudentsTable();
        verify(studentService,times(1)).saveStudentsTable();
    }

    @Test
    void addNewStudent() {
        doNothing().when(studentService).addNewStudent("Ivan","Ivanov");
        studentController.addNewStudent("Ivan","Ivanov");
        verify(studentService,times(1)).addNewStudent("Ivan","Ivanov");
    }

    @Test
    void addStudentToCourse() {
        doNothing().when(studentService).addStudentToCourse(1,1);
        studentController.addStudentToCourse(1,1);
        verify(studentService,times(1)).addStudentToCourse(1,1);
    }

    @Test
    void deleteStudentById() {
        doNothing().when(studentService).deleteStudentById(1);
        studentController.deleteStudentById(1);
        verify(studentService,times(1)).deleteStudentById(1);
    }

}