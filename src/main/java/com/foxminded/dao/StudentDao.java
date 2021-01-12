package com.foxminded.dao;

import com.foxminded.model.StudentInf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.model.Student;

public class StudentDao {

    public void addNewStudent(String firstName, String lastName) {
        try (DataSource dataSource = new DataSource();
             PreparedStatement preparedStatement =
                     dataSource.getConnection()
                             .prepareStatement("INSERT INTO students (first_name,last_name) VALUES (?,?)"))
        {
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try (DataSource dataSource = new DataSource();
             PreparedStatement preparedStatement =
                     dataSource.getConnection()
                             .prepareStatement("INSERT INTO student_courses(student_id,course_id) VALUES (?,?)"))
        {
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteStudentById(int studentId) {
        try (DataSource dataSource = new DataSource();
             PreparedStatement preparedStatementForStudentCoursesTable =
                     dataSource.getConnection()
                             .prepareStatement("DELETE FROM student_courses WHERE student_id = ?");
             PreparedStatement preparedStatementForStudentsTable =
                     dataSource.getConnection()
                             .prepareStatement("DELETE FROM students WHERE student_id = ?"))
        {
            preparedStatementForStudentCoursesTable.setInt(1,studentId);
            preparedStatementForStudentCoursesTable.execute();
            preparedStatementForStudentsTable.setInt(1,studentId);
            preparedStatementForStudentsTable.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeStudentFromCourse(int studentId,int courseId) {
        try(DataSource dataSource = new DataSource();
            PreparedStatement preparedStatement =
                    dataSource.getConnection()
                            .prepareStatement("DELETE FROM student_courses WHERE student_id = ? AND course_id = ?"))
        {
            preparedStatement.setInt(1,studentId);
            preparedStatement.setInt(2,courseId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveStudentsTable(List<Student> students) {
        try (DataSource dataSource = new DataSource();
             PreparedStatement preparedStatement =
                     dataSource.getConnection()
                             .prepareStatement("INSERT INTO students(group_id,first_name,last_name) VALUES (?,?,?)"))
        {
            for (Student student : students) {
                preparedStatement.setInt(1, student.getGroupId());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getLastName());
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<StudentInf> showAllStudents() {
        try (DataSource dataSource = new DataSource();
             Statement statement =
                     dataSource.getConnection().createStatement())
        {
            ResultSet resultSet = statement.executeQuery("SELECT student_id,first_name,last_name FROM students");
            List<StudentInf> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new StudentInf(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
