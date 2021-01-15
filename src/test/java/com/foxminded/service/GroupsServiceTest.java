package com.foxminded.service;

import com.foxminded.dao.GroupsDao;
import com.foxminded.model.Group;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class GroupsServiceTest {
    private GroupsDao groupsDao = mock(GroupsDao.class);
    private GroupsService groupsService = new GroupsService(groupsDao);

    @Test
    void saveGroupsTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() throws URISyntaxException,IOException{
        doNothing().when(groupsDao).saveGroupsTable(any());
        groupsService.saveGroupsTable();
        verify(groupsDao,times(1)).saveGroupsTable(any());
    }

    @Test
    void searchGroupsWithLessOrEqualsStudentCount_WhenTablesAreFilled_thenShouldBeTheSameResultListAsDaoReturn() throws SQLException{
        given(groupsDao.searchGroupsWithLessOrEqualsStudentCount(20)).willReturn(Arrays.asList(new Group("ml-25")));
        List<Group> expected = Arrays.asList(new Group("ml-25"));
        assertEquals(expected,groupsService.searchGroupsWithLessOrEqualsStudentCount(20));
    }

}