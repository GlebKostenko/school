package com.foxminded.dao;

import com.foxminded.model.StudentInf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.model.Student;

public class StudentDao {
    private DataSource dataSource;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatementForStudentCoursesTable;
    private PreparedStatement preparedStatementForStudentsTable;
    private Statement statement;

    public void addNewStudent(String firstName, String lastName) {
        try {
            String insertionInStudentsTable = "INSERT INTO students (first_name,last_name) VALUES (?,?)";
            dataSource = new DataSource();
            preparedStatement = dataSource.getConnection().prepareStatement(insertionInStudentsTable);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                dataSource.closeConnection();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try {
            String insertionInStudentCoursesTable = "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)";
            dataSource = new DataSource();
            preparedStatement = dataSource.getConnection().prepareStatement(insertionInStudentCoursesTable);
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                dataSource.closeConnection();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void deleteStudentById(int studentId) {
        try {
            String deletionInStudentsTable = "DELETE FROM students WHERE student_id = ?";
            String deletionStudentCoursesTable = "DELETE FROM student_courses WHERE student_id = ?";
            dataSource = new DataSource();
            preparedStatementForStudentCoursesTable =
                    dataSource.getConnection().prepareStatement(deletionStudentCoursesTable);
            preparedStatementForStudentCoursesTable.setInt(1,studentId);
            preparedStatementForStudentCoursesTable.execute();
            preparedStatementForStudentsTable =
                    dataSource.getConnection().prepareStatement(deletionInStudentsTable);
            preparedStatementForStudentsTable.setInt(1,studentId);
            preparedStatementForStudentsTable.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                dataSource.closeConnection();
                if (preparedStatementForStudentCoursesTable != null && preparedStatementForStudentsTable != null) {
                    preparedStatementForStudentCoursesTable.close();
                    preparedStatementForStudentsTable.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void removeStudentFromCourse(int studentId,int courseId) {
        try {
            dataSource = new DataSource();
            String deletionFromStudentCourses = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";
            preparedStatement = dataSource.getConnection().prepareStatement(deletionFromStudentCourses);
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                dataSource.closeConnection();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void saveStudentsTable(List<Student> students) {
        try {
            String insertionInStudentsTable = "INSERT INTO students(group_id,first_name,last_name) VALUES (?,?,?)";
            dataSource = new DataSource();
            preparedStatement = dataSource.getConnection().prepareStatement(insertionInStudentsTable);
            for (Student student : students) {
                preparedStatement.setInt(1, student.getGroupId());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getLastName());
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                dataSource.closeConnection();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public List<StudentInf> showAllStudents() throws SQLException {
        dataSource = new DataSource();
        statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT student_id,first_name,last_name FROM students");
        List<StudentInf> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(new StudentInf(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3)));
        }
        dataSource.closeConnection();
        return result;
    }

}
