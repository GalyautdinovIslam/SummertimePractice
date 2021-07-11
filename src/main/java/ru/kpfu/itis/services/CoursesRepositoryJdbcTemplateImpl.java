package ru.kpfu.itis.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.models.Course;
import ru.kpfu.itis.models.Student;
import ru.kpfu.itis.models.Teacher;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.*;

public class CoursesRepositoryJdbcTemplateImpl implements CoursesRepository {

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from course c left join teacher t on c.c_teacher_id = t.t_id left join course_student cs on c.c_id = cs.course_id left join student s on cs.student_id = s.s_id where c.c_id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_NAME = "select * from course c left join teacher t on c.c_teacher_id = t.t_id left join course_student cs on c.c_id = cs.course_id left join student s on cs.student_id = s.s_id where c.c_name = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "select * from course c left join teacher t on c.c_teacher_id = t.t_id left join course_student cs on c.c_id = cs.course_id left join student s on cs.student_id = s.s_id order by c.c_id";

    //language=SQL
    private static final String SQL_SAVE_COURSE = "insert into course(c_name, c_date_start, c_date_end, c_teacher_id) values (?,?,?,?)";

    //language=SQL
    private static final String SQL_SAVE_RELATIONS = "insert into course_student(course_id, student_id) values (?,?)";

    //language=SQL
    private static final String SQL_DELETE_COURSE = "delete from course cascade where c_id = ?";

    //language=SQL
    private static final String SQL_UPDATE = "update course set c_name = ?, c_date_start = ?, c_date_end = ?, c_teacher_id = ? where c_id = ?";

    private final ResultSetExtractor<List<Course>> rse = resultSet -> {
        Map<Integer, Course> courses = new HashMap<>();
        Map<Integer, Teacher> teachers = new HashMap<>();
        Map<Integer, Student> students = new HashMap<>();

        while (resultSet.next()) {
            Course course;
            Teacher teacher;
            Student student;

            if (courses.containsKey(resultSet.getInt("c_id"))) {
                course = courses.get(resultSet.getInt("c_id"));
            } else {
                course = new Course(resultSet.getInt("c_id"), resultSet.getString("c_name"), resultSet.getString("c_date_start"), resultSet.getString("c_date_end"));
                courses.put(resultSet.getInt("c_id"), course);
            }

            if (teachers.containsKey(resultSet.getInt("t_id"))) {
                teacher = teachers.get(resultSet.getInt("t_id"));
            } else {
                teacher = new Teacher(resultSet.getInt("t_id"), resultSet.getString("t_first_name"), resultSet.getString("t_last_name"), resultSet.getInt("t_experience"));
                teachers.put(resultSet.getInt("t_id"), teacher);
            }

            if (students.containsKey(resultSet.getInt("s_id"))) {
                student = students.get(resultSet.getInt("s_id"));
            } else {
                student = new Student(resultSet.getInt("s_id"), resultSet.getString("s_first_name"), resultSet.getString("s_last_name"), resultSet.getString("s_group_number"));
                students.put(resultSet.getInt("s_id"), student);
            }

            course.setTeacher(teacher);
            course.getStudents().add(student);
            teacher.getCourses().add(course);
            student.getCourses().add(course);
        }
        return new ArrayList<>(courses.values());
    };

    private final JdbcTemplate jdbcTemplate;

    public CoursesRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COURSE, new String[]{"c_id"});

            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDateStart());
            preparedStatement.setString(3, course.getDateEnd());
            preparedStatement.setInt(4, course.getTeacher().getId());

            return preparedStatement;
        }, keyHolder);

        Integer id = keyHolder.getKey().intValue();
        course.setId(id);

        for (Student student : course.getStudents()) {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_RELATIONS);

                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, student.getId());

                return preparedStatement;
            });
        }
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDateStart());
            preparedStatement.setString(3, course.getDateEnd());
            preparedStatement.setInt(4, course.getTeacher().getId());
            preparedStatement.setInt(5, course.getId());

            return preparedStatement;
        });
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COURSE);

            preparedStatement.setInt(1, id);

            return preparedStatement;
        });
    }

    @Override
    public Optional<Course> findById(Integer id) {
        List<Course> list = jdbcTemplate.query(SQL_FIND_BY_ID, rse, id);
        if (list.size() > 1) throw new IllegalArgumentException("Exception in <Find By ID>");
        if (list.size() == 0) return Optional.empty();
        return Optional.of(list.get(0));
    }

    @Override
    public List<Course> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, rse, name);
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rse);
    }
}
