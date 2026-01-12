package project.demo.controller;

import org.attoparser.dom.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import project.demo.Model.ApplicationDTO;
import project.demo.entity.Application;
import project.demo.entity.Application.ApplicationStatus;
import project.demo.entity.Company;
import project.demo.entity.Supervisor;
import project.demo.entity.User;

import project.demo.repository.CompanyRepository;
import project.demo.repository.DocumentRepository;
import project.demo.repository.SupervisorRepository;
import project.demo.repository.UserRepository;
import project.demo.service.ApplicationService;
import project.demo.service.DocumentService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private DocumentRepository doc_Repository;

    @Autowired
    private DocumentService documentService;


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
                return "redirect:/supervisor/dashboard";
        }

        // =========================
        // Supervisor Assign Applications
        // ========================

      @GetMapping("/approvals")
        public String approvals(Model model,
                                @RequestParam(required = false) Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return "redirect:/login";

        User user = userRepo.findByEmailWithRoles(auth.getName()).orElseThrow();
        Supervisor supervisor =
                supervisorRepository.findByUserId(user.getId()).orElseThrow();

        List<ApplicationDTO> pendingApps =
                applicationRepository.findPendingWithDetails(
                        supervisor.getId(),
                        Application.ApplicationStatus.PENDING
                );

        model.addAttribute("pendingApplications", pendingApps);

        ApplicationDTO selectedApp = null;

        if (!pendingApps.isEmpty()) {
                if (id != null) {
                selectedApp = pendingApps.stream()
                        .filter(a -> a.getId().equals(id))
                        .findFirst()
                        .orElse(pendingApps.get(0));
                } else {
                selectedApp = pendingApps.get(0);
                }
        }

        model.addAttribute("selectedApplication", selectedApp);

        if (selectedApp != null) {
                model.addAttribute(
                        "documents",
                        doc_Repository.findByApplicationId(selectedApp.getId())
                );
        } else {
                model.addAttribute("documents", List.of());
        }

        return "Supervisor/approvals";
        }
        @GetMapping("/app_view")
        public String getDetailApp(@RequestParam Integer id, Model model) {

        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return "redirect:/login";

        User user = userRepo.findByEmailWithRoles(auth.getName()).orElseThrow();
        Supervisor supervisor = supervisorRepository.findByUserId(user.getId()).orElseThrow();

        
        ApplicationDTO application = applicationRepository
                .findByIdAndSupervisorId(id, supervisor.getId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

       
        List<project.demo.entity.Document> documents = doc_Repository.findByApplicationId(application.getId());

        
        model.addAttribute("applicationView", application);
        model.addAttribute("documents", documents);


        // 5. Return the detail page
        return "Supervisor/application_detail";
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
