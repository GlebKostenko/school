package com.foxminded;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Searcher {
    public String searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        DBWorker dbWorker = new DBWorker();
        String query = "SELECT gr.group_name FROM students st " +
                "LEFT JOIN sql_jdbc_school.public.groups gr ON gr.group_id=st.group_id" +
                " GROUP BY gr.group_id" +
                " HAVING COUNT(st.student_id) <= ?";
        PreparedStatement preparedStatement = dbWorker.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,count);
        ResultSet resultSet = preparedStatement.executeQuery();
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getString(1) +  "\n");
        }
        return result.toString();
    }
    public String findStudentsRelatedToCourse(String courseName) throws SQLException{
        DBWorker dbWorker = new DBWorker();
        String query = "SELECT st.first_name,st.last_name FROM student_courses sc " +
                "LEFT JOIN students st ON st.student_id = sc.student_id " +
                "LEFT JOIN courses c ON c.course_id = sc.course_id WHERE c.course_name = ?";
        PreparedStatement preparedStatement = dbWorker.getConnection().prepareStatement(query);
        preparedStatement.setString(1,courseName);
        ResultSet resultSet = preparedStatement.executeQuery();
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getString(1) + " " + resultSet.getString(2) + "\n");
        }
        return result.toString();
    }
}
