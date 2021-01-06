package com.foxminded.service;

import com.foxminded.dao.Course;
import com.foxminded.dao.DaoCourses;
import com.foxminded.dao.DataSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoursesService {
    private final static String fileCoursesDescription = "CoursesDescription.txt";
    private DaoCourses daoCourses;
    public CoursesService(DaoCourses daoCourses){
        this.daoCourses = daoCourses;
    }

    public void saveCoursesTable(){
        daoCourses.saveCoursesTable();
    }

    public String showAllCourses() throws SQLException {
        return daoCourses.showAllCourses();
    }

    public List<String> findStudentsRelatedToCourse(String courseName) throws SQLException {
       return daoCourses.findStudentsRelatedToCourse(courseName);
    }

    public List<Course> generateCourses() throws URISyntaxException,IOException{
        List<Course> courses = new ArrayList<>();
        List<String> coursesDescription = Files.lines(Paths
                .get(getFileFromResource(fileCoursesDescription).getPath()))
                .collect(Collectors.toList());
        for(int i = 0;i < 10;++i){
            String fullInfAboutCourse = coursesDescription.get(i);
            int startOfDesc = fullInfAboutCourse.indexOf('(');
            String nameOfCourse = fullInfAboutCourse.substring(0,startOfDesc);
            String descriptionOfCourse = fullInfAboutCourse.substring(startOfDesc + 1,fullInfAboutCourse.length() - 1);
            courses.add(new Course(nameOfCourse,descriptionOfCourse));
        }
        return courses;
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
