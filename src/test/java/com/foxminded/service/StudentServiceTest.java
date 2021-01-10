package com.foxminded.service;

import com.foxminded.dao.StudentDao;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;

class StudentServiceTest {
    StudentDao studentDao = mock(StudentDao.class);
    private StudentService studentService;
    StudentServiceTest(){
        studentService = new StudentService(studentDao);
    }

    @Test
    void saveStudentsTable() throws URISyntaxException, IOException {
        doNothing().when(studentDao).saveStudentsTable(anyList());
        studentService.saveStudentsTable();
        verify(studentDao,times(1)).saveStudentsTable(anyList());
    }

    @Test
    void addNewStudent() {
        doNothing().when(studentDao).addNewStudent("Ivan","Ivanov");
        studentService.addNewStudent("Ivan","Ivanov");
        verify(studentDao,times(1)).addNewStudent("Ivan","Ivanov");
    }

    @Test
    void addStudentToCourse() {
        doNothing().when(studentDao).addStudentToCourse(1,1);
        studentService.addStudentToCourse(1,1);
        verify(studentDao,times(1)).addStudentToCourse(1,1);
    }

    @Test
    void deleteStudentById() {
        doNothing().when(studentDao).deleteStudentById(1);
        studentService.deleteStudentById(1);
        verify(studentDao,times(1)).deleteStudentById(1);
    }

    @Test
    void removeStudentFromCourse() {
        given(studentDao.removeStudentFromCourse(1)).willReturn("Math");
        String expected = "Math";
        assertEquals(expected,studentService.removeStudentFromCourse(1));
    }

}