package com.foxminded.service;

import com.foxminded.dao.DaoStudent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;

class StudentServiceTest {
    DaoStudent daoStudent = mock(DaoStudent.class);
    private StudentService studentService;
    StudentServiceTest(){
        studentService = new StudentService(daoStudent);
    }

    @Test
    void saveStudentsTable() {
        doNothing().when(daoStudent).saveStudentsTable();
        studentService.saveStudentsTable();
        verify(daoStudent,times(1)).saveStudentsTable();
    }

    @Test
    void addNewStudent() {
        doNothing().when(daoStudent).addNewStudent("Ivan","Ivanov");
        studentService.addNewStudent("Ivan","Ivanov");
        verify(daoStudent,times(1)).addNewStudent("Ivan","Ivanov");
    }

    @Test
    void addStudentToCourse() {
        doNothing().when(daoStudent).addStudentToCourse(1,1);
        studentService.addStudentToCourse(1,1);
        verify(daoStudent,times(1)).addStudentToCourse(1,1);
    }

    @Test
    void deleteStudentById() {
        doNothing().when(daoStudent).deleteStudentById(1);
        studentService.deleteStudentById(1);
        verify(daoStudent,times(1)).deleteStudentById(1);
    }

    @Test
    void removeStudentFromCourse() {
        given(daoStudent.removeStudentFromCourse(1)).willReturn("Math");
        String expected = "Math";
        assertEquals(expected,studentService.removeStudentFromCourse(1));
    }

}