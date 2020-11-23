package edu.hanu.qldt.repository.user;

import edu.hanu.qldt.model.user.group.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Transactional
    void deleteById(Long id);
}
