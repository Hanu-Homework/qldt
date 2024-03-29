package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.response.CourseResponseDTO;
import edu.hanu.qldt.model.Course;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.model.user.group.Teacher;
import edu.hanu.qldt.repository.CourseRepository;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.repository.user.TeacherRepository;
import edu.hanu.qldt.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the course.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    /**
     * Returns a List of Courses.
     *
     * @return courses from database.
     */
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    /**
     * Returns a Course object by id, if course exist
     * or returns a null value.
     *
     * @param id Id of the course.
     * @return a course object by id.
     * @see Course
     */
    @Override
    public Course findById(Long id) {
        return courseRepository
                .findById(id)
                .orElse(null);
    }

    /**
     * Returns a List of Courses by Teacher id.
     *
     * @param teacher_id Id of the Teacher.
     * @return a list of courses.
     */
    @Override
    public List<Course> getCoursesByTeacherId(Long teacher_id) {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getTeacher().getId().equals(teacher_id))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new course and save into the database.
     *
     * @param courseResponseDTO Submitted DTO from web application.
     * @return  a new Course object.
     * @see Course
     */
    @Override
    public Course create(CourseResponseDTO courseResponseDTO) {
        /* Finds teacher by id. */
        Teacher teacher = teacherRepository.getOne(courseResponseDTO.getTeacherId());
        return courseRepository.save(new Course(
                courseResponseDTO.getTitle(),
                courseResponseDTO.getYear(),
                teacher
        ));
    }

    /**
     * Updates a course from database by id.
     *
     * @param id Id of the course.
     * @param courseResponseDTO Submitted DTO from web application.
     * @return an updated course.
     * @see Course
     */
    @Override
    public Course update(Long id, CourseResponseDTO courseResponseDTO) {
        /* Finds course by id. */
        Course course = courseRepository.getOne(id);
        /* Finds teacher by id. */
        Teacher teacher = teacherRepository.getOne(courseResponseDTO.getTeacherId());

        /* Updates the old course with a new data. */
        course.setTeacher(teacher);
        course.setTitle(course.getTitle());
        course.setYear(course.getYear());
        courseRepository.setTeacher(course.getId(), teacher.getId());

        return courseRepository.save(course);
    }

    /**
     * Deletes a course from database by id.
     *
     * @param id Id of the course.
     */
    @Override
    public void delete(Long id) {
        courseRepository.deleteFromStudentCourse(id);
        courseRepository.delete(courseRepository.getOne(id));
    }

    /**
     * Sets a course to student by ids.
     *
     * @param student_id Id of the Course.
     * @param course_id Id of the Student.
     */
    @Override
    public void setCourse(Long student_id, Long course_id) {
        /* Finds student by id. */
        Student student = studentRepository.getOne(student_id);
        /* Finds course by id. */
        Course course = courseRepository.getOne(course_id);
        if(courseRepository.courseIsAlreadyTaken(student_id, course_id) == 0) {
            course.getStudents().add(student);
            courseRepository.save(course);
        }
    }

    /**
     * Unsets a course to student by ids.
     *
     * @param student_id Id of the Student.
     * @param course_id  Id of the Course.
     */
    @Override
    public void unsetCourse(Long student_id, Long course_id) {
        courseRepository.unsetStudentFromCourse(course_id, student_id);
    }

}
