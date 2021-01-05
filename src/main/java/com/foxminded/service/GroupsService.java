package com.foxminded.service;

import com.foxminded.dao.DaoGroups;
import com.foxminded.dao.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupsService {
    private DaoGroups daoGroups;
    public GroupsService(DaoGroups daoGroups){
        this.daoGroups = daoGroups;
    }
    public List<String> searchGroupsWithLessOrEqualsStudentCount(int count) throws SQLException {
        DataSource dataSource = new DataSource();
        String query = "SELECT gr.group_name FROM students st " +
                "RIGHT JOIN sql_jdbc_school.public.groups gr ON gr.group_id=st.group_id" +
                " GROUP BY gr.group_id" +
                " HAVING COUNT(st.student_id) <= ?  ";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,count);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(resultSet.getString(1));
        }
        return result;
    }

    public void saveGroupsTable() {
        daoGroups.saveGroupsTable();
    }
}
