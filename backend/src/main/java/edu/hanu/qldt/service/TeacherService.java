package edu.hanu.qldt.service;

import edu.hanu.qldt.dto.response.TeacherPreferenceResponseDTO;
import edu.hanu.qldt.dto.response.TeacherResponseDTO;
import edu.hanu.qldt.model.TeacherPreference;
import edu.hanu.qldt.model.user.group.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This interface contains all related function definitions to the teacher.
 */
@Service
public interface TeacherService {

    /**
     * Returns a List of Teachers.
     *
     * @return teachers from database.
     */
    List<Teacher> findAll();

    /**
     * Returns a Teacher object by id, if teacher exist
     * or returns a null value.
     *
     * @param id Id of the teacher.
     * @return a teacher object by id.
     * @see Teacher
     */
    Teacher findById(Long id);

    /**
     * Returns a Teacher object by username, if teacher exist
     * or returns a null value.
     *
     * @param user_id Id of the teacher user.
     * @return a teacher object by user id.
     * @see Teacher
     */
    Teacher findByUserId(Long user_id);

    /**
     * Creates a new teacher and save into the database.
     *
     * @param teacherResponseDTO Submitted DTO from web application.
     * @return  a new Teacher object.
     * @see Teacher
     */
    Teacher create(TeacherResponseDTO teacherResponseDTO);

    /**
     * Updates a teacher from database by id.
     *
     * @param id Id of the teacher.
     * @param teacherResponseDTO Submitted DTO from web application.
     * @return an updated teacher.
     * @see Teacher
     */
    Teacher update(Long id, TeacherResponseDTO teacherResponseDTO);

    /**
     * Deletes a teacher from database by id.
     *
     * @param id Id of the teacher.
     */
    void delete(Long id);

    /**
     * Sets a course to teacher by ids.
     *
     * @param teacher_id Id of the Teacher.
     * @param course_id Id of the Course.
     */
    void setCourse(Long teacher_id, Long course_id);

    /**
     * Set all weights for exam types.
     *
     * @param teacherPreferenceResponseDTO Submitted DTO from web application.
     */
    void setTeacherPreferences(TeacherPreferenceResponseDTO teacherPreferenceResponseDTO);

    /**
     * Get all preferences by teacher id.
     *
     * @param teacher_id Id of the Teacher.
     * @return List of the TeacherPreferences.
     */
    TeacherPreference getAllTeacherPreferences(Long teacher_id);
}
