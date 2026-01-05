package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.demo.repository.CompanyRepository;

@Controller
public class AdminPagesController {

    private final CompanyRepository companyRepository;

    public AdminPagesController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("studentCount", 0);
        model.addAttribute("supervisorCount", 0);
        model.addAttribute("applicationCount", 0);
        model.addAttribute("recentLogs", java.util.Collections.emptyList());
        return "Admin/admin-dashboard";
    }

    @GetMapping("/admin/assign-supervisor")
    public String assignSupervisor() {
        return "Admin/assign-supervisor";
    }

    @GetMapping("/admin/approvals")
    public String approvals() {
        return "Admin/approvals";
    }

    @GetMapping("/admin/companies")
    public String companies(Model model) {
        model.addAttribute("companies", companyRepository.findAll()); // ✅ load from DB
        return "Admin/companies";
    }
}
