package com.foxminded.service;

import com.foxminded.dao.CoursesDao;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

class CoursesServiceTest {
    CoursesDao coursesDao = mock(CoursesDao.class);
    private CoursesService coursesService;
    CoursesServiceTest(){
        coursesService = new CoursesService(coursesDao);
    }
    @Test
    void saveCoursesTable() throws URISyntaxException, IOException {
        doNothing().when(coursesDao).saveCoursesTable(anyList());
        coursesService.saveCoursesTable();
        verify(coursesDao,times(1)).saveCoursesTable(anyList());
    }
}