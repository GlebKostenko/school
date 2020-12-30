package com.foxminded.service;

import com.foxminded.dao.DaoLayer;
import com.foxminded.dao.DataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class ServiceDaoTest {
    @Mock
    private DaoLayer dataSource;
    private ServiceDao serviceDao;
    ServiceDaoTest(){
        MockitoAnnotations.initMocks(this);
        serviceDao = new ServiceDao(dataSource);
    }
    @Test
    void searchGroupsWithLessOrEqualsStudentCount() throws SQLException {
        given(dataSource.searchGroupsWithLessOrEqualsStudentCount(15)).willReturn(Arrays.asList("fv-05","fk-03","rt-01"));
        List<String> expected = Arrays.asList("fv-05","fk-03","rt-01");
        assertEquals(expected,serviceDao.searchGroupsWithLessOrEqualsStudentCount(15));
    }

    @Test
    void findStudentsRelatedToCourse() throws SQLException{
        given(dataSource.findStudentsRelatedToCourse("Calculus")).willReturn(Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski"));
        List<String> expected = Arrays.asList("Victor Ivanov","Alexander Chernyev","Iakov Dymarski");
        assertEquals(expected,serviceDao.findStudentsRelatedToCourse("Calculus"));
    }
}