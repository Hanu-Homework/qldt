package edu.hanu.qldt.repository;

import edu.hanu.qldt.model.TeacherPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherPreferenceRepository extends JpaRepository<TeacherPreference, Long> {

    @Modifying
    @Query(value = "UPDATE teacher_preferences SET homework_weight = :homework, repetition_weight = :repetition, test_weight = :test, topic_test_weight = :topic_test  WHERE teacher_id = :teacher_id", nativeQuery = true)
    @Transactional
    void setPreferences(@Param("teacher_id") Long teacher_id,
                        @Param("test") double test,
                        @Param("topic_test") double topicTest,
                        @Param("repetition") double repetition,
                        @Param("homework") double homework);
}
