package com.foxminded.service;

import com.foxminded.dao.CoursesDao;
import com.foxminded.model.Course;
import com.foxminded.model.Student;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoursesService {
    private final static String fileCoursesDescription = "CoursesDescription.txt";
    private CoursesDao coursesDao;
    public CoursesService(CoursesDao coursesDao){
        this.coursesDao = coursesDao;
    }

    public void saveCoursesTable() throws URISyntaxException,IOException{
        coursesDao.saveCoursesTable(generateCourses());
    }

    public List<Course> showAllCourses() throws SQLException {
        return coursesDao.showAllCourses();
    }

    public List<Student> findStudentsRelatedToCourse(int courseId) throws SQLException {
       return coursesDao.findStudentsRelatedToCourse(courseId);
    }

    private List<Course> generateCourses() throws URISyntaxException,IOException{
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
