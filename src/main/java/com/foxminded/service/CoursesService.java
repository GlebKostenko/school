package com.foxminded.service;

import com.foxminded.dao.DaoCourses;
import com.foxminded.dao.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesService {
    private DaoCourses daoCourses;
    public CoursesService(DaoCourses daoCourses){
        this.daoCourses = daoCourses;
    }

    public void saveCoursesTable(){
        daoCourses.saveCoursesTable();
    }

    public List<String> findStudentsRelatedToCourse(String courseName) throws SQLException {
        DataSource dataSource = new DataSource();
        String query = "SELECT st.first_name,st.last_name FROM student_courses sc " +
                "LEFT JOIN students st ON st.student_id = sc.student_id " +
                "LEFT JOIN courses c ON c.course_id = sc.course_id WHERE c.course_name = ?";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);
        preparedStatement.setString(1,courseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(resultSet.getString(1) + " " + resultSet.getString(2));
        }
        return result;
    }
}
