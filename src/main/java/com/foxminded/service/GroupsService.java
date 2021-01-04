package com.foxminded.service;

import com.foxminded.dao.DaoLayer;

import java.sql.SQLException;
import java.util.List;

public class GroupsService {
    private DaoLayer dataSource;
    public GroupsService(DaoLayer dataSource){
        this.dataSource = dataSource;
    }
    public List<String> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        return dataSource.searchGroupsWithLessOrEqualsStudentCount(count);
    }
}
