package com.foxminded.controller;

import com.foxminded.service.CoursesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class CoursesControllerTest {
    @Mock
    private CoursesService coursesService;
    private CoursesController coursesController;
    CoursesControllerTest(){
        MockitoAnnotations.initMocks(this);
        coursesController = new CoursesController(coursesService);
    }

    @Test
    void findStudentsRelatedToCourse() throws SQLException{
        given(coursesService.findStudentsRelatedToCourse("Math")).willReturn(Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski"));
        String expected = "Victor Ivanov\n" + "Alexander Chernyev\n" + "Iakov Dymarski\n";
        assertEquals(expected, coursesController.findStudentsRelatedToCourse("Math"));
    }
}