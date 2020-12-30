package com.foxminded.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoCourses {
    public String showAllCourses() throws SQLException {
        DataSource dataSource = new DataSource();
        String query = "SELECT course_name FROM courses";
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getString(1) +"\n");
        }
        return result.toString();
    }
}
