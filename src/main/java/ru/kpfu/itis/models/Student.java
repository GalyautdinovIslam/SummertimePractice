package ru.kpfu.itis.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private String groupNumber;
    private List<Course> courses = new ArrayList<>();

    public Student(Integer id) {
        this.id = id;
    }

    public Student(String firstName, String lastName, String groupNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNumber = groupNumber;
    }

    public Student(Integer id, String firstName, String lastName, String groupNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNumber = groupNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupNumber='" + groupNumber + '\'' +
                ", courses number=" + courses.size() +
                '}';
    }
}
