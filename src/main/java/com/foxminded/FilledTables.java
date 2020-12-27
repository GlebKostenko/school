package com.foxminded;

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
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FilledTables {

    private final static String fileNames = "names.txt";
    private final static String fileSurnames = "surnames.txt";
    private final static String fileCoursesDescription = "CoursesDescription.txt";
    public void fillTableOfGroups(){
        try {
            String INSERT_NEW = "INSERT INTO sql_jdbc_school.public.groups VALUES (?,?)";
            DBWorker worker = new DBWorker();
            PreparedStatement preparedStatement = worker.getConnection().prepareStatement(INSERT_NEW);
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

    public void fillTableOfStudents() throws URISyntaxException, IOException {
        try {
            String INSERT_NEW = "INSERT INTO students VALUES (?,?,?,?)";
            DBWorker worker = new DBWorker();
            PreparedStatement preparedStatementForDistribution = worker.getConnection().prepareStatement(INSERT_NEW);
            List<String> names = Files.lines(Paths.get(getFileFromResource(fileNames).getPath())).collect(Collectors.toList());
            List<String> surnames = Files.lines(Paths.get(getFileFromResource(fileSurnames).getPath())).collect(Collectors.toList());
            for (int i = 0; i < 200; ++i) {
                int randomNumberOfGroup = 1 + (int) (Math.random() * 10);
                int randomNumberOFStudentsInGroup = 10 + (int) (Math.random() * 21);
                String randomName = names.get((int) (Math.random() * 20));
                String randomSurname = surnames.get((int) (Math.random() * 20));
                preparedStatementForDistribution.setInt(1, i + 1);
                preparedStatementForDistribution.setInt(2, randomNumberOfGroup);
                preparedStatementForDistribution.setString(3, randomName);
                preparedStatementForDistribution.setString(4, randomSurname);
                preparedStatementForDistribution.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void fillTableOfCourses(){
        try {
            String INSERT_NEW = "INSERT INTO courses VALUES (?,?,?)";
            DBWorker worker = new DBWorker();
            PreparedStatement preparedStatementForCreatingNewCourse =
                    worker.getConnection().prepareStatement(INSERT_NEW);
            List<String> coursesDescription = Files.lines(Paths
                    .get(getFileFromResource(fileCoursesDescription).getPath()))
                    .collect(Collectors.toList());
            for(int i = 0;i < 10;++i){
                String fullInfAboutCourse = coursesDescription.get(i);
                int startOfDesc = fullInfAboutCourse.indexOf('(');
                String nameOfCourse = fullInfAboutCourse.substring(0,startOfDesc);
                String descriptionOfCourse = fullInfAboutCourse.substring(startOfDesc + 1,fullInfAboutCourse.length() - 1);
                preparedStatementForCreatingNewCourse.setInt(1,i+1);
                preparedStatementForCreatingNewCourse.setString(2,nameOfCourse);
                preparedStatementForCreatingNewCourse.setString(3,descriptionOfCourse);
                preparedStatementForCreatingNewCourse.execute();
            }
        }catch (SQLException  | URISyntaxException | IOException e){
            e.printStackTrace();
        }

    }

    public void fillTableOfStudentsCourses(){
        DBWorker worker = new DBWorker();
        String query = "SELECT * FROM students";
        try {
            String INSERT_NEW = "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)";
            PreparedStatement preparedStatementForCreatingNewCourse =
                    worker.getConnection().prepareStatement(INSERT_NEW);
            Statement statement = worker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int numberOfCoursesForStudent = 1 + (int) (Math.random() * 3);
                int[] numbersOfCoursesThatUsersAlreadyHas = new int[3];
                for(int j = 0;j < numberOfCoursesForStudent;++j){
                    int randomNumberOfCourse = 1  + (int)(Math.random() * 10);
                    boolean contains = IntStream.of(numberOfCoursesForStudent).anyMatch(x -> x == randomNumberOfCourse);
                    if(contains){
                        int newRandomNumberOfCourse = 1  + (int)(Math.random() * 10);
                        preparedStatementForCreatingNewCourse.setInt(1,resultSet.getInt("student_id"));
                        preparedStatementForCreatingNewCourse.setInt(2,newRandomNumberOfCourse);
                        preparedStatementForCreatingNewCourse.execute();
                        if(numbersOfCoursesThatUsersAlreadyHas.length == 0){
                            numbersOfCoursesThatUsersAlreadyHas[0] = newRandomNumberOfCourse;
                        }
                        else{
                            numbersOfCoursesThatUsersAlreadyHas[1] = newRandomNumberOfCourse;
                        }
                    }else {
                        preparedStatementForCreatingNewCourse.setInt(1, resultSet.getInt("student_id"));
                        preparedStatementForCreatingNewCourse.setInt(2, randomNumberOfCourse);
                        preparedStatementForCreatingNewCourse.execute();
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

}
