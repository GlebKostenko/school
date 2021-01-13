package com.foxminded.service;

import com.foxminded.dao.StudentDao;
import com.foxminded.model.StudentInf;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    StudentDao studentDao = mock(StudentDao.class);
    private StudentService studentService;
    StudentServiceTest(){
        studentService = new StudentService(studentDao);
    }

    @Test
    void saveStudentsTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws URISyntaxException, IOException {
        doNothing().when(studentDao).saveStudentsTable(anyList());
        studentService.saveStudentsTable();
        verify(studentDao,times(1)).saveStudentsTable(anyList());
    }

    @Test
    void addNewStudent_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() {
        doNothing().when(studentDao).addNewStudent("Ivan","Ivanov");
        studentService.addNewStudent("Ivan","Ivanov");
        verify(studentDao,times(1)).addNewStudent("Ivan","Ivanov");
    }

    @Test
    void addStudentToCourse_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() {
        doNothing().when(studentDao).addStudentToCourse(1,1);
        studentService.addStudentToCourse(1,1);
        verify(studentDao,times(1)).addStudentToCourse(1,1);
    }

    @Test
    void deleteStudentById_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() {
        doNothing().when(studentDao).deleteStudentById(1);
        studentService.deleteStudentById(1);
        verify(studentDao,times(1)).deleteStudentById(1);
    }

    @Test
    void removeStudentFromCourse_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() {
        doNothing().when(studentDao).removeStudentFromCourse(1,1);
        studentService.removeStudentFromCourse(1,1);
        verify(studentDao,times(1)).removeStudentFromCourse(1,1);
    }

    @Test
    void showAllStudents_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn(){
        try {
            given(studentDao.showAllStudents()).
                    willReturn(Arrays.asList(new StudentInf(1, "Ivan", "Ivanov")));
            List<StudentInf> expected = Arrays.asList(new StudentInf(1, "Ivan", "Ivanov"));
            assertEquals(expected,studentService.showAllStudents());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}