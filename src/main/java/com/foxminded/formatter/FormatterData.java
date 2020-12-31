package com.foxminded.formatter;

import com.foxminded.dao.DaoCourses;
import com.foxminded.dao.DaoStudent;
import com.foxminded.service.DaoController;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class FormatterData {
    private DaoController daoController;
    public FormatterData(DaoController daoController){
        this.daoController = daoController;
    }

    public String GroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        return daoController.searchGroupsWithLessOrEqualsStudentCount(count).stream().map(x ->{
            return x + "\n";
        }).collect(Collectors.joining());
    }

    public String findStudentsRelatedToCourse(String courseName) throws SQLException{
        return daoController.findStudentsRelatedToCourse(courseName).stream().map(x -> {
            return x + "\n";
        }).collect(Collectors.joining());
    }

    public String showAllStudents() throws SQLException{
        DaoStudent daoStudent = new DaoStudent();
        return daoStudent.showAllStudents();
    }

    public String showAllCourses() throws SQLException{
        DaoCourses daoCourses = new DaoCourses();
        return daoCourses.showAllCourses();
    }
}
