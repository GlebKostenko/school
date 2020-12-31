package com.foxminded.formatter;

import com.foxminded.service.DaoController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

class FormatterDataTest {
    @Mock
    private DaoController daoController;
    private FormatterData formatterData;
    FormatterDataTest(){
        MockitoAnnotations.initMocks(this);
        formatterData = new FormatterData(daoController);
    }

    @Test
    void groupsWithLessOrEqualsStudentCount() throws SQLException {
        given(daoController.searchGroupsWithLessOrEqualsStudentCount(15)).willReturn(Arrays.asList("fv-05","fk-03","rt-01"));
        String expected = "fv-05\n" + "fk-03\n" + "rt-01\n";
        assertEquals(expected,formatterData.GroupsWithLessOrEqualsStudentCount(15));
    }

    @Test
    void findStudentsRelatedToCourse() throws SQLException{
        given(daoController.findStudentsRelatedToCourse("Math")).willReturn(Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski"));
        String expected = "Victor Ivanov\n" + "Alexander Chernyev\n" + "Iakov Dymarski\n";
        assertEquals(expected,formatterData.findStudentsRelatedToCourse("Math"));
    }
}