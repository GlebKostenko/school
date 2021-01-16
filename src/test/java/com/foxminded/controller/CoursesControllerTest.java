package com.foxminded.controller;

import com.foxminded.model.Course;
import com.foxminded.model.Student;
import com.foxminded.service.CoursesService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CoursesControllerTest {
    private CoursesService coursesService = mock(CoursesService.class);
    private CoursesController coursesController = new CoursesController(coursesService);
    @Test
    void saveCoursesTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws URISyntaxException, IOException {
        doNothing().when(coursesService).saveCoursesTable();
        coursesController.saveCoursesTable();
        verify(coursesService,times(1)).saveCoursesTable();
    }

    @Test
    void findStudentsRelatedToCourse_WhenTablesAreFilled_thenShouldBeFormattedResultListFromService() throws SQLException{
        given(coursesService.findStudentsRelatedToCourse(1)).willReturn(Arrays.asList(
                new Student(1,"Victor","Ivanov"),
                new Student(2,"Alexander","Chernyev"),
                new Student(3,"Iakov","Dymarski"))
        );
        String expected = "Victor Ivanov\n" + "Alexander Chernyev\n" + "Iakov Dymarski\n";
        assertEquals(expected, coursesController.findStudentsRelatedToCourse(1));
    }

    @Test
    void showAllCourses_WhenTablesAreFilled_thenShouldBeFormattedResultListFromService(){
        try {
            given(coursesService.showAllCourses()).
                    willReturn(Arrays.asList(new Course("Math", "Intresting")));
            String expected = "1.Math\n";
            assertEquals(expected, coursesController.showAllCourses());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}