package com.foxminded.dao;

import com.foxminded.service.CoursesService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoCourses {

    public void saveCoursesTable() {
        try {
            String insertionInCoursesTable = "INSERT INTO courses(course_name,course_description) VALUES (?,?)";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement =
                    dataSource.getConnection().prepareStatement(insertionInCoursesTable);
            DaoCourses daoCourses = new DaoCourses();
            CoursesService coursesService = new CoursesService(daoCourses);
            for(Course course : coursesService.generateCourses()){
                preparedStatement.setString(1,course.getCourseName());
                preparedStatement.setString(2,course.getCourseDescription());
                preparedStatement.execute();
            }
        }catch (SQLException  | URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }



    public String showAllCourses() throws SQLException {
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT course_name FROM courses");
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getString(1) +"\n");
        }
        return result.toString();
    }

    public List<String> findStudentsRelatedToCourse(String courseName) throws SQLException {
        DataSource dataSource = new DataSource();
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("SELECT st.first_name,st.last_name FROM student_courses sc " +
                "LEFT JOIN students st ON st.student_id = sc.student_id " +
                "LEFT JOIN courses c ON c.course_id = sc.course_id WHERE c.course_name = ?");
        preparedStatement.setString(1,courseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(resultSet.getString(1) + " " + resultSet.getString(2));
        }
        return result;
    }

}
