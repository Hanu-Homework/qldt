package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.ExamDTO;
import edu.hanu.qldt.dto.response.ExamResponseDTO;
import edu.hanu.qldt.model.Course;
import edu.hanu.qldt.model.Exam;
import edu.hanu.qldt.model.ExamType;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.repository.CourseRepository;
import edu.hanu.qldt.repository.ExamRepository;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the exam.
 */
@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Returns a List of Exams that written by student on the course.
     *
     * @param student_id Id of the Student.
     * @param course_id  Id of the Course.
     * @return List of the exams.
     */
    @Override
    public List<Exam> findAllByStudent(Long student_id, Long course_id) {
        return examRepository.findAll()
                .stream()
                .filter(exam -> exam.getCourse().getId().equals(course_id))
                .filter(exam -> exam.getStudent().getId().equals(student_id))
                .collect(Collectors.toList());
    }

    /**
     * Returns an Exam object by id.
     *
     * @param id Id of the Exam.
     * @return an exam object.
     */
    @Override
    public Exam findById(Long id) {
        return examRepository.getOne(id);
    }

    /**
     * Creates a new exam and save into the database.
     *
     * @param examResponseDTO Submitted DTO from web application.
     * @return a new Exam object.
     * @see Exam
     */
    @Override
    public Exam create(ExamResponseDTO examResponseDTO) {
        /* Finds student by id. */
        Student student = studentRepository.getOne(examResponseDTO.getStudentId());
        /* Finds course by id. */
        Course course = courseRepository.getOne(examResponseDTO.getCourseId());


        if(examResponseDTO.getMark() <= 1 && examResponseDTO.getMark() >= 5) {
            return examRepository.save(new Exam(
                    examResponseDTO.getMark(),
                    examResponseDTO.getWrittenAt(),
                    ExamType.valueOf(examResponseDTO.getExamType()),
                    course,
                    student
            ));
        }
        return null;
    }

    /**
     * Updates a exam from database by id.
     *
     * @param id Id of the exam.
     * @param examResponseDTO Submitted DTO from web application.
     * @return an updated exam.
     * @see Exam
     */
    @Override
    public Exam update(Long id, ExamResponseDTO examResponseDTO) {
        /* Finds exam by id. */
        Exam exam = examRepository.getOne(id);

        if(examResponseDTO.getMark() >= 1 && examResponseDTO.getMark() <= 5) {
            exam.setMark(examResponseDTO.getMark());
            exam.setWrittenAt(examResponseDTO.getWrittenAt());
        }
        return examRepository.save(exam);
    }

    /**
     * Deletes a exam from database by id.
     *
     * @param id Id of the exam.
     */
    @Override
    public void delete(Long id) {
        examRepository.delete(examRepository.getOne(id));
    }

    /**
     * Returns a form that contains a list of students
     * and mark field for each student.
     *
     * @param classroom_id Id of the classroom.
     * @param written_at The exam date.
     * @return A form table to create exams to all student in classroom.
     */
    @Override
    public List<ExamDTO> makeExamsFormToClassroom(Long classroom_id, LocalDate written_at, String examType) {
        List<Student> students = getStudentFromClassroom(classroom_id);
        List<ExamDTO> result = new ArrayList<>();
        for (Student student : students) {
            result.add(new ExamDTO(student, written_at, examType));
        }
        return result;
    }

    /**
     * Creates a new exams and save into the database.
     *
     * @param examResponseDTOS Submitted DTOs from web application.
     * @return a new Exam objects.
     * @see Exam
     */
    @Override
    public List<Exam> createExamsFromForm(List<ExamResponseDTO> examResponseDTOS) {
        List<Exam> result = new ArrayList<>();
        /* Finds course by id. */
        Course course = courseRepository.getOne(examResponseDTOS.get(0).getCourseId());

        for(ExamResponseDTO examResponseDTO : examResponseDTOS) {
            /* Finds student by id. */
            if(examResponseDTO.getMark() >= 1 && examResponseDTO.getMark() <= 5) {
                Student student = studentRepository.getOne(examResponseDTO.getStudentId());
                Exam exam = new Exam(
                        examResponseDTO.getMark(),
                        examResponseDTO.getWrittenAt(),
                        ExamType.valueOf(examResponseDTO.getExamType()),
                        course,
                        student
                ); // Creates a new exam.
                result.add(exam);
                examRepository.save(exam); //Saves the exam.
            }

        }
        return result;
    }

    /**
     * Collect all exam type.
     *
     * @return exam types.
     */
    @Override
    public List<ExamType> getAllExamType() {
        List<ExamType> result = new ArrayList<>();
        result.add(ExamType.TOPIC_TEST);
        result.add(ExamType.TEST);
        result.add(ExamType.REPETITION);
        result.add(ExamType.HOMEWORK);
        return result;
    }

    /**
     * Returns a List of Students, who are in the class.
     *
     * @param classroom_id Id of the classroom.
     * @return List of students.
     */
    private List<Student> getStudentFromClassroom(Long classroom_id) {
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getClassroom().getId().equals(classroom_id))
                .collect(Collectors.toList());
    }
}
