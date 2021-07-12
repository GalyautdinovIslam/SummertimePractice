package ru.kpfu.itis.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lesson {
    private Integer id;
    private String name;
    private Date date;
    private Course course;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("EEEE, HH:mm");

    public Lesson(Integer id) {
        this.id = id;
    }

    public Lesson(String name, String date) {
        this.name = name;
        try {
            this.date = formatter.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Lesson(Integer id, String name, String date) {
        this.id = id;
        this.name = name;
        try {
            this.date = formatter.parse(date);
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

    public String getDate() {
        return formatter.format(date);
    }

    public void setDate(String date) {
        try {
            this.date = formatter.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + formatter.format(date) +
                ", course id=" + course.getId() +
                '}';
    }
}
