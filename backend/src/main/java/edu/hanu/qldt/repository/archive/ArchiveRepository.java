package edu.hanu.qldt.repository.archive;

import edu.hanu.qldt.model.archive.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {
}
