package com.foxminded.controller;

import com.foxminded.model.Group;
import com.foxminded.service.GroupsService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GroupsControllerTest {
    GroupsService groupsService = mock(GroupsService.class);
    private GroupsController groupsController;
    GroupsControllerTest(){
        groupsController = new GroupsController(groupsService);
    }

    @Test
    void groupsWithLessOrEqualsStudentCount_WhenTablesAreFilled_thenShouldBeFormattedResultListFromService() throws SQLException {
        given(groupsService.searchGroupsWithLessOrEqualsStudentCount(15)).willReturn(Arrays.asList(new Group("fv-05"),new Group("fk-03"),new Group("rt-01")));
        String expected = "fv-05\n" + "fk-03\n" + "rt-01\n";
        assertEquals(expected, groupsController.GroupsWithLessOrEqualsStudentCount(15));
    }

    @Test
    void saveGroupsTable_WhenTablesAreFilled_thenShouldBeOneCallWithoutErrors() {
        doNothing().when(groupsService).saveGroupsTable();
        groupsController.saveGroupsTable();
        verify(groupsService,times(1)).saveGroupsTable();
    }

}