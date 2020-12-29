package com.foxminded.dao;

import java.sql.SQLException;
import java.util.List;

public interface DaoLayer {
    void addNewStudent(String firstName, String lastName);
    void addStudentToCourse(int studentId,int courseId);
    void deleteStudentById(int studentId);
    String  removeStudentFromCourse(int studentId);
    void save();
    void saveGroupsTable();
    void saveStudentsTable();
    void saveCoursesTable();
    void saveStudentCoursesTable();
    List<String> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException;
    List<String> findStudentsRelatedToCourse(String courseName)  throws SQLException;
}
