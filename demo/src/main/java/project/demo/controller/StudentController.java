package project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

// Removed the generic Application import to prevent naming conflicts
import project.demo.entity.Student;
import project.demo.entity.User;
import project.demo.entity.Company;
import project.demo.repository.StudentRepository;
import project.demo.repository.UserRepository;
import project.demo.repository.CompanyRepository;
import project.demo.service.ApplicationService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //String email = auth != null ? auth.getName() : null;

        if (email != null) {
            Optional<User> userOpt = userRepository.findUsersByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                Optional<Student> studentOpt = studentRepository.findByUserId(user);
                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();
                    model.addAttribute("student", student);

                    // Force the compiler to use your specific Entity class
                    List<project.demo.entity.Application> apps = applicationService.getApplicationsByStudent(student.getId());
                    
                    apps = apps.stream()
                            .sorted(Comparator.comparing(project.demo.entity.Application::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                            .collect(Collectors.toList());

                    model.addAttribute("applicationCount", apps.size());

                    // Fix for lines 62-64: Explicitly use the full path to access getStatus()
                    String currentStatus = "No application";
                    if (apps != null && !apps.isEmpty()) {
                        project.demo.entity.Application latestApp = apps.get(0);
                        if (latestApp.getStatus() != null) {
                            currentStatus = latestApp.getStatus().name();
                        }
                    }
                    model.addAttribute("latestStatus", currentStatus);

                    // Build company map using the full path
                    java.util.Map<Integer, String> companyMap = apps.stream()
                        .map(project.demo.entity.Application::getCompanyId)
                        .distinct()
                        .collect(Collectors.toMap(
                            id -> id, 
                            id -> companyRepository.findById(id).map(Company::getName).orElse("Unknown"),
                            (existing, replacement) -> existing // Prevent duplicate key errors
                        ));

                    model.addAttribute("companyMap", companyMap);
                    model.addAttribute("recentApplications", apps.stream().limit(5).collect(Collectors.toList()));
                    return "student/dashboard";
                }
            }
        }

        model.addAttribute("student", new Student());
        model.addAttribute("applicationCount", 0);
        model.addAttribute("latestStatus", "No application");
        model.addAttribute("recentApplications", java.util.List.of());
        return "student/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;

        if (email != null) {
            Optional<User> userOpt = userRepository.findUsersByEmail(email);
            if (userOpt.isPresent()) {
                Optional<Student> studentOpt = studentRepository.findByUserId(userOpt.get());
                studentOpt.ifPresent(student -> model.addAttribute("student", student));
            }
        }

        if (!model.containsAttribute("student")) {
            model.addAttribute("student", new Student());
        }
        return "student/profile-form";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;

        if (email != null) {
            Optional<User> userOpt = userRepository.findUsersByEmail(email);
            if (userOpt.isPresent()) {
                Optional<Student> studentOpt = studentRepository.findByUserId(userOpt.get());
                if (studentOpt.isPresent()) {
                    // Using the full path here as well
                    List<project.demo.entity.Application> apps = applicationService.getApplicationsByStudent(studentOpt.get().getId());
                    java.util.Map<Integer, String> companyMap = apps.stream()
                        .map(project.demo.entity.Application::getCompanyId)
                        .distinct()
                        .collect(Collectors.toMap(
                            id -> id, 
                            id -> companyRepository.findById(id).map(Company::getName).orElse("Unknown"),
                            (existing, replacement) -> existing
                        ));

                    model.addAttribute("applications", apps);
                    model.addAttribute("companyMap", companyMap);
                    return "student/application-list";
                }
            }
        }

        model.addAttribute("applications", java.util.List.of());
        return "student/application-list";
    }

    @GetMapping("/evaluations")
    public String evaluations(Model model) {
        model.addAttribute("evaluations", java.util.List.of());
        return "student/evaluation-list";
    }
}   