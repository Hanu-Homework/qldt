package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.response.ClassroomResponseDTO;
import edu.hanu.qldt.model.Classroom;
import edu.hanu.qldt.model.user.Authority;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.model.user.group.Teacher;
import edu.hanu.qldt.repository.ClassroomRepository;
import edu.hanu.qldt.repository.CourseRepository;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.repository.user.TeacherRepository;
import edu.hanu.qldt.service.ClassroomService;
import edu.hanu.qldt.service.auth.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the classroom.
 */
@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    final TeacherRepository teacherRepository;
    private final AuthorityService authService;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository classroomRepository, StudentRepository studentRepository, CourseRepository courseRepository, TeacherRepository teacherRepository, AuthorityService authService) {
        this.classroomRepository = classroomRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.authService = authService;
    }

    /**
     * Returns a List of Classroom.
     *
     * @return classrooms from database.
     */
    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    /**
     * Returns a Classroom object by id, if classroom exist
     * or returns a null value.
     *
     * @param id Id of the classroom.
     * @return a classroom object by id.
     * @see Classroom
     */
    @Override
    public Classroom findById(Long id) {
        return classroomRepository.findById(id).orElse(null);
    }

    /**
     * Returns a Classroom object by Headteacher Id if classroom exist
     * or returns a null value.
     *
     * @param id Id of the headteacher
     * @return a classroom object by headteacher id.
     */
    @Override
    public Classroom findByHeadteacher(Long id) {
        return classroomRepository.findAll()
                .stream()
                .filter(classroom -> classroom.getHeadTeacher().getId().equals(id))
                .findAny()
                .orElse(null);
    }

    /**
     * Creates a new classroom and save into the database.
     *
     * @param classroomResponseDTO Submitted DTO from web application.
     * @return  a new Classroom object.
     * @see Classroom
     */
    @Override
    public Classroom create(ClassroomResponseDTO classroomResponseDTO) {
        /* Finds teacher by id. */
        Teacher teacher = teacherRepository.getOne(classroomResponseDTO.getHeadTeacherId());
        Classroom classroom = new Classroom(
                classroomResponseDTO.getStartYear(),
                classroomResponseDTO.getEndYear(),
                classroomResponseDTO.getYear(),
                classroomResponseDTO.getLetter(),
                teacher
        ); // Creates a new classroom.
        /* sets back a teacher role from ROLE_TEACHER to ROLE_HEADTEACHER. */
        classroomRepository.setHeadteacherFromTeacher(teacherRepository.GetUserIdByTeacherId(teacher.getId()));
        return classroomRepository.save(classroom);
    }

    /**
     * Updates a classroom from database by id.
     *
     * @param id Id of the classroom.
     * @param classroomResponseDTO Submitted DTO from web application.
     * @return an updated classroom.
     * @see Classroom
     */
    @Override
    public Classroom update(Long id, ClassroomResponseDTO classroomResponseDTO) {
        /* Finds classroom by id. */
        Classroom classroom = classroomRepository.getOne(id);
        /* Finds teacher by id. */
        Teacher teacher = teacherRepository.getOne(classroomResponseDTO.getHeadTeacherId());

        /* Updates the old classroom with a new data. */
        classroom.setStartYear(classroomResponseDTO.getStartYear());
        classroom.setEndYear(classroomResponseDTO.getEndYear());
        classroom.setHeadTeacher(teacher);
        classroom.setLetter(classroomResponseDTO.getLetter());
        classroom.setYear(classroomResponseDTO.getYear());

        /* sets  a teacher role from ROLE_HEADTEACHER to ROLE_TEACHER. */
        classroomRepository.setTeacherFromHeadteacher(classroom.getHeadTeacher().getTeacher().getId());
        /* sets  a teacher role from ROLE_TEACHER to ROLE_HEADTEACHER. */
        classroomRepository.setHeadteacherFromTeacher(teacher.getTeacher().getId());

        return classroomRepository.save(classroom);
    }

    /**
     * Deletes a classroom from database by id.
     *
     * @param id Id of the classroom.
     */
    @Override
    public void delete(Long id) {
        Classroom classroom = classroomRepository.getOne(id);
        List<Authority> authorities = authService.findByName("ROLE_TEACHER");
        classroom.getHeadTeacher().getTeacher().setAuthorities(authorities);
        classroomRepository.delete(classroomRepository.getOne(id));
    }

    /**
     * Returns a List of Students, who are in the class.
     *
     * @param id Id of the classroom.
     * @return List of students.
     */
    @Override
    public List<Student> getStudentsFromClassroom(Long id) {
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getClassroom().getId().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * Sets a course to all student, who are in the class. This
     * method helps to update at a new year.
     *
     * @param classroom_id Id of the classroom.
     * @param course_id Id of the Course.
     */
    @Override
    public void setCourse(Long classroom_id, Long course_id) {
        List<Student> students = studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getClassroom().getId().equals(classroom_id))
                .collect(Collectors.toList());
        for(Student student : students) {
            if(courseRepository.courseIsAlreadyTaken(student.getId(), course_id) == 0) {
                classroomRepository.setCourseForClassroom(student.getId(), course_id);
            }
        }
    }

    /**
     * unsets a course to all student, who are in the class.
     *
     * @param classroom_id Id of the classroom.
     * @param course_id    Id of the Course.
     */
    @Override
    public void unsetCourse(Long classroom_id, Long course_id) {
        List<Student> students = this.getStudentsFromClassroom(classroom_id);
        for(Student student: students) {
            courseRepository.unsetStudentFromCourse(course_id, student.getId());
        }
    }


}
