package com.foxminded.service;

import com.foxminded.dao.DaoGroups;
import com.foxminded.dao.DataSource;
import com.foxminded.dao.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupsService {
    private DaoGroups daoGroups;
    public GroupsService(DaoGroups daoGroups){
        this.daoGroups = daoGroups;
    }
    public List<String> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        return daoGroups.searchGroupsWithLessOrEqualsStudentCount(count);
    }

    public List<Group> GenerateGroups() {
        List<Group> groups = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < 10; ++i) {
            char firstRandomLetter = (char) (r.nextInt('z' - 'a') + 'a');
            char secondRandomLetter = (char) (r.nextInt('z' - 'a') + 'a');
            String twoRandomsLetters = Character.toString(firstRandomLetter) + Character.toString(secondRandomLetter);
            String twoRandomDigits = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10);
            String randomNameOfGroup = String.format("%s-%s", twoRandomsLetters, twoRandomDigits);
            groups.add(new Group(randomNameOfGroup));
        }
        return groups;
    }

    public void saveGroupsTable() {
        daoGroups.saveGroupsTable();
    }
}
