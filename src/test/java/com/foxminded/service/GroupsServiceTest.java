package com.foxminded.service;

import com.foxminded.dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GroupsServiceTest {

    DaoGroups daoGroups = mock(DaoGroups.class);
    private GroupsService groupsService;
    GroupsServiceTest(){
        groupsService = new GroupsService(daoGroups);
    }

    @Test
    void saveGroupsTable() {
        doNothing().when(daoGroups).saveGroupsTable();
        groupsService.saveGroupsTable();
        verify(daoGroups,times(1)).saveGroupsTable();
    }

}