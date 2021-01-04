package com.foxminded.controller;

import com.foxminded.dao.DaoCourses;
import com.foxminded.dao.DaoStudent;
import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentService;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class StudentController {

    public String showAllStudents() throws SQLException{
        DaoStudent daoStudent = new DaoStudent();
        return daoStudent.showAllStudents();
    }

    public String showAllCourses() throws SQLException{
        DaoCourses daoCourses = new DaoCourses();
        return daoCourses.showAllCourses();
    }

}
