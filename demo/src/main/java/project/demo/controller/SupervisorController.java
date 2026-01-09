package project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.demo.entity.Application;
import project.demo.entity.Company;
import project.demo.entity.Supervisor;
import project.demo.entity.User;

import project.demo.repository.CompanyRepository;
import project.demo.repository.SupervisorRepository;
import project.demo.repository.UserRepository;
import project.demo.service.ApplicationService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CompanyRepository companyRepository;

    // =========================
    // Supervisor Dashboard
    // =========================
    @GetMapping("/dashboard")
    public String supervisorDashboard(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;

        if (email == null) {
            return "redirect:/login";
        }

        User user = userRepo.findByEmailWithRoles(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Supervisor supervisor = supervisorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Supervisor record not found"));

        model.addAttribute("supervisor", supervisor);

        // Get applications supervised by this supervisor
        List<Application> apps =
                applicationService.getApplicationsBySupervisor(supervisor.getId());

        // Sort by created date (latest first)
        apps = apps.stream()
                .sorted(Comparator.comparing(
                        Application::getCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .collect(Collectors.toList());

        model.addAttribute("applicationCount", apps.size());

        String latestStatus = apps.isEmpty()
                ? "No applications"
                : apps.get(0).getStatus().name();
        model.addAttribute("latestStatus", latestStatus);

        // Company name mapping
        Map<Integer, String> companyMap = apps.stream()
                .map(Application::getCompanyId)
                .distinct()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> companyRepository.findById(id)
                                .map(Company::getName)
                                .orElse("Unknown"),
                        (a, b) -> a
                ));

        model.addAttribute("companyMap", companyMap);
        model.addAttribute("recentApplications", apps.stream().limit(5).toList());

        return "Supervisor/dashboard";
    }

    // =========================
    // Accept Application
    // =========================
    @PostMapping("/applications/{id}/accept")
    public String acceptApplication(@PathVariable Integer id) {
        applicationService.approveApplication(id);
        return "redirect:/supervisor/dashboard";
    }

    // =========================
    // Reject Application
    // =========================
    @PostMapping("/applications/{id}/reject")
    public String rejectApplication(@PathVariable Integer id) {
        applicationService.rejectApplication(id);
        return "redirect:/supervisor/dashboard";
    }
}
