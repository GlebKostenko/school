package com.foxminded.controller;

import com.foxminded.dao.DaoCourses;
import com.foxminded.dao.DaoStudent;
import com.foxminded.dao.DataSource;
import com.foxminded.service.CoursesService;
import com.foxminded.service.GroupsService;
import com.foxminded.service.StudentService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class StudentController {
    private StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public String showAllStudents() throws SQLException {
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getInt(1) +" "
                    + resultSet.getString(3) +" "
                    + resultSet.getString(4) + "\n");
        }
        return result.toString();
    }

    public void saveStudentsTable(){
        studentService.saveStudentsTable();
    }

    public void addNewStudent(String firstName, String lastName){
        studentService.addNewStudent(firstName,lastName);
    }

    public void addStudentToCourse(int studentId, int courseId){
        studentService.addStudentToCourse(studentId,courseId);
    }

    public void deleteStudentById(int studentId){
        studentService.deleteStudentById(studentId);
    }

    public String removeStudentFromCourse(int studentId){
        return studentService.removeStudentFromCourse(studentId);
    }

}
