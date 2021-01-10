package com.foxminded.controller;

import com.foxminded.service.GroupsService;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class GroupsController {
    private GroupsService groupsService;
    public GroupsController(GroupsService groupsService){
        this.groupsService = groupsService;
    }

    public String GroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        return groupsService.searchGroupsWithLessOrEqualsStudentCount(count).stream().map(x ->{
            return x.getGroupName() + "\n";
        }).collect(Collectors.joining());
    }

    public void saveGroupsTable() {
        groupsService.saveGroupsTable();
    }
}
