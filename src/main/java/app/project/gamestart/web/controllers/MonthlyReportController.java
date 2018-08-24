package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.views.BookAllView;
import app.project.gamestart.domain.models.views.MonthlyReportView;
import app.project.gamestart.services.MonthlyReportService;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reports")
public class MonthlyReportController extends BaseController{
    private final MonthlyReportService monthlyReportService;
    private final ModelMapper modelMapper;

    @Autowired
    public MonthlyReportController(MonthlyReportService monthlyReportService, ModelMapper modelMapper) {
        this.monthlyReportService = monthlyReportService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ModelAndView all(){
        return super.view("/admin/reports",null,"Reports");
    }

    @GetMapping(value = "/api/all", produces = "application/json")
    public @ResponseBody
    Page<MonthlyReportView> myBooksList(Pageable pageable){
        return PageMapper.mapPage(this.monthlyReportService.allReports(pageable), MonthlyReportView.class, this.modelMapper);
    }
}
