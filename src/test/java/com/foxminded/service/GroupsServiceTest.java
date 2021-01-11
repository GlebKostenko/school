package com.foxminded.service;

import com.foxminded.dao.*;
import com.foxminded.model.Group;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;



class GroupsServiceTest {

    GroupsDao groupsDao = mock(GroupsDao.class);
    private GroupsService groupsService;

    GroupsServiceTest(){
        groupsService = new GroupsService(groupsDao);
    }

    @Test
    void saveGroupsTable() {
        doNothing().when(groupsDao).saveGroupsTable(anyList());
        groupsService.saveGroupsTable();
        verify(groupsDao,times(1)).saveGroupsTable(anyList());
    }

    @Test
    void searchGroupsWithLessOrEqualsStudentCount(){
        try {
            given(groupsDao.searchGroupsWithLessOrEqualsStudentCount(23)).
                    willReturn(Arrays.asList(new Group("fx-01"), new Group("us-01")));
            List<Group> expected = Arrays.asList(new Group("fx-01"), new Group("us-01"));
            assertEquals(expected, groupsService.searchGroupsWithLessOrEqualsStudentCount(23));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}