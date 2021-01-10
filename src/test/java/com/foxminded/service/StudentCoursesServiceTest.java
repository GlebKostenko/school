package com.foxminded.service;

import com.foxminded.dao.StudentCoursesDao;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class StudentCoursesServiceTest {

    StudentCoursesDao studentCoursesDao = mock(StudentCoursesDao.class);
    private StudentCoursesService studentCoursesService;
    StudentCoursesServiceTest(){
        studentCoursesService = new StudentCoursesService(studentCoursesDao);
    }

    @Test
    void saveCoursesTable() {
        doNothing().when(studentCoursesDao).saveStudentCoursesTable();
        studentCoursesService.saveStudentCoursesTable();
        verify(studentCoursesDao,times(1)).saveStudentCoursesTable();
    }

}