package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.response.StudentResponseDTO;
import edu.hanu.qldt.dto.SummaryDTO;
import edu.hanu.qldt.model.*;
import edu.hanu.qldt.repository.*;
import edu.hanu.qldt.model.user.User;
import edu.hanu.qldt.model.user.group.Gender;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.repository.user.UserRepository;
import edu.hanu.qldt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the student.
 */
@Service
public class StudentServiceImp implements StudentService {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final ClassroomRepository classroomRepository;

    private final CourseRepository courseRepository;

    private final ExamRepository examRepository;

    private final ReportRepository reportRepository;

    private final AttendanceRepository attendanceRepository;

    private final TeacherPreferenceRepository teacherPreferenceRepository;

    @Autowired
    public StudentServiceImp(StudentRepository studentRepository, UserRepository userRepository, ClassroomRepository classroomRepository, CourseRepository courseRepository, ExamRepository examRepository, ReportRepository reportRepository, AttendanceRepository attendanceRepository, TeacherPreferenceRepository teacherPreferenceRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.courseRepository = courseRepository;
        this.examRepository = examRepository;
        this.reportRepository = reportRepository;
        this.attendanceRepository = attendanceRepository;
        this.teacherPreferenceRepository = teacherPreferenceRepository;
    }

    /**
     * Returns a List of Students.
     *
     * @return students from database.
     */
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * Returns a Students object by id, if classroom exist
     * or returns a null value.
     *
     * @param id Id of the student.
     * @return a student object by id.
     * @see Student
     */
    @Override
    public Student findById(Long id) {
        return studentRepository
                .findById(id).orElse(null);
    }

    /**
     * Returns a Students object by username, if classroom exist
     * or returns a null value.
     *
     * @param user_id Id of the student user.
     * @return a student object by user id.
     * @see Student
     */
    @Override
    public Student findByUserId(Long user_id) {
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getStudent()
                        .getId().equals(user_id))
                .findAny()
                .orElse(null);
    }

    /**
     * Creates a new student and save into the database.
     *
     * @param studentResponseDTO Submitted DTO from web application.
     * @return  a new Student object.
     * @see Student
     */
    @Override
    public Student create(StudentResponseDTO studentResponseDTO) {
        User user = userRepository.findByUsername(studentResponseDTO.getUsername());
        Classroom classroom = classroomRepository.getOne(studentResponseDTO.getClassroom_id());
        Student student = new Student();

        student.setAddress(studentResponseDTO.getAddress());
        student.setClassroom(classroom);
        student.setDateOfBirth(studentResponseDTO.getDateOfBirth());
        student.setGender(Gender.valueOf(studentResponseDTO.getGender()));
        student.setEducationId(studentResponseDTO.getEducationId());
        student.setHealthCareId(studentResponseDTO.getHealthCareId());
        student.setStart_year(studentResponseDTO.getStart_year());
        student.setParent1Name(studentResponseDTO.getParent1Name());
        student.setParent2Name(studentResponseDTO.getParent2Name());
        student.setFatherPhone(studentResponseDTO.getParent1Phone());
        student.setMotherPhone(studentResponseDTO.getParent2Phone());
        student.setStudent(user);
        studentRepository.save(student);
        return student;
    }

    /**
     * Updates a student from database by id.
     *
     * @param id Id of the student.
     * @param studentResponseDTO Submitted DTO from web application.
     * @return an updated student.
     * @see Student
     */
    @Override
    public Student update(Long id, StudentResponseDTO studentResponseDTO) {
        Classroom classroom = classroomRepository.getOne(studentResponseDTO.getClassroom_id());
        Student student = studentRepository.getOne(id);

        student.setAddress(studentResponseDTO.getAddress());
        student.setClassroom(classroom);
        student.setDateOfBirth(studentResponseDTO.getDateOfBirth());
        student.setGender(Gender.valueOf(studentResponseDTO.getGender()));
        student.setEducationId(studentResponseDTO.getEducationId());
        student.setHealthCareId(studentResponseDTO.getHealthCareId());
        student.setStart_year(studentResponseDTO.getStart_year());
        student.setParent1Name(studentResponseDTO.getParent1Name());
        student.setParent2Name(studentResponseDTO.getParent2Name());
        student.setFatherPhone(studentResponseDTO.getParent1Phone());
        student.setMotherPhone(studentResponseDTO.getParent2Phone());

        return studentRepository.save(student);
    }

    /**
     * Deletes a student from database by id.
     *
     * @param id Id of the student.
     */
    @Override
    public void delete(Long id) {
        deleteStudentData(id);
        studentRepository.deleteById(id);
    }

    /**
     * Returns a List of course-marks pairs by student id.
     *
     * @param id Id of the student.
     * @return List of results for each course.
     */
    @Override
    public List<SummaryDTO> getSummary(Long id) {
        Student student = studentRepository.getOne(id);
        List<SummaryDTO> summaryDTOList = new ArrayList<>();
        for(Course course : courseRepository.findAll()) {
            if(student.getCourses().contains(course)) {
                List<Exam> exams = student.getExams()
                        .stream()
                        .filter(exam -> exam.getCourse().getId().equals(course.getId()))
                        .collect(Collectors.toList());
                summaryDTOList.add(new SummaryDTO(course.getTitle(), exams, weightedAverage(exams, course)));
            }
        }
        return summaryDTOList;
    }

    private double weightedAverage(List<Exam> exams, Course course) {
        TeacherPreference teacherPreference = teacherPreferenceRepository.getOne(course.getTeacher().getId());
        double sum = 0;
        int examsNumber = exams.size();
        for(Exam exam: exams) {
            if(exam.getExamType().equals(ExamType.TEST)) {
                sum += exam.getMark() * teacherPreference.getTestWeight();
            }
            if(exam.getExamType().equals(ExamType.TOPIC_TEST)) {
                sum += exam.getMark() * teacherPreference.getTopicTestWeight();
            }
            if(exam.getExamType().equals(ExamType.REPETITION)) {
                sum += exam.getMark() * teacherPreference.getRepetitionWeight();
            }
            if(exam.getExamType().equals(ExamType.HOMEWORK)) {
                sum += exam.getMark() * teacherPreference.getHomeworkWeight();
            }
        }
        return sum / examsNumber;
    }

    /**
     * Deletes all data, which connected to Student.
     *
     * @param student_id Id of the student.
     */
    private void deleteStudentData(Long student_id) {
        deleteAllAttendanceByStudent(student_id);
        deleteAllExamByStudent(student_id);
        deleteAllReportByStudent(student_id);
    }

    /**
     * Deletes all exam which student wrote.
     *
     * @param student_id Id of the student.
     */
    private void deleteAllExamByStudent(Long student_id) {
        for(Exam exam: examRepository.findAll()) {
            if(exam.getStudent().getId().equals(student_id)) {
                examRepository.delete(exam);
            }
        }
    }

    /**
     * Deletes all reports which connected to student except the archived reports.
     *
     * @param student_id Id of the student.
     */
    private void deleteAllReportByStudent(Long student_id) {
        for(Report report: reportRepository.findAll()) {
            if(report.getStudent().getId().equals(student_id)) {
                reportRepository.delete(report);
            }
        }
    }
    /**
     * Deletes all attendances which connected to student.
     *
     * @param student_id Id of the student.
     */
    private void deleteAllAttendanceByStudent(Long student_id) {
        for(Attendance attendance: attendanceRepository.findAll()) {
            if(attendance.getStudent().getId().equals(student_id)) {
                attendanceRepository.delete(attendance);
            }
        }
    }
}
