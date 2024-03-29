package edu.hanu.qldt.service;

import edu.hanu.qldt.dto.response.TimeTableEntityResponseDTO;
import edu.hanu.qldt.model.TimeTableEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface contains all related function definitions to the timetable.
 */
@Service
public interface TimeTableService {

    /**
     * Returns a TimeTable 2d array to student. The table size
     * is 12x5. The y diagonal represents the days and x diagonal
     * shows the lecture.
     *
     * @param id Id of the Student.
     * @return a TimeTable 2d matrix.
     */
    TimeTableEntity[][] getTimeTableByStudent(Long id);

    /**
     * Returns a TimeTable 2d array to teacher. The table size
     * is 12x5. The y diagonal represents the days and x diagonal
     * shows the lecture.
     *
     * @param id Id of the Teacher.
     * @return a TimeTable 2d matrix.
     */
    TimeTableEntity[][] getTimeTableByTeacher(Long id);

    /**
     * Returns a List of TimeTableEntity, which are connected with a course.
     * The course get by id. If the course id not valid or a list empty returns null.
     *
     * @param course_id Id of the Course.
     * @return a List of the TimeTableEntity.
     */
    List<TimeTableEntity> getTimeTableEntitiesByCourse(Long course_id);

    /**
     * Returns an TimeTableEntity Object by id.
     *
     * @param id Id of the TimeTableEntity
     * @return a TimeTableEntity object.
     */
    TimeTableEntity findById(Long id);

    /**
     * Creates a new TimeTableEntity and save into the database.
     *
     * @param timeTableEntityResponseDTO Submitted DTO from web application.
     * @return  a new TimeTableEntity object.
     * @see TimeTableEntity
     */
    TimeTableEntity create(TimeTableEntityResponseDTO timeTableEntityResponseDTO);

    /**
     * Updates a TimeTableEntity from database by id.
     *
     * @param id Id of the TimeTableEntity.
     * @param timeTableEntityResponseDTO Submitted DTO from web application.
     * @return an updated TimeTableEntity.
     * @see TimeTableEntity
     */
    TimeTableEntity update(Long id, TimeTableEntityResponseDTO timeTableEntityResponseDTO);

    /**
     * Deletes a TimeTableEntity from database by id.
     *
     * @param id Id of the TimeTableEntity.
     */
    void delete(Long id);

}
