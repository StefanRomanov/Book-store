package app.project.gamestart.services;

import app.project.gamestart.domain.models.service.MonthlyReportServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MonthlyReportService {
    Page<MonthlyReportServiceModel> allReports(Pageable pageable);
}
