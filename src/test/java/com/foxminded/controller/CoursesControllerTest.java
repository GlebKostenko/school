package com.foxminded.controller;

import com.foxminded.dao.DaoCourses;
import com.foxminded.service.CoursesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CoursesControllerTest {
    CoursesService coursesService = mock(CoursesService.class);
    private CoursesController coursesController;
    CoursesControllerTest(){
        coursesController = new CoursesController(coursesService);
    }

    @Test
    void saveCoursesTable() {
        doNothing().when(coursesService).saveCoursesTable();
        coursesController.saveCoursesTable();
        verify(coursesService,times(1)).saveCoursesTable();
    }

    @Test
    void findStudentsRelatedToCourse() throws SQLException{
        given(coursesService.findStudentsRelatedToCourse("Math")).willReturn(Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski"));
        String expected = "Victor Ivanov\n" + "Alexander Chernyev\n" + "Iakov Dymarski\n";
        assertEquals(expected, coursesController.findStudentsRelatedToCourse("Math"));
    }
}