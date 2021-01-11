package com.foxminded.controller;

import com.foxminded.model.StudentInf;
import com.foxminded.service.StudentService;
import com.foxminded.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

class StudentControllerTest {
    StudentService studentService = mock(StudentService.class);
    private StudentController studentController;
    StudentControllerTest(){
        studentController = new StudentController(studentService);
    }

    @Test
    void removeStudentFromCourse() {
        doNothing().when(studentService).removeStudentFromCourse(15,2);
        studentController.removeStudentFromCourse(15,2);
        verify(studentService,times(1)).removeStudentFromCourse(15,2);
    }

    @Test
    void saveStudentsTable() throws URISyntaxException, IOException {
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

    @Test
    void showAllStudents(){
        try {
            given(studentService.showAllStudents()).
                    willReturn(Arrays.asList(new StudentInf(1, "Ivan", "Ivanov")));
            String expected = "1 Ivan Ivanov\n";
            assertEquals(expected,studentController.showAllStudents());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}