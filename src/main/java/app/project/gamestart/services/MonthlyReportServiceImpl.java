package app.project.gamestart.services;

import app.project.gamestart.domain.entities.MonthlyReport;
import app.project.gamestart.domain.entities.Sale;
import app.project.gamestart.domain.models.service.MonthlyReportServiceModel;
import app.project.gamestart.repositories.MonthlyReportRepository;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonthlyReportServiceImpl implements MonthlyReportService {

    private final MonthlyReportRepository monthlyReportRepository;
    private final SaleService saleService;
    private final ModelMapper modelMapper;

    @Autowired
    public MonthlyReportServiceImpl(MonthlyReportRepository monthlyReportRepository, SaleService saleService, ModelMapper modelMapper) {
        this.monthlyReportRepository = monthlyReportRepository;
        this.saleService = saleService;
        this.modelMapper = modelMapper;
    }


    @Scheduled(cron="0 30 13 25 * ?")
    public void generateReports(){
        LocalDate startDate = LocalDate.now().minusMonths(1);
        List<Sale> sales = this.saleService.allSalesAfterDate(startDate);

        MonthlyReport monthlyReport = new MonthlyReport();

        monthlyReport.setDate(LocalDate.now());
        monthlyReport.setTotalBooksSold(sales.size());
        monthlyReport.setTotalEarnings(sales.stream().map(s -> s.getBook().getPrice()).reduce(BigDecimal.ZERO,BigDecimal::add));
        monthlyReport.setTotalBuyers(sales.stream().map(s -> s.getCustomer().getId()).distinct().collect(Collectors.toList()).size());

        this.monthlyReportRepository.saveAndFlush(monthlyReport);
    }

    @Override
    public Page<MonthlyReportServiceModel> allReports(Pageable pageable){
        return PageMapper.mapPage(this.monthlyReportRepository.findAll(pageable),MonthlyReportServiceModel.class,this.modelMapper);
    }
}
