package project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.demo.Model.ApplicationDTO;
import project.demo.entity.Application;
import project.demo.entity.Application.ApplicationStatus;
import project.demo.entity.Company;
import project.demo.entity.Supervisor;
import project.demo.entity.User;

import project.demo.repository.CompanyRepository;
import project.demo.repository.SupervisorRepository;
import project.demo.repository.UserRepository;
import project.demo.service.ApplicationService;

import java.nio.file.OpenOption;
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

    @Autowired
    private project.demo.service.CompanyService companyService;

    @Autowired
    private project.demo.repository.ApplicationRepository applicationRepository;

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
        model.addAttribute("recentApplications", apps.stream().limit(10).toList());

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
        return "redirect:/supervisor/dashbord";
    }

    // =========================
    // Supervisor Assign Applications
    // ========================

        @GetMapping("/approvals")
                public String approvals(Model model, @RequestParam(required = false) Integer id) {

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String email = auth != null ? auth.getName() : null;

                if (email == null) return "redirect:/login";

                User user = userRepo.findByEmailWithRoles(email).orElseThrow();
                Supervisor supervisor = supervisorRepository.findByUserId(user.getId()).orElseThrow();

                List<ApplicationDTO> pendingApps = applicationRepository.findPendingWithDetails(
                supervisor.getId(), Application.ApplicationStatus.PENDING).stream().map(a -> new ApplicationDTO(
                                a.getId(),
                                a.getStudentId(),
                                a.getStudentName(),
                                a.getCompanyId(),
                                a.getCompanyName(),
                                a.getSupervisorId(),
                                a.getTitle(),
                                a.getDescription(),
                                a.getStatus(),
                                a.getCreatedAt()
                        )).collect(Collectors.toList());

                model.addAttribute("pendingApplications", pendingApps);

                ApplicationDTO selectedApp = null;

                        if (id != null) {
                        selectedApp = pendingApps.stream()
                                .filter(a -> a.getId().equals(id))
                                .findFirst()
                                .orElse(null);
                        }
                        if (selectedApp == null && !pendingApps.isEmpty()) {
                        selectedApp = pendingApps.get(0); // default to first pending
                        if (selectedApp != null) {
                        System.out.println("🧪 Selected Application (DTO)");
                        System.out.println("ID: " + selectedApp.getId());
                        System.out.println("Student ID: " + selectedApp.getStudentId());
                        System.out.println("Student Name: " + selectedApp.getStudentName());
                        System.out.println("Company ID: " + selectedApp.getCompanyId());
                        System.out.println("Company Name: " + selectedApp.getCompanyName());
                        System.out.println("Supervisor ID: " + selectedApp.getSupervisorId());
                        System.out.println("Title: " + selectedApp.getTitle());
                        System.out.println("Description: " + selectedApp.getDescription());
                        System.out.println("Status: " + selectedApp.getStatus());
                        System.out.println("Created At: " + selectedApp.getCreatedAt());
                        } else {
                        System.out.println("No selected application found!");
                        }
                        // System.out.println(pendingApps.get(0));
                        }
                // System.out.println("Pending apps count: " + pendingApps.size());
                // pendingApps.forEach(a -> System.out.println(a.getId() + " | " + a.getStudentName()));


                model.addAttribute("application", selectedApp);

                return "Supervisor/approvals";
        }


    
        // Show pending applications page
        @GetMapping("/assign-app")
        public String showAssignApplications(Model model) {
        List<Application> applications = applicationRepository.findByStatus(Application.ApplicationStatus.PENDING);
        List<Company> companies = companyService.getAll();
        model.addAttribute("applications", applications);
        model.addAttribute("companies", companies);
        return "Supervisor/assign-app"; // Thymeleaf template name
        }

        // Handle form submission
        @PostMapping("/assign-app")
        public String assign(@RequestParam Integer applicationId,
                                @RequestParam Integer companyId) {
        applicationService.assignCompany(applicationId, companyId);
        return "redirect:/supervisor/assign-app"; // reload the page
        }
        



    

}
