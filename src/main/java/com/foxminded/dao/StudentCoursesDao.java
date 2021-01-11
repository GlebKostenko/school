package com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.IntStream;

public class StudentCoursesDao {
    private DataSource dataSource;
    private PreparedStatement preparedStatement;
    private Statement statement;

    public void saveStudentCoursesTable() {
        dataSource = new DataSource();
        String selectStudentIdFromStudents = "SELECT student_id FROM students";
        try {
            preparedStatement =
                    dataSource.getConnection().prepareStatement( "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)");
            statement = dataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(selectStudentIdFromStudents);
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
        }finally {
            try {
                dataSource.closeConnection();
                if (preparedStatement != null && statement != null) {
                    preparedStatement.close();
                    statement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
