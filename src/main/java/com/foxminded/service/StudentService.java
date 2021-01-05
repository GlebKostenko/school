package com.foxminded.service;

import com.foxminded.dao.DaoStudent;

public class StudentService {
    private DaoStudent daoStudent;
    public StudentService(DaoStudent daoStudent){
        this.daoStudent = daoStudent;
    }

    public void saveStudentsTable(){
        daoStudent.saveStudentsTable();
    }

    public void addNewStudent(String firstName, String lastName){
        daoStudent.addNewStudent(firstName,lastName);
    }

    public void addStudentToCourse(int studentId, int courseId){
        daoStudent.addStudentToCourse(studentId,courseId);
    }

    public void deleteStudentById(int studentId){
        daoStudent.deleteStudentById(studentId);
    }
    public String removeStudentFromCourse(int studentId){
        return daoStudent.removeStudentFromCourse(studentId);
    }
}
