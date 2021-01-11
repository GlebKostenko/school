package com.foxminded.service;

import com.foxminded.dao.StudentCoursesDao;
import com.foxminded.dao.StudentDao;
import com.foxminded.model.StudentCourse;
import com.foxminded.model.StudentInf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentCoursesService {
    private StudentCoursesDao studentCoursesDao;
    private StudentDao studentDao;

    public StudentCoursesService(StudentCoursesDao studentCoursesDao) {
        this.studentCoursesDao = studentCoursesDao;
    }

    public void saveStudentCoursesTable(){
        studentCoursesDao.saveStudentCoursesTable(generateCoursesForStudent());
    }
    private List<StudentCourse> generateCoursesForStudent() {
        try {
            List<StudentCourse> result = new ArrayList<>();
            studentDao = new StudentDao();
            for (StudentInf studentInf : studentDao.showAllStudents()) {
                int numberOfCoursesForStudent = 1 + (int) (Math.random() * 3);
                int[] numbersOfCoursesThatUsersAlreadyHas = new int[3];
                for (int j = 0; j < numberOfCoursesForStudent; ++j) {
                    int randomNumberOfCourse = 1  + (int)(Math.random() * 10);
                    result.add(new StudentCourse(studentInf.getStudentId(),randomNumberOfCourse));
                }
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
