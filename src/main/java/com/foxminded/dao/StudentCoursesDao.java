package com.foxminded.dao;

import com.foxminded.model.Student;
import com.foxminded.model.StudentCourse;
import com.foxminded.model.StudentInf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.IntStream;

public class StudentCoursesDao {
    private DataSource dataSource;
    private PreparedStatement preparedStatement;

    public void saveStudentCoursesTable(List<StudentCourse> studentCourses) {
        try {
            dataSource = new DataSource();
            preparedStatement =
                    dataSource.getConnection().prepareStatement( "INSERT INTO student_courses(student_id,course_id) VALUES (?,?)");
            for(StudentCourse studentCourse : studentCourses){
                preparedStatement.setInt(1,studentCourse.getStudentId());
                preparedStatement.setInt(2,studentCourse.getCourseId());
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                dataSource.closeConnection();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
