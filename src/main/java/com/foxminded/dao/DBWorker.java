package com.foxminded.dao;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class DBWorker {
    private final String HOST = "jdbc:postgresql://localhost:5432/sql_jdbc_school";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "root";
    private Connection connection = null;

    public DBWorker(){
        try {
            connection = DriverManager.getConnection(HOST,USERNAME,PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
