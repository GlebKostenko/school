package com.foxminded.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private Connection connection = null;
    private Properties properties;
    public DataSource(){
        try {
            properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file " + "application.properties" + " not found in the classpath");
            }
        }catch ( IOException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            String host = properties.getProperty("url");
            String username = properties.getProperty("user");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(host, username, password);
            return connection;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
