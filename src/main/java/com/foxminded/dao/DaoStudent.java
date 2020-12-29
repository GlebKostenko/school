package com.foxminded.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoStudent {
    public String showAllStudents() throws SQLException {
        DBWorker dataSource = new DBWorker();
        String query = "SELECT * FROM students";
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        StringBuilder result = new StringBuilder();
        while (resultSet.next()){
            result.append(resultSet.getInt(1) +" "
                    + resultSet.getString(3) +" "
                    + resultSet.getString(4) + "\n");
        }
        return result.toString();
    }
}
