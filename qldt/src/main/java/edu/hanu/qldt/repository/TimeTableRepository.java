package edu.hanu.qldt.repository;

import edu.hanu.qldt.model.TimeTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTableEntity, Long> {
}
