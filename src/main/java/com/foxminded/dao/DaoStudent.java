package com.foxminded.dao;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DaoStudent {
    private final static String fileNames = "names.txt";
    private final static String fileSurnames = "surnames.txt";

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
            List<String> names = Files.lines(Paths.get(getFileFromResource(fileNames).getPath())).collect(Collectors.toList());
            List<String> surnames = Files.lines(Paths.get(getFileFromResource(fileSurnames).getPath())).collect(Collectors.toList());
            for (int i = 0; i < 200; ++i) {
                int randomNumberOfGroup = 1 + (int) (Math.random() * 10);
                int randomNumberOFStudentsInGroup = 10 + (int) (Math.random() * 21);
                String randomName = names.get((int) (Math.random() * 20));
                String randomSurname = surnames.get((int) (Math.random() * 20));
                preparedStatement.setInt(1, randomNumberOfGroup);
                preparedStatement.setString(2, randomName);
                preparedStatement.setString(3, randomSurname);
                preparedStatement.execute();
            }
        }catch (SQLException  | URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}
