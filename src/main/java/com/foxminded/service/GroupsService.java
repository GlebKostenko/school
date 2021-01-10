package com.foxminded.service;

import com.foxminded.dao.GroupsDao;
import com.foxminded.model.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupsService {
    private GroupsDao groupsDao;
    public GroupsService(GroupsDao groupsDao){
        this.groupsDao = groupsDao;
    }
    public List<Group> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        return groupsDao.searchGroupsWithLessOrEqualsStudentCount(count);
    }

    private List<Group> generateGroups() {
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
        groupsDao.saveGroupsTable(generateGroups());
    }
}
