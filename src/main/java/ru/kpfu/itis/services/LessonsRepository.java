package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Course;
import ru.kpfu.itis.models.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonsRepository {
    void save(Lesson lesson);

    void update(Lesson lesson);

    void delete(Integer id);

    Optional<Lesson> findById(Integer id);

    List<Lesson> findByCourse(Course course);

    List<Lesson> findAll();
}
