package com.foxminded.formatter;

import com.foxminded.dao.DataSource;
import com.foxminded.service.ServiceDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

class FormatterDataTest {
    @Mock
    private ServiceDao serviceDao;
    private FormatterData formatterData;
    FormatterDataTest(){
        MockitoAnnotations.initMocks(this);
        formatterData = new FormatterData(serviceDao);
    }

    @Test
    void groupsWithLessOrEqualsStudentCount() throws SQLException {
        given(serviceDao.searchGroupsWithLessOrEqualsStudentCount(15)).willReturn(Arrays.asList("fv-05","fk-03","rt-01"));
        String expected = "fv-05\n" + "fk-03\n" + "rt-01\n";
        assertEquals(expected,formatterData.GroupsWithLessOrEqualsStudentCount(15));
    }

    @Test
    void findStudentsRelatedToCourse() throws SQLException{
        given(serviceDao.findStudentsRelatedToCourse("Math")).willReturn(Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski"));
        String expected = "Victor Ivanov\n" + "Alexander Chernyev\n" + "Iakov Dymarski\n";
        assertEquals(expected,formatterData.findStudentsRelatedToCourse("Math"));
    }
}