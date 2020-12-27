package com.foxminded;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Deleter {
    public void deleteStudentById(int studentId){
        try {
            String queryForStudentsTable = "DELETE FROM students WHERE student_id = ?";
            String queryForStudentCoursesTable = "DELETE FROM student_courses WHERE student_id = ?";
            DBWorker dataSource = new DBWorker();
            PreparedStatement preparedStatementForStudentCoursesTable =
                    dataSource.getConnection().prepareStatement(queryForStudentCoursesTable);
            preparedStatementForStudentCoursesTable.setInt(1,studentId);
            preparedStatementForStudentCoursesTable.execute();
            PreparedStatement preparedStatementForStudentsTable =
                    dataSource.getConnection().prepareStatement(queryForStudentsTable);
            preparedStatementForStudentsTable.setInt(1,studentId);
            preparedStatementForStudentsTable.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void removeStudentFromCourse(int studentId){
        try {
            String query = "SELECT course_id FROM courses WHERE student_id = ?";
            DBWorker dataSource = new DBWorker();
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int courseId = resultSet.getInt(1);
            String queryForDelete = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";
            PreparedStatement preparedStatement1 = dataSource.getConnection().prepareStatement(queryForDelete);
            preparedStatement1.setInt(studentId,courseId);
            preparedStatement1.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
