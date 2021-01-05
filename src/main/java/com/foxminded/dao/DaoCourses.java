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
import java.util.stream.Collectors;

public class DaoCourses {
    private final static String fileCoursesDescription = "CoursesDescription.txt";

    public void saveCoursesTable() {
        try {
            String insertionInCoursesTable = "INSERT INTO courses(course_name,course_description) VALUES (?,?)";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(insertionInCoursesTable);
            List<String> coursesDescription = Files.lines(Paths
                    .get(getFileFromResource(fileCoursesDescription).getPath()))
                    .collect(Collectors.toList());
            for(int i = 0;i < 10;++i){
                String fullInfAboutCourse = coursesDescription.get(i);
                int startOfDesc = fullInfAboutCourse.indexOf('(');
                String nameOfCourse = fullInfAboutCourse.substring(0,startOfDesc);
                String descriptionOfCourse = fullInfAboutCourse.substring(startOfDesc + 1,fullInfAboutCourse.length() - 1);
                preparedStatement.setString(1,nameOfCourse);
                preparedStatement.setString(2,descriptionOfCourse);
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
