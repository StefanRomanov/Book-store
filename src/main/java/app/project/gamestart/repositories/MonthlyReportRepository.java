package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.MonthlyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyReportRepository extends JpaRepository<MonthlyReport,String> {
    Page<MonthlyReport> findAll(Pageable pageable);
}
