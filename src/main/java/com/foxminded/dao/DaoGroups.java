package com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DaoGroups {
    public void saveGroupsTable() {
        try {
            String insertionInaGroupsTable = "INSERT INTO TEST.PUBLIC.GROUPS (group_name) VALUES (?)";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertionInaGroupsTable);
            Random r = new Random();
            for(int i = 0; i < 10; ++i) {
                char firstRandomLetter = (char) (r.nextInt('z' - 'a') + 'a');
                char secondRandomLetter = (char) (r.nextInt('z' - 'a') + 'a');
                String twoRandomsLetters = Character.toString(firstRandomLetter) + Character.toString(secondRandomLetter);
                String twoRandomDigits = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10);
                String randomNameOfGroup = String.format("%s-%s", twoRandomsLetters, twoRandomDigits);
                preparedStatement.setString(1, randomNameOfGroup);
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
