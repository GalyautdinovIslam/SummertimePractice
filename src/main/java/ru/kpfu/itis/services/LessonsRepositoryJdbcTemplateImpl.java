package ru.kpfu.itis.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.models.Course;
import ru.kpfu.itis.models.Lesson;
import ru.kpfu.itis.models.Student;
import ru.kpfu.itis.models.Teacher;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.*;

public class LessonsRepositoryJdbcTemplateImpl implements LessonsRepository {

    //language=SQL
    private static final String SQL_SAVE_LESSON = "insert into lesson(l_name, l_date, l_course_id) values (?,?,?)";

    //language=SQL
    private static final String SQL_UPDATE = "update lesson set l_name = ?, l_date = ?, l_course_id = ? where l_id = ?";

    //language=SQL
    private static final String SQL_DELETE_LESSON = "delete from lesson cascade where l_id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "select * from lesson l left join course c on c.c_id = l.l_course_id left join course_student cs on c.c_id = cs.course_id left join student s on cs.student_id = s.s_id left join teacher t on c.c_teacher_id = t.t_id order by  l.l_id";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from lesson l left join course c on c.c_id = l.l_course_id left join course_student cs on c.c_id = cs.course_id left join student s on cs.student_id = s.s_id left join teacher t on c.c_teacher_id = t.t_id where l_id = ?";

    //language=SQL
    private static final String SQL_FIND_BY_COURSE = "select * from lesson l left join course c on c.c_id = l.l_course_id left join course_student cs on c.c_id = cs.course_id left join student s on cs.student_id = s.s_id left join teacher t on c.c_teacher_id = t.t_id where l_course_id = ?";

    private final ResultSetExtractor<List<Lesson>> rse = resultSet -> {
        Map<Integer, Lesson> lessons = new HashMap<>();
        Map<Integer, Course> courses = new HashMap<>();
        Map<Integer, Teacher> teachers = new HashMap<>();
        Map<Integer, Student> students = new HashMap<>();

        while (resultSet.next()) {
            Lesson lesson;
            Course course;
            Teacher teacher;
            Student student;

            if (lessons.containsKey(resultSet.getInt("l_id"))) {
                lesson = lessons.get(resultSet.getInt("l_id"));
            } else {
                lesson = new Lesson(resultSet.getInt("l_id"), resultSet.getString("l_name"), resultSet.getString("l_date"));
                lessons.put(resultSet.getInt("l_id"), lesson);
            }

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

            lesson.setCourse(course);
            course.setTeacher(teacher);
            course.getStudents().add(student);
            teacher.getCourses().add(course);
            student.getCourses().add(course);
        }

        return new ArrayList<>(lessons.values());
    };

    private final JdbcTemplate jdbcTemplate;

    public LessonsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_LESSON, new String[]{"l_id"});

            preparedStatement.setString(1, lesson.getName());
            preparedStatement.setString(2, lesson.getDate());
            preparedStatement.setInt(3, lesson.getCourse().getId());

            return preparedStatement;
        }, keyHolder);
        lesson.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, lesson.getName());
            preparedStatement.setString(2, lesson.getDate());
            preparedStatement.setInt(3, lesson.getCourse().getId());
            preparedStatement.setInt(4, lesson.getId());

            return preparedStatement;
        });
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_LESSON);

            preparedStatement.setInt(1, id);

            return preparedStatement;
        });
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        List<Lesson> list = jdbcTemplate.query(SQL_FIND_BY_ID, rse, id);
        if (list.size() > 1) throw new IllegalArgumentException("Exception in <Find By ID>");
        if (list.size() == 0) return Optional.empty();
        return Optional.of(list.get(0));
    }

    @Override
    public List<Lesson> findByCourse(Course course) {
        return jdbcTemplate.query(SQL_FIND_BY_COURSE, rse, course.getId());
    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rse);
    }
}
