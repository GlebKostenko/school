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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DaoImpl implements DaoLayer{
    private final static String fileNames = "names.txt";
    private final static String fileSurnames = "surnames.txt";
    private final static String fileCoursesDescription = "CoursesDescription.txt";

    @Override
    public void addNewStudent(String firstName, String lastName) {
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

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
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

    @Override
    public void deleteStudentById(int studentId) {
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

    @Override
    public String removeStudentFromCourse(int studentId) {
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
            return resultSet.getString(2);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(){
        saveGroupsTable();
        saveStudentsTable();
        saveCoursesTable();
        saveStudentCoursesTable();
    }

    @Override
    public void saveGroupsTable() {
        try {
            String query1 = "INSERT INTO sql_jdbc_school.public.groups VALUES (?,?)";
            DBWorker dataSource = new DBWorker();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query1);
            Random r = new Random();
            for(int i = 0; i < 10; ++i) {
                char firstRandomLetter = (char) (r.nextInt('z' - 'a') + 'a');
                char secondRandomLetter = (char) (r.nextInt('z' - 'a') + 'a');
                String twoRandomsLetters = Character.toString(firstRandomLetter) + Character.toString(secondRandomLetter);
                String twoRandomDigits = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10);
                String randomNameOfGroup = String.format("%s-%s", twoRandomsLetters, twoRandomDigits);
                preparedStatement.setInt(1, i + 1);
                preparedStatement.setString(2, randomNameOfGroup);
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveStudentsTable() {
        try {
            String query1 = "INSERT INTO students VALUES (?,?,?,?)";
            DBWorker dataSource = new DBWorker();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query1);
            List<String> names = Files.lines(Paths.get(getFileFromResource(fileNames).getPath())).collect(Collectors.toList());
            List<String> surnames = Files.lines(Paths.get(getFileFromResource(fileSurnames).getPath())).collect(Collectors.toList());
            for (int i = 0; i < 200; ++i) {
                int randomNumberOfGroup = 1 + (int) (Math.random() * 10);
                int randomNumberOFStudentsInGroup = 10 + (int) (Math.random() * 21);
                String randomName = names.get((int) (Math.random() * 20));
                String randomSurname = surnames.get((int) (Math.random() * 20));
                preparedStatement.setInt(1, i + 1);
                preparedStatement.setInt(2, randomNumberOfGroup);
                preparedStatement.setString(3, randomName);
                preparedStatement.setString(4, randomSurname);
                preparedStatement.execute();
            }
        }catch (SQLException  | URISyntaxException  | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveCoursesTable() {
        try {
            String query1 = "INSERT INTO courses VALUES (?,?,?)";
            DBWorker dataSource = new DBWorker();
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(query1);
            List<String> coursesDescription = Files.lines(Paths
                    .get(getFileFromResource(fileCoursesDescription).getPath()))
                    .collect(Collectors.toList());
            for(int i = 0;i < 10;++i){
                String fullInfAboutCourse = coursesDescription.get(i);
                int startOfDesc = fullInfAboutCourse.indexOf('(');
                String nameOfCourse = fullInfAboutCourse.substring(0,startOfDesc);
                String descriptionOfCourse = fullInfAboutCourse.substring(startOfDesc + 1,fullInfAboutCourse.length() - 1);
                preparedStatement.setInt(1,i+1);
                preparedStatement.setString(2,nameOfCourse);
                preparedStatement.setString(3,descriptionOfCourse);
                preparedStatement.execute();
            }
        }catch (SQLException  | URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveStudentCoursesTable() {
        DBWorker dataSource = new DBWorker();
        String query = "SELECT * FROM students";
        try {
            String query1 = "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)";
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(query1);
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
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

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }

    @Override
    public List<String> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        DBWorker dbWorker = new DBWorker();
        String query = "SELECT gr.group_name FROM students st " +
                "RIGHT JOIN sql_jdbc_school.public.groups gr ON gr.group_id=st.group_id" +
                " GROUP BY gr.group_id" +
                " HAVING COUNT(st.student_id) <= ?  ";
        PreparedStatement preparedStatement = dbWorker.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,count);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(resultSet.getString(1));
        }
        return result;
    }

    @Override
    public List<String> findStudentsRelatedToCourse(String courseName) throws SQLException {
        DBWorker dbWorker = new DBWorker();
        String query = "SELECT st.first_name,st.last_name FROM student_courses sc " +
                "LEFT JOIN students st ON st.student_id = sc.student_id " +
                "LEFT JOIN courses c ON c.course_id = sc.course_id WHERE c.course_name = ?";
        PreparedStatement preparedStatement = dbWorker.getConnection().prepareStatement(query);
        preparedStatement.setString(1,courseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(resultSet.getString(1) + " " + resultSet.getString(2));
        }
        return result;
    }

}
