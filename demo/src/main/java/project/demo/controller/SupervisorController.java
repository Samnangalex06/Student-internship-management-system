package project.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.demo.Model.ApplicationDTO;
import project.demo.entity.Application;

import project.demo.entity.Company;
import project.demo.entity.Evaluation;
import project.demo.entity.Supervisor;
import project.demo.entity.User;

import project.demo.repository.CompanyRepository;
import project.demo.repository.DocumentRepository;
import project.demo.repository.SupervisorRepository;
import project.demo.repository.UserRepository;
import project.demo.service.ApplicationService;
import project.demo.service.EvaluationService;
import project.demo.service.SupervisorService;

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
    private DocumentRepository doc_Repository;


    @Autowired
    private EvaluationService evaluationService;




    @Autowired
    private project.demo.repository.ApplicationRepository applicationRepository;

    private final SupervisorService supervisorService ;

    public SupervisorController(SupervisorService supervisorService){
        this.supervisorService = supervisorService;
    }
    


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

        // ===============================
        // Supervisor Approve Applications
        // ===============================

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
                .findDTOByIdAndSupervisorId(id, supervisor.getId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

       
        List<project.demo.entity.Document> documents = doc_Repository.findByApplicationId(application.getId());

        
        model.addAttribute("applicationView", application);
        model.addAttribute("documents", documents);


        // 5. Return the detail page
        return "Supervisor/application_detail";
        }


        


        
    
        @GetMapping("/evaluation")
        public String showAssignApplications(Model model) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                System.out.println("DEBUG: /evaluation GET called by user: " + (auth != null ? auth.getName() : "null"));

                if (auth == null) {
                        System.out.println("DEBUG: No authenticated user, redirecting to login.");
                        return "redirect:/login";
                }

                User user = userRepo.findByEmailWithRoles(auth.getName()).orElseThrow(() -> new RuntimeException("User not found"));
                System.out.println("DEBUG: Found user: " + user.getEmail());

                Supervisor supervisor = supervisorRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Supervisor not found"));
                System.out.println("DEBUG: Supervisor ID: " + supervisor.getId());

                // Load applications with PENDING status
                List<Application> applications = applicationRepository.findByStatus(Application.ApplicationStatus.PENDING);
                System.out.println("DEBUG: Pending applications count: " + applications.size());

                // Load all companies
                List<Company> companies = companyService.getAll();
                System.out.println("DEBUG: Total companies count: " + companies.size());
                
                Map<Integer, List<Evaluation>> evaluationsMap = applications.stream()
                .collect(Collectors.toMap(
                        Application::getId,
                        app -> evaluationService.getEvaluationsByApplicationId(app.getId()) // sorted by date DESC
                ));
                model.addAttribute("applications", applications);
                model.addAttribute("companies", companies);
                model.addAttribute("evaluationsMap", evaluationsMap);
                return "Supervisor/evaluation"; // Thymeleaf template
        }


        // Handle form submission
        @PostMapping("/evaluation")
        public String assign(@RequestParam Integer applicationId,
                                @RequestParam String comment,
                                @RequestParam Integer score,
                                Authentication authentication  ) {

        
            System.out.println("DEBUG POST: applicationId=" + applicationId);
                System.out.println("DEBUG POST: comment=" + comment + ", score=" + score);
                System.out.println("DEBUG POST: username=" + authentication.getName());

                Supervisor supervisor = supervisorRepository.findByEmail(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("Supervisor not found"));
                System.out.println("DEBUG POST: supervisorId=" + supervisor.getId());

        Application application = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new RuntimeException("Application not found"));         if (application.getSupervisorId() == null) {
                application.setSupervisorId(supervisor.getId());
                applicationRepository.save(application);
                System.out.println("DEBUG POST: application assigned to supervisorId=" + supervisor.getId());
        }

        Evaluation evaluation = evaluationService
        .getByApplicationAndSupervisor(applicationId, supervisor.getId());

        if (evaluation == null) {
                evaluation = new Evaluation();
                evaluation.setApplication(application);
                evaluation.setSupervisor(supervisor);
        }

        evaluation.setComment(comment);
        evaluation.setScore(score);

        evaluationService.addEvaluation(evaluation);
        return "redirect:/supervisor/evaluation"; // reload the page
        }



        @GetMapping("/profile")
        public String profile(Model model ,Authentication authentication) {

                Supervisor supervisor = supervisorRepository.findByEmail(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("Supervisor not found"));

                Supervisor supervisorData = supervisorService.getById(supervisor.getId());
                System.out.println(supervisorData +"not error");
                model.addAttribute("supervisor",supervisorData);
                

            return "Supervisor/profile";
        }
        

        @PostMapping("/profile/save")
        public String SaveProfile(@ModelAttribute("supervisor") Supervisor supervisor,RedirectAttributes redirectAttributes) {
           try{
                Supervisor supervisorChange= supervisorService.getById(supervisor.getId());
                supervisorChange.setFullName(supervisor.getFullName());
                supervisorChange.setDepartment(supervisor.getDepartment());
                supervisorChange.setEmail(supervisor.getEmail());
                supervisorChange.setPhoneNumber(supervisor.getPhoneNumber());

                supervisorService.update(supervisorChange.getId(), supervisorChange);
                System.out.println("this error" + supervisorChange.getFullName());
                redirectAttributes.addFlashAttribute("success",true);

           }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            e.printStackTrace();
           }
            
            return "redirect:/supervisor/profile";
        }
        


        



    

}
