package edu.hanu.qldt.service.impl;

import edu.hanu.qldt.dto.AttendanceDTO;
import edu.hanu.qldt.dto.response.AttendanceResponseDTO;
import edu.hanu.qldt.model.Attendance;
import edu.hanu.qldt.model.user.group.Student;
import edu.hanu.qldt.repository.AttendanceRepository;
import edu.hanu.qldt.repository.user.StudentRepository;
import edu.hanu.qldt.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains all related function implementations to the attendance.
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private StudentRepository studentRepository;

    /**
     * Returns a form that contains a list of students
     * and boolean field for each student.
     *
     * @param classroom_id Id of the classroom.
     * @return A form table to collect the missing students.
     */
    @Override
    public List<AttendanceDTO> makeAttendanceFormToClassroom(Long classroom_id) {
        List<AttendanceDTO> result = new ArrayList<>();
        for (Student student : getAllStudentByClassroom(classroom_id)) {
            result.add(new AttendanceDTO(student));
        }
        return result;
    }

    /**
     * Creates new attendances for the missing students.
     *
     * @param attendanceResponseDTOS Submitted DTOs from web application.
     * @return List of Attendances.
     * @see Attendance
     */
    @Override
    public List<Attendance> create(List<AttendanceResponseDTO> attendanceResponseDTOS) {
        List<Attendance> result = new ArrayList<>();
        for(AttendanceResponseDTO attendanceResponseDTO: attendanceResponseDTOS) {
            if(attendanceResponseDTO.isMiss()) {
                /* Finds student by id. */
                Student student = studentRepository.getOne(attendanceResponseDTO.getStudentId());
                Attendance attendance = new Attendance(
                        student,
                        attendanceResponseDTO.getLesson(),
                        attendanceResponseDTO.getDateOfMiss()
                );
                result.add(attendance);
                attendanceRepository.save(attendance);
            }
        }
        return result;
    }

    /**
     * Deletes an attendances by id.
     *
     * @param id Id of the Attendance.
     */
    @Override
    public void delete(Long id) {
        Attendance attendance = attendanceRepository.getOne(id);
        attendanceRepository.delete(attendance);
    }

    /**
     * Verifies a missed lecture by id.
     *
     * @param id Id of the Attendance.
     */
    @Override
    public void verify(Long id) {
        Attendance attendance = attendanceRepository.getOne(id);
        attendance.setVerified(true);
        attendanceRepository.save(attendance);
    }

    /**
     * Returns a List of non verified attendances by classroom.
     *
     * @param classroom_id Id of the Classroom.
     * @return a list of attendances.
     */
    @Override
    public List<Attendance> getAllAttendancesByClassroom(Long classroom_id) {
        return attendanceRepository
                .findAll()
                .stream()
                .filter(attendance -> attendance.getStudent().getClassroom().getId().equals(classroom_id))
                .sorted(Comparator.comparing(Attendance::getDateOfMiss).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Returns a List of Attendances by student id.
     *
     * @param student_id Id of the Student.
     * @return a list of the attendances.
     */
    @Override
    public List<Attendance> getAllByStudent(Long student_id) {
        return attendanceRepository
                .findAll()
                .stream()
                .filter(attendance -> attendance.getStudent().getId().equals(student_id) && !attendance.isVerified())
                .sorted(Comparator.comparing(Attendance::getDateOfMiss).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Returns a List of Students, who are in the class.
     *
     * @param classroom_id Id of the classroom.
     * @return List of student.
     */
    private List<Student> getAllStudentByClassroom(Long classroom_id) {
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getClassroom().getId().equals(classroom_id))
                .collect(Collectors.toList());
    }
}
