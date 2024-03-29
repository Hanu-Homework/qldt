package edu.hanu.qldt.service;

import edu.hanu.qldt.dto.ClassroomCourseResultDTO;
import edu.hanu.qldt.dto.FailedStudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface contains all related function definitions to the headteacher.
 */
@Service
public interface HeadTeacherService {

    /**
     * Find all student who failed at least one subject.
     *
     * @param classroom_id Id of the class.
     * @return List of Failed Student with courses.
     */
    List<FailedStudentDTO> findFailedStudentsInClass(Long classroom_id);

    /**
     * Returns a list of ClassroomCourseResultDTO. This function create an
     * average for each course by class id.
     *
     * @param classroom_id Id of the class.
     * @return List of ClassroomCourseResultDTO by classroom id.
     */
    List<ClassroomCourseResultDTO> showResultByCourse(Long classroom_id);
}
