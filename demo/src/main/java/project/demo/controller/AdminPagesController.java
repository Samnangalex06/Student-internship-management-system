package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPagesController {

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("studentCount", 0);
        model.addAttribute("supervisorCount", 0);
        model.addAttribute("applicationCount", 0);
        model.addAttribute("recentLogs", java.util.Collections.emptyList());
        return "admin-dashboard";
    }

    @GetMapping("/admin/assign-supervisor")
    public String assignSupervisor() {
        return "assign-supervisor";
    }

    @GetMapping("/admin/approvals")
    public String approvals() {
        return "approvals";
    }
}
