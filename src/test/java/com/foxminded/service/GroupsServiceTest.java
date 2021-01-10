package com.foxminded.service;

import com.foxminded.dao.*;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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

}