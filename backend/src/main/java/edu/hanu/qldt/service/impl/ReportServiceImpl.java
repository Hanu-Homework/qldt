package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.ReportDTO;
import edu.hanu.qldt.dto.response.ReportResponseDTO;
import edu.hanu.qldt.model.Course;
import edu.hanu.qldt.model.Report;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.repository.CourseRepository;
import edu.hanu.qldt.repository.ReportRepository;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the report.
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.reportRepository = reportRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Returns a Summary of semester for student. One
     * Report object is a course-mark pair. Each report made by
     * Teacher.
     *
     * @param student_id Id of the student.
     * @param year       The year when semester was.
     * @param semester   Semester when report made.
     * @return List of the reports.
     */
    @Override
    public List<Report> getSemesterResultByStudent(Long student_id, int year, int semester) {
        return reportRepository.findAll()
                .stream()
                .filter(report -> report.getStudent().getId().equals(student_id)
                                    && report.getYear() == year
                                    && report.getSemester() == semester)
                .collect(Collectors.toList());
    }

    /**
     * Returns a Report object by id.
     *
     * @param id Id of the Course.
     * @return a report object.
     */
    @Override
    public Report findById(Long id) {
        return reportRepository.getOne(id);
    }

    /**
     * Creates a new report and save into the database.
     *
     * @param reportResponseDTO Submitted DTO from web application.
     * @return a new Report object.
     * @see Report
     */
    @Override
    public Report create(ReportResponseDTO reportResponseDTO) {
        /* Finds student by id. */
        Student student = studentRepository.getOne(reportResponseDTO.getStudentId());
        /* Finds course by id. */
        Course course = courseRepository.getOne(reportResponseDTO.getCourseId());
        return reportRepository.save(new Report(
                student,
                reportResponseDTO.getYear(),
                reportResponseDTO.getSemester(),
                course,
                reportResponseDTO.getMark()
        ));
    }

    /**
     * Updates a report from database by id.
     *
     * @param id                Id of the report.
     * @param reportResponseDTO Submitted DTO from web application.
     * @return an updated report.
     * @see Report
     */
    @Override
    public Report update(Long id, ReportResponseDTO reportResponseDTO) {
        /* Finds report by id. */
        Report report = reportRepository.getOne(id);
        /* Finds course by id. */
        Course course = courseRepository.getOne(reportResponseDTO.getCourseId());
        report.setMark(reportResponseDTO.getMark());
        report.setSemester(reportResponseDTO.getSemester());
        report.setYear(reportResponseDTO.getYear());
        report.setCourse(course);

        return reportRepository.save(report);
    }

    /**
     * Deletes a report from database by id.
     *
     * @param id Id of the report.
     */
    @Override
    public void delete(Long id) {
        reportRepository.delete(reportRepository.getOne(id));
    }

    /**
     * Returns a form that contains a list of students
     * and mark field for each student.
     *
     * @param classroom_id Id of the classroom.
     * @return A form table to create reports to all student in classroom.
     */
    @Override
    public List<ReportDTO> makeReportFormToClassroom(Long classroom_id) {
        List<Student> students = getStudentFromClassroom(classroom_id);
        List<ReportDTO> reportDTOS = new ArrayList<>();
        for(Student student : students) {
            reportDTOS.add(new ReportDTO(student));
        }
        return reportDTOS;
    }

    /**
     * Creates a new reports and save into the database.
     *
     * @param reportResponseDTOS Submitted DTOs from web application.
     * @return a new Report objects.
     * @see Report
     */
    @Override
    public List<Report> createReportsToClassroom(List<ReportResponseDTO> reportResponseDTOS) {
        List<Report> reports = new ArrayList<>();
        for(ReportResponseDTO reportResponseDTO : reportResponseDTOS) {
            /* Finds student by id. */
            Student student = studentRepository.getOne(reportResponseDTO.getStudentId());
            /* Finds course by id. */
            Course course = courseRepository.getOne(reportResponseDTO.getCourseId());
            Report report = new Report(
                    student,
                    reportResponseDTO.getYear(),
                    reportResponseDTO.getSemester(),
                    course,
                    reportResponseDTO.getMark()
            );
            reports.add(report);
            reportRepository.save(report);
        }
        return reports;
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
