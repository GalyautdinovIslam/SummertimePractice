package ru.kpfu.itis.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course {
    private Integer id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private Teacher teacher;
    private List<Student> students = new ArrayList<>();

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public Course(Integer id) {
        this.id = id;
    }

    public Course(String name, String dateStart, String dateEnd) {
        this.name = name;
        try {
            this.dateStart = formatter.parse(dateStart);
            this.dateEnd = formatter.parse(dateEnd);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Course(Integer id, String name, String dateStart, String dateEnd) {
        this.id = id;
        this.name = name;
        try {
            this.dateStart = formatter.parse(dateStart);
            this.dateEnd = formatter.parse(dateEnd);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateStart() {
        return formatter.format(dateStart);
    }

    public void setDateStart(String dateStart) {
        try {
            this.dateStart = formatter.parse(dateStart);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getDateEnd() {
        return formatter.format(dateEnd);
    }

    public void setDateEnd(String dateEnd) {
        try {
            this.dateEnd = formatter.parse(dateEnd);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateStart=" + formatter.format(dateStart) +
                ", dateEnd=" + formatter.format(dateEnd) +
                ", teacher id =" + teacher.getId() +
                ", teacher name =" + teacher.getFirstName() + " " + teacher.getLastName() +
                ", students number=" + students.size() +
                '}';
    }
}
