package com.foxminded.service;

import com.foxminded.dao.DaoLayer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class CoursesServiceTest {
    @Mock
    private DaoLayer dataSource;
    private CoursesService coursesService;
    CoursesServiceTest(){
        MockitoAnnotations.initMocks(this);
        coursesService = new CoursesService(dataSource);
    }
    @Test
    void findStudentsRelatedToCourse() throws SQLException {
        given(dataSource.findStudentsRelatedToCourse("Calculus")).willReturn(Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski"));
        List<String> expected = Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski");
        assertEquals(expected, coursesService.findStudentsRelatedToCourse("Calculus"));
    }
}