package edu.hanu.qldt.repository;

import edu.hanu.qldt.model.Remark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Long> {
}
