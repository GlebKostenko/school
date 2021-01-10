package com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.model.Group;

public class GroupsDao {
    public void saveGroupsTable(List<Group> groups) {
        try {
            String insertionInaGroupsTable = "INSERT INTO GROUPS (group_name) VALUES (?)";
            DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(insertionInaGroupsTable);
            for(Group group : groups) {
                preparedStatement.setString(1, group.getGroupName());
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Group> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        DataSource dataSource = new DataSource();
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("SELECT gr.group_name FROM students st " +
                "RIGHT JOIN groups gr ON gr.group_id=st.group_id" +
                " GROUP BY gr.group_id" +
                " HAVING COUNT(st.student_id) <= ?  ");
        preparedStatement.setInt(1,count);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Group> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(new Group(resultSet.getString(1)));
        }
        return result;
    }

}
