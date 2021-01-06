package com.foxminded.controller;

import com.foxminded.dao.DataSource;
import com.foxminded.service.StudentService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentController {
    private StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public String showAllStudents() throws SQLException {
        return studentService.showAllStudents();
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
