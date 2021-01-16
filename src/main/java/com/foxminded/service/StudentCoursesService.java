package com.foxminded.service;

import com.foxminded.dao.StudentCoursesDao;
import com.foxminded.dao.StudentDao;
import com.foxminded.model.StudentCourse;
import com.foxminded.model.StudentInf;

import java.util.ArrayList;
import java.util.List;

public class StudentCoursesService {
    private StudentCoursesDao studentCoursesDao;
    private StudentDao studentDao;

    public StudentCoursesService(StudentCoursesDao studentCoursesDao,StudentDao studentDao) {
        this.studentCoursesDao = studentCoursesDao;
        this.studentDao = studentDao;
    }

    public void saveStudentCoursesTable(){
        studentCoursesDao.saveStudentCoursesTable(generateCoursesForStudent());
    }
    private List<StudentCourse> generateCoursesForStudent() {
        List<StudentCourse> result = new ArrayList<>();
        for (StudentInf studentInf : studentDao.showAllStudents()) {
            int numberOfCoursesForStudent = 1 + (int) (Math.random() * 3);
                int[] numbersOfCoursesThatUsersAlreadyHas = new int[3];
                for (int j = 0; j < numberOfCoursesForStudent; ++j) {
                    int randomNumberOfCourse = 1  + (int)(Math.random() * 10);
                    result.add(new StudentCourse(studentInf.getStudentId(),randomNumberOfCourse));
                }
        }
        return result;
    }
}
