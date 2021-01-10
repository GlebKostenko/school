package com.foxminded.model;

public class StudentInf {
    private int studentId;
    private String firstName;
    private String lastName;

    public StudentInf(int studentId, String firstName, String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

