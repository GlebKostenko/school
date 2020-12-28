package com.foxminded.service;

import com.foxminded.dao.DaoLayer;

import java.sql.SQLException;
import java.util.List;

public class ServiceDao {
    private DaoLayer dataSource;
    public ServiceDao(DaoLayer dataSource){
        this.dataSource = dataSource;
    }

    public void fillTables(){
        dataSource.save();
    }

    public List<String> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        return dataSource.searchGroupsWithLessOrEqualsStudentCount(count);
    }

    public List<String> findStudentsRelatedToCourse(String courseName) throws SQLException{
        return dataSource.findStudentsRelatedToCourse(courseName);
    }
}
