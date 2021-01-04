package com.foxminded.service;

import com.foxminded.dao.DaoCourses;
import com.foxminded.dao.DaoLayer;
import com.foxminded.dao.DaoStudent;
import com.foxminded.dao.DataSource;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private DaoLayer dataSource;
    public StudentService(DaoLayer dataSource){
        this.dataSource = dataSource;
    }

    public void fillTables(){
        dataSource.save();
    }

    public void save(){
        dataSource.save();
    }

    public void addNewStudent(String firstName, String lastName){
        dataSource.addNewStudent(firstName,lastName);
    }

    public void addStudentToCourse(int studentId, int courseId){
        dataSource.addStudentToCourse(studentId,courseId);
    }

    public void deleteStudentById(int studentId){
        dataSource.deleteStudentById(studentId);
    }
    public String removeStudentFromCourse(int studentId){
        return dataSource.removeStudentFromCourse(studentId);
    }
}
