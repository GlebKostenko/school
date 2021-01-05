package com.foxminded.service;

import com.foxminded.dao.DaoCourses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CoursesServiceTest {
    DaoCourses daoCourses = mock(DaoCourses.class);
    private CoursesService coursesService;
    CoursesServiceTest(){
        coursesService = new CoursesService(daoCourses);
    }
    @Test
    void saveCoursesTable() {
        doNothing().when(daoCourses).saveCoursesTable();
        coursesService.saveCoursesTable();
        verify(daoCourses,times(1)).saveCoursesTable();
    }
}