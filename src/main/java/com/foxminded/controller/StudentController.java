package com.foxminded.controller;

import com.foxminded.dao.DataSource;
import com.foxminded.service.StudentService;

import java.io.IOException;
import java.net.URISyntaxException;
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
        return studentService.showAllStudents().stream().map(x->{
            return x.getStudentId() +" "+ x.getFirstName()+ " " + x.getLastName() + "\n";
        }).collect(Collectors.joining());
    }

    public void saveStudentsTable()throws URISyntaxException, IOException {
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

    public void removeStudentFromCourse(int studentId,int courseId){
        studentService.removeStudentFromCourse(studentId,courseId);
    }

}
