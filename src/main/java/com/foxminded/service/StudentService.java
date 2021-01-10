package com.foxminded.service;

import com.foxminded.dao.StudentDao;
import com.foxminded.model.Student;
import com.foxminded.model.StudentInf;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private final static String fileNames = "names.txt";
    private final static String fileSurnames = "surnames.txt";
    private StudentDao studentDao;

    public StudentService(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    public void saveStudentsTable() throws URISyntaxException,IOException{
        studentDao.saveStudentsTable(generateStudents());
    }

    public void addNewStudent(String firstName, String lastName){
        studentDao.addNewStudent(firstName,lastName);
    }

    public void addStudentToCourse(int studentId, int courseId){
        studentDao.addStudentToCourse(studentId,courseId);
    }

    public void deleteStudentById(int studentId){
        studentDao.deleteStudentById(studentId);
    }

    public String removeStudentFromCourse(int studentId){
        return studentDao.removeStudentFromCourse(studentId);
    }

    public List<StudentInf> showAllStudents() throws SQLException {
        return studentDao.showAllStudents();
    }

    private List<Student> generateStudents() throws URISyntaxException,IOException{
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
