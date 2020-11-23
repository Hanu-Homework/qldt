package edu.hanu.qldt.repository.archive;

import edu.hanu.qldt.model.archive.ArchiveReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveReportRepository extends JpaRepository<ArchiveReport, Long> {
}
