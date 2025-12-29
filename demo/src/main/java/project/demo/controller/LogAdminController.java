package project.demo.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.demo.entity.InternshipLog;
import project.demo.service.InternshipLogService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LogAdminController {

    private final InternshipLogService logService;

    public LogAdminController(InternshipLogService logService) {
        this.logService = logService;
    }

    @GetMapping("/admin/logs")
    public String logs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String performedBy,
            Model model
    ) {
        List<InternshipLog> logs = logService.filter(startDate, endDate, action, performedBy);

        model.addAttribute("logs", logs);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("action", action);
        model.addAttribute("performedBy", performedBy);

        return "admin-logs";
    }
}
