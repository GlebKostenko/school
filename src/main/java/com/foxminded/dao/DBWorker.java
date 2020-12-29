package com.foxminded.dao;

import org.postgresql.Driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class DBWorker {
    private Connection connection = null;

    public DBWorker(){
        try {
            Properties prop = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file " + "config.properties"+ " not found in the classpath");
            }
            String host = prop.getProperty("host");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            connection = DriverManager.getConnection(host,username,password);
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
