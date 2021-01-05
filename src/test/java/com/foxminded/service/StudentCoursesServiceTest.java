package com.foxminded.service;

import com.foxminded.dao.DaoCourses;
import com.foxminded.dao.DaoStudentCourses;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class StudentCoursesServiceTest {

    DaoStudentCourses daoStudentCourses = mock(DaoStudentCourses.class);
    private StudentCoursesService studentCoursesService;
    StudentCoursesServiceTest(){
        studentCoursesService = new StudentCoursesService(daoStudentCourses);
    }

    @Test
    void saveCoursesTable() {
        doNothing().when(daoStudentCourses).saveStudentCoursesTable();
        studentCoursesService.saveStudentCoursesTable();
        verify(daoStudentCourses,times(1)).saveStudentCoursesTable();
    }

}