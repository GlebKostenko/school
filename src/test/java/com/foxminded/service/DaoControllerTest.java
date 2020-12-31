package com.foxminded.service;

import com.foxminded.dao.DaoLayer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class DaoControllerTest {
    @Mock
    private DaoLayer dataSource;
    private DaoController daoController;
    DaoControllerTest(){
        MockitoAnnotations.initMocks(this);
        daoController = new DaoController(dataSource);
    }
    @Test
    void searchGroupsWithLessOrEqualsStudentCount() throws SQLException {
        given(dataSource.searchGroupsWithLessOrEqualsStudentCount(15)).willReturn(Arrays.asList("fv-05","fk-03","rt-01"));
        List<String> expected = Arrays.asList("fv-05","fk-03","rt-01");
        assertEquals(expected, daoController.searchGroupsWithLessOrEqualsStudentCount(15));
    }

    @Test
    void findStudentsRelatedToCourse() throws SQLException{
        given(dataSource.findStudentsRelatedToCourse("Calculus")).willReturn(Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski"));
        List<String> expected = Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski");
        assertEquals(expected, daoController.findStudentsRelatedToCourse("Calculus"));
    }
}