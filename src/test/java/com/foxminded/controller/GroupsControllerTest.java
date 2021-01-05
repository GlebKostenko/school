package com.foxminded.controller;

import com.foxminded.dao.DaoGroups;
import com.foxminded.service.GroupsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void groupsWithLessOrEqualsStudentCount() throws SQLException {
        given(groupsService.searchGroupsWithLessOrEqualsStudentCount(15)).willReturn(Arrays.asList("fv-05","fk-03","rt-01"));
        String expected = "fv-05\n" + "fk-03\n" + "rt-01\n";
        assertEquals(expected, groupsController.GroupsWithLessOrEqualsStudentCount(15));
    }

    @Test
    void saveGroupsTable() {
        doNothing().when(groupsService).saveGroupsTable();
        groupsController.saveGroupsTable();
        verify(groupsService,times(1)).saveGroupsTable();
    }

}