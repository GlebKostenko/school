package com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.IntStream;

public class DaoStudentCourses {
    public void saveStudentCoursesTable() {
        DataSource dataSource = new DataSource();
        String selectAllFromStudents = "SELECT * FROM students";
        try {
            String query1 = "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)";
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(query1);
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllFromStudents);
            while(resultSet.next()){
                int numberOfCoursesForStudent = 1 + (int) (Math.random() * 3);
                int[] numbersOfCoursesThatUsersAlreadyHas = new int[3];
                for(int j = 0;j < numberOfCoursesForStudent;++j){
                    int randomNumberOfCourse = 1  + (int)(Math.random() * 10);
                    boolean contains = IntStream.of(numberOfCoursesForStudent).anyMatch(x -> x == randomNumberOfCourse);
                    if(contains){
                        int newRandomNumberOfCourse = 1  + (int)(Math.random() * 10);
                        preparedStatement.setInt(1,resultSet.getInt("student_id"));
                        preparedStatement.setInt(2,newRandomNumberOfCourse);
                        preparedStatement.execute();
                        if(numbersOfCoursesThatUsersAlreadyHas.length == 0){
                            numbersOfCoursesThatUsersAlreadyHas[0] = newRandomNumberOfCourse;
                        }
                        else{
                            numbersOfCoursesThatUsersAlreadyHas[1] = newRandomNumberOfCourse;
                        }
                    }else {
                        preparedStatement.setInt(1, resultSet.getInt("student_id"));
                        preparedStatement.setInt(2, randomNumberOfCourse);
                        preparedStatement.execute();
                        if(numbersOfCoursesThatUsersAlreadyHas.length == 0){
                            numbersOfCoursesThatUsersAlreadyHas[0] = randomNumberOfCourse;
                        }
                        else{
                            numbersOfCoursesThatUsersAlreadyHas[1] = randomNumberOfCourse;
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
