package com.foxminded.service;

import com.foxminded.dao.StudentCoursesDao;

public class StudentCoursesService {
    private StudentCoursesDao studentCoursesDao;
    public StudentCoursesService(StudentCoursesDao studentCoursesDao) {
        this.studentCoursesDao = studentCoursesDao;
    }

    public void saveStudentCoursesTable(){
        studentCoursesDao.saveStudentCoursesTable();
    }
}
