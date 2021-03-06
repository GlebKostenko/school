package com.foxminded.dao;

import com.foxminded.model.Course;
import com.foxminded.model.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CoursesDao {
    public void saveCoursesTable(List<Course> courses) {
        try (DataSource dataSource = new DataSource();
             PreparedStatement preparedStatement =
                     dataSource.getConnection()
                             .prepareStatement("INSERT INTO courses(course_name,course_description) VALUES (?,?)"))
        {

            for(Course course : courses){
                preparedStatement.setString(1,course.getCourseName());
                preparedStatement.setString(2,course.getCourseDescription());
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public List<Course> showAllCourses() {
        try (DataSource dataSource = new DataSource();
             Statement statement =
                     dataSource.getConnection().createStatement())
        {
            ResultSet resultSet = statement.executeQuery("SELECT course_name,course_description FROM courses");
            List<Course> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new Course(resultSet.getString(1), resultSet.getString(2)));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> findStudentsRelatedToCourse(int courseId) {
        try (DataSource dataSource = new DataSource();
             PreparedStatement preparedStatement =
                     dataSource.getConnection()
                             .prepareStatement("SELECT st.group_id,st.first_name,st.last_name FROM student_courses sc " +
                                     "LEFT JOIN students st ON st.student_id = sc.student_id " +
                                     "LEFT JOIN courses c ON c.course_id = sc.course_id WHERE c.course_id = ?"))
        {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Student> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAll() throws SQLException{
        DataSource dataSource = new DataSource();
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DELETE FROM student_courses");
        statement.execute("DELETE FROM courses");
    }
}
