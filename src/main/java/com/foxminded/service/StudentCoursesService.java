package com.foxminded.service;

import com.foxminded.dao.DaoStudent;
import com.foxminded.dao.DaoStudentCourses;

public class StudentCoursesService {
    private DaoStudentCourses daoStudentCourses;
    public StudentCoursesService(DaoStudentCourses daoStudentCourses) {
        this.daoStudentCourses = daoStudentCourses;
    }

    public void saveStudentCoursesTable(){
        daoStudentCourses.saveStudentCoursesTable();
    }
}
