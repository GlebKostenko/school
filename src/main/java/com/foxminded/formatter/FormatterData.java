package com.foxminded.formatter;

import com.foxminded.service.ServiceDao;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class FormatterData {
    private ServiceDao serviceDao;
    public FormatterData(ServiceDao serviceDao){
        this.serviceDao = serviceDao;
    }

    public String GroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        return serviceDao.searchGroupsWithLessOrEqualsStudentCount(count).stream().map(x ->{
            return x + "\n";
        }).collect(Collectors.joining());
    }

    public String findStudentsRelatedToCourse(String courseName) throws SQLException{
        return serviceDao.findStudentsRelatedToCourse(courseName).stream().map(x -> {
            return x + "\n";
        }).collect(Collectors.joining());
    }
}
