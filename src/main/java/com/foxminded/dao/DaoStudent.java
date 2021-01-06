package com.foxminded.dao;

import com.foxminded.service.StudentService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoStudent {

    public void addNewStudent(String firstName, String lastName) {
        try {
            String insertionInStudentsTable = "INSERT INTO students (first_name,last_name) VALUES (?,?)";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertionInStudentsTable);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try {
            String insertionInStudentCoursesTable = "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertionInStudentCoursesTable);
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteStudentById(int studentId) {
        try {
            String deletionInStudentsTable = "DELETE FROM students WHERE student_id = ?";
            String deletionStudentCoursesTable = "DELETE FROM student_courses WHERE student_id = ?";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatementForStudentCoursesTable =
                    dataSource.getConnection().prepareStatement(deletionStudentCoursesTable);
            preparedStatementForStudentCoursesTable.setInt(1,studentId);
            preparedStatementForStudentCoursesTable.execute();
            PreparedStatement preparedStatementForStudentsTable =
                    dataSource.getConnection().prepareStatement(deletionInStudentsTable);
            preparedStatementForStudentsTable.setInt(1,studentId);
            preparedStatementForStudentsTable.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String removeStudentFromCourse(int studentId) {
        try {
            String selectCourseId = "SELECT course_id FROM student_courses WHERE student_id = ?";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(selectCourseId);
            preparedStatement.setInt(1,studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int courseId = resultSet.getInt(1);
            String deletionFromStudentCourses = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";
            PreparedStatement preparedStatement1 = dataSource.getConnection().prepareStatement(deletionFromStudentCourses);
            preparedStatement1.setInt(1,studentId);
            preparedStatement1.setInt(2,courseId);
            preparedStatement1.execute();
            String selectCourseName = "SELECT course_name FROM courses WHERE course_id = ?";
            PreparedStatement preparedStatement2 = dataSource.getConnection().prepareStatement(selectCourseName);
            preparedStatement2.setInt(1,courseId);
            ResultSet resultSet1 = preparedStatement2.executeQuery();
            resultSet1.next();
            return resultSet1.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void saveStudentsTable() {
        try {
            String insertionInStudentsTable = "INSERT INTO students(group_id,first_name,last_name) VALUES (?,?,?)";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertionInStudentsTable);
            DaoStudent daoStudent = new DaoStudent();
            StudentService studentService = new StudentService(daoStudent);
            for (Student student : studentService.generateStudents()) {
                preparedStatement.setInt(1, student.getGroupId());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getLastName());
                preparedStatement.execute();
            }
        }catch (SQLException  | URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public String showAllStudents() throws SQLException {
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT student_id,first_name,last_name FROM students");
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getInt(1) +" "
                    + resultSet.getString(2) +" "
                    + resultSet.getString(3) + "\n");
        }
        return result.toString();
    }

}
