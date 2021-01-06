package com.foxminded.service;

import com.foxminded.dao.DaoStudent;
import com.foxminded.dao.DataSource;
import com.foxminded.dao.Student;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private final static String fileNames = "names.txt";
    private final static String fileSurnames = "surnames.txt";
    private DaoStudent daoStudent;

    public StudentService(DaoStudent daoStudent){
        this.daoStudent = daoStudent;
    }

    public void saveStudentsTable(){
        daoStudent.saveStudentsTable();
    }

    public void addNewStudent(String firstName, String lastName){
        daoStudent.addNewStudent(firstName,lastName);
    }

    public void addStudentToCourse(int studentId, int courseId){
        daoStudent.addStudentToCourse(studentId,courseId);
    }

    public void deleteStudentById(int studentId){
        daoStudent.deleteStudentById(studentId);
    }

    public String removeStudentFromCourse(int studentId){
        return daoStudent.removeStudentFromCourse(studentId);
    }

    public String showAllStudents() throws SQLException {
        return daoStudent.showAllStudents();
    }

    public List<Student> generateStudents() throws URISyntaxException,IOException{
        List<Student> students = new ArrayList<>();
        List<String> names = Files.lines(Paths.get(getFileFromResource(fileNames).getPath())).collect(Collectors.toList());
        List<String> surnames = Files.lines(Paths.get(getFileFromResource(fileSurnames).getPath())).collect(Collectors.toList());
        for (int i = 0; i < 200; ++i) {
            int randomNumberOfGroup = 1 + (int) (Math.random() * 10);
            String randomName = names.get((int) (Math.random() * 20));
            String randomSurname = surnames.get((int) (Math.random() * 20));
            students.add(new Student(randomNumberOfGroup,randomName,randomSurname));
        }
        return students;
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }

}
