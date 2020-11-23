package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.model.Classroom;
import edu.hanu.qldt.model.Report;
import edu.hanu.qldt.model.archive.Archive;
import edu.hanu.qldt.model.archive.ArchiveReport;
import edu.hanu.qldt.model.user.Authority;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.repository.*;
import edu.hanu.qldt.repository.archive.ArchiveReportRepository;
import edu.hanu.qldt.repository.archive.ArchiveRepository;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.repository.user.TeacherRepository;
import edu.hanu.qldt.repository.user.UserRepository;
import edu.hanu.qldt.service.AdminService;
import edu.hanu.qldt.service.auth.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the admin.
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final ReportRepository reportRepository;
    private final TimeTableRepository timeTableRepository;
    private final AttendanceRepository attendanceRepository;
    private final ArchiveReportRepository archiveReportRepository;
    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;
    final TeacherRepository teacherRepository;
    private final AuthorityService authService;
    private final RemarkRepository remarkRepository;

    @Autowired
    public AdminServiceImpl(AttendanceRepository attendanceRepository, ClassroomRepository classroomRepository, RemarkRepository remarkRepository, StudentRepository studentRepository, ExamRepository examRepository, ReportRepository reportRepository, TimeTableRepository timeTableRepository, ArchiveReportRepository archiveReportRepository, ArchiveRepository archiveRepository, UserRepository userRepository, TeacherRepository teacherRepository, AuthorityService authService) {
        this.attendanceRepository = attendanceRepository;
        this.classroomRepository = classroomRepository;
        this.remarkRepository = remarkRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
        this.reportRepository = reportRepository;
        this.timeTableRepository = timeTableRepository;
        this.archiveReportRepository = archiveReportRepository;
        this.archiveRepository = archiveRepository;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.authService = authService;
    }

    /**
     * When the school year ends, the admin able to update the
     * entire database for new year and clear out the previous data.
     * +!+ WARNING +!+ THIS FUNCTION DOESN'T CREATE ANY BACKUP. +!+ WARNING +!+
     */
    @Override
    public void newYear() {
        attendanceRepository.deleteAll();
        timeTableRepository.deleteAll();
        reportRepository.deleteAll();
        examRepository.deleteAll();
        remarkRepository.deleteAll();
        for(Classroom classroom: classroomRepository.findAll()) {
            int new_year = classroom.getYear() + 1;
            classroom.setYear(new_year);
            classroomRepository.save(classroom);
        }
    }

    /**
     * Creates an archive file, that contains all student reports.
     *
     */
    @Override
    public String createArchive() {
        for(Student student: studentRepository.findAll()) {
            saveReportsByStudent(student.getId(), new Archive(
                    student.getStudent().getUsername(),
                    student.getStudent().getFullName(),
                    student.getDateOfBirth()
            ));
        }
        return "Archived";
    }

    /**
     * Returns a List of Archive Report.
     *
     * @return a list of archive report.
     */
    @Override
    public List<Archive> getArchive() {
        return archiveRepository.findAll();
    }

    /**
     * Returns an Archive  by student id.
     *
     * @param id Id of the archive;
     * @return a list of archive report.
     */
    @Override
    public Archive getArchiveById(Long id) {
        return archiveRepository.getOne(id);
    }

    /**
     * If, the class finished the school, this function delete the class
     * and also all student from the class.
     *
     * @param classroom_id Id of the classroom.
     */
    @Override
    public void finished(Long classroom_id) {
        List<Student> students = getStudentsFromClassroom(classroom_id);
        for(Student student: students) {
            studentRepository.delete(student);
            userRepository.delete(student.getStudent());
            deleteClassroomById(classroom_id);
        }
        classroomRepository.delete(classroomRepository.getOne(classroom_id));
    }

    /**
     * This function finds all report by student and returns with a list of
     * ArchiveReport. !IMPORTANT! The collected reports are from 2nd Semester.
     *
     * @param student_id Id of the student.
     * @return a List of ArchiveReport.
     */
    private List<ArchiveReport> saveReportsByStudent(Long student_id, Archive archive) {
        List<ArchiveReport> result = new ArrayList<>();
        for(Report report: reportRepository.findAll()) {
            if(report.getStudent().getId().equals(student_id) && report.getSemester() == 2) {
                ArchiveReport archiveReport = new ArchiveReport(
                        report.getCourse().getTitle(),
                        report.getYear(),
                        report.getMark(),
                        archiveRepository.save(archive)
                );
                for(ArchiveReport archiver: archiveReportRepository.findAll()) {
                    if(!archiver.equals(archiveReport)) {
                        result.add(archiveReport);
                        archiveReportRepository.save(archiveReport);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns a List of Students, who are in the class.
     *
     * @param id Id of the classroom.
     * @return List of students.
     */
    private List<Student> getStudentsFromClassroom(Long id) {
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getClassroom().getId().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * Deletes a classroom from database by id.
     *
     * @param id Id of the classroom.
     */
    private void deleteClassroomById(Long id) {
        Classroom classroom = classroomRepository.getOne(id);
        List<Authority> authorities = authService.findByName("ROLE_TEACHER");
        classroom.getHeadTeacher().getTeacher().setAuthorities(authorities);
        classroomRepository.delete(classroomRepository.getOne(id));
    }

}
