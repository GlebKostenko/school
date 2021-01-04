package com.foxminded.service;

import com.foxminded.dao.DaoLayer;

import java.sql.SQLException;
import java.util.List;

public class CoursesService {
    private DaoLayer dataSource;
    public CoursesService(DaoLayer dataSource){
        this.dataSource = dataSource;
    }
    public List<String> findStudentsRelatedToCourse(String courseName) throws SQLException {
        return dataSource.findStudentsRelatedToCourse(courseName);
    }
}
