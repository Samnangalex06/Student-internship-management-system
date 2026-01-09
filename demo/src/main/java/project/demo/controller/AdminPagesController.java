package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.demo.repository.ApplicationRepository;
import project.demo.repository.CompanyRepository;

@Controller
public class AdminPagesController {

    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;

    public AdminPagesController(
            CompanyRepository companyRepository,
            ApplicationRepository applicationRepository) {
        this.companyRepository = companyRepository;
        this.applicationRepository = applicationRepository;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("studentCount", 0);
        model.addAttribute("supervisorCount", 0);
        model.addAttribute("applicationCount", 0);
        model.addAttribute("recentLogs", java.util.Collections.emptyList());
        return "Admin/admin-dashboard";
    }

    // ✅ THIS MUST MATCH: templates/Admin/application.html
    @GetMapping("/admin/applications")
    public String applications(Model model) {
        model.addAttribute(
                "applications",
                applicationRepository.findAll()
        );
        return "Admin/application";
    }

    @GetMapping("/admin/approvals")
    public String approvals() {
        return "Admin/approvals";
    }

    @GetMapping("/admin/companies")
    public String companies(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        return "Admin/companies";
    }
}
