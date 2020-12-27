package com.foxminded;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException, URISyntaxException,SQLException {
        Deleter deleter = new Deleter();
        deleter.deleteStudentById(201);
//        String query = "UPDATE students SET group_id = NULL WHERE student_id = (SELECT student_id FROM students GROUP BY group_id HAVING COUNT(student_id) < 10";
//        TablesCreator test = new TablesCreator();
//        DBWorker dbWorker = new DBWorker();
//        Searcher searcher = new Searcher();
//        System.out.println(searcher.searchGroupsWithLessOrEqualsStudentCount(50));
//        System.out.println(searcher.findStudentsRelatedToCourse("Biology"));
//        Inserter inserter = new Inserter();
//        inserter.addNewStudent("Лев","Ландау");
//        inserter.addStudentToCourse(201,3);
//        String query = "TRUNCATE TABLE students";
//        Statement statement = dbWorker.getConnection().createStatement();
//        PreparedStatement statement = dbWorker.getConnection().prepareStatement(query);
//        statement.execute();
//        test.fillTableOfGroups();
//        test.fillTableOfStudents();
//        test.fillTableOfCourses();
//        test.fillTableOfStudentsCourses();
    }
}
