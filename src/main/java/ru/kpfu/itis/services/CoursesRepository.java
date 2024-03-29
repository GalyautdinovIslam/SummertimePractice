package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Course;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository {
    void save(Course course);

    void update(Course course);

    void delete(Integer id);

    Optional<Course> findById(Integer id);

    List<Course> findByName(String name);

    List<Course> findAll();
}
