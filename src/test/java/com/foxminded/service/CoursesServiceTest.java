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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CoursesServiceTest {
    private CoursesDao coursesDao = mock(CoursesDao.class);
    private CoursesService coursesService = new CoursesService(coursesDao);

    @Test
    void saveCoursesTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws IOException,URISyntaxException {
        doNothing().when(coursesDao).saveCoursesTable(any());
        coursesService.saveCoursesTable();
        verify(coursesDao,times(1)).saveCoursesTable(any());
    }
    @Test
    void findStudentsRelatedToCourse_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn() throws SQLException{
        given(coursesDao.findStudentsRelatedToCourse(1)).
                willReturn(Arrays.asList(new Student(1,"Ivan","Ivanov")));
        List<Student> expected = Arrays.asList(new Student(1,"Ivan","Ivanov"));
        assertEquals(expected,coursesService.findStudentsRelatedToCourse(1));
    }

    @Test
    void showAllCourses_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn() throws SQLException{
        given(coursesDao.showAllCourses()).
                willReturn(Arrays.asList(new Course("Math","Intresting")));
        List<Course> expected = Arrays.asList(new Course("Math","Intresting"));
        assertEquals(expected,coursesService.showAllCourses());
    }
}