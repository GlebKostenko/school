package com.foxminded;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Inserter {
    public void addNewStudent(String firstName,String lastName){
        try {
            String query = "INSERT INTO students (first_name,last_name) VALUES (?,?)";
            DBWorker dataSource = new DBWorker();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addStudentToCourse(int studentId,int courseId){
        try {
            String query = "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)";
            DBWorker dataSource = new DBWorker();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
