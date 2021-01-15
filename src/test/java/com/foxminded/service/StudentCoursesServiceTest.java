package com.foxminded.service;

import com.foxminded.dao.DataSource;
import com.foxminded.dao.StudentCoursesDao;
import com.foxminded.dao.StudentDao;
import com.foxminded.model.StudentInf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class StudentCoursesServiceTest {
    StudentDao studentDao = mock(StudentDao.class);
    StudentCoursesDao studentCoursesDao = mock(StudentCoursesDao.class);
    StudentCoursesService studentCoursesService = new StudentCoursesService(studentCoursesDao,studentDao);

    @Test
    void saveCoursesTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws IOException, URISyntaxException,SQLException {
        given(studentDao.showAllStudents()).willReturn(Arrays.asList(new StudentInf(1,"Ivan","Ivanov")));
        doNothing().when(studentCoursesDao).saveStudentCoursesTable(any());
        studentCoursesService.saveStudentCoursesTable();
        verify(studentCoursesDao,times(1)).saveStudentCoursesTable(any());
    }

}