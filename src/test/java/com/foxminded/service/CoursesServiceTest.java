package com.foxminded.service;

import com.foxminded.dao.CoursesDao;
import com.foxminded.model.Course;
import com.foxminded.model.Student;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

class CoursesServiceTest {
    CoursesDao coursesDao = mock(CoursesDao.class);
    private CoursesService coursesService;
    CoursesServiceTest(){
        coursesService = new CoursesService(coursesDao);
    }
    @Test
    void saveCoursesTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws URISyntaxException, IOException {
        doNothing().when(coursesDao).saveCoursesTable(anyList());
        coursesService.saveCoursesTable();
        verify(coursesDao,times(1)).saveCoursesTable(anyList());
    }

    @Test
    void findStudentsRelatedToCourse_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn(){
        try {
            given(coursesDao.findStudentsRelatedToCourse("Math")).
                    willReturn(Arrays.asList(new Student(2, "Ivan", "Ivanov")));
            List<Student> expected = Arrays.asList(new Student(2, "Ivan", "Ivanov"));
            assertEquals(expected, coursesService.findStudentsRelatedToCourse("Math"));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void showAllCourses_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn(){
        try {
            given(coursesDao.showAllCourses()).
                    willReturn(Arrays.asList(new Course("Math", "Intresting")));
            List<Course> expected = Arrays.asList(new Course("Math", "Intresting"));
            assertEquals(expected, coursesService.showAllCourses());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}