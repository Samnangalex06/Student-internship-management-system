package project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import project.demo.entity.Student;
import project.demo.entity.Application;
import project.demo.repository.StudentRepository;
import project.demo.repository.UserRepository;
import project.demo.repository.CompanyRepository;
import project.demo.service.ApplicationService;

import java.util.*;
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

    // Helper to get current logged-in student
    private Student getCurrentStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;

        if (email == null) return null;

        return userRepository.findByEmailWithRoles(email)
                .flatMap(user -> studentRepository.findByUserId(user))
                .orElse(null);
    }

    // ==================== DASHBOARD ====================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Student student = getCurrentStudent();

        if (student != null) {
            model.addAttribute("student", student);

            List<Application> apps = applicationService.getApplicationsByStudent(student.getId());
            apps.sort(Comparator.comparing(Application::getCreatedAt,
                    Comparator.nullsLast(Comparator.reverseOrder())));

            model.addAttribute("applicationCount", apps.size());

            String latestStatus = apps.isEmpty() ? "No application"
                    : Optional.ofNullable(apps.get(0).getStatus())
                              .map(Enum::name)
                              .orElse("PENDING");
            model.addAttribute("latestStatus", latestStatus);

            Map<Integer, String> companyMap = new HashMap<>();
            Set<Integer> companyIds = apps.stream()
                    .map(Application::getCompanyId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (!companyIds.isEmpty()) {
                companyRepository.findAllById(companyIds).forEach(c ->
                        companyMap.put(c.getId(), c.getName()));
            }

            model.addAttribute("companyMap", companyMap);
            model.addAttribute("recentApplications", apps.stream().limit(5).toList());

        } else {
            model.addAttribute("student", new Student());
            model.addAttribute("applicationCount", 0);
            model.addAttribute("latestStatus", "No application");
            model.addAttribute("companyMap", Collections.emptyMap());
            model.addAttribute("recentApplications", Collections.emptyList());
        }

        return "student/dashboard";
    }

    // ==================== PROFILE ====================
    @GetMapping("/profile")
    public String profile(Model model) {
        Student student = getCurrentStudent();
        model.addAttribute("student", student != null ? student : new Student());
        return "student/profile-form";
    }

    // ==================== APPLICATION LIST ====================
    @GetMapping("/applications")
    public String applications(Model model) {
        Student student = getCurrentStudent();

        if (student != null) {
            List<Application> apps = applicationService.getApplicationsByStudent(student.getId());

            Map<Integer, String> companyMap = new HashMap<>();
            Set<Integer> companyIds = apps.stream()
                    .map(Application::getCompanyId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (!companyIds.isEmpty()) {
                companyRepository.findAllById(companyIds).forEach(c ->
                        companyMap.put(c.getId(), c.getName()));
            }

            model.addAttribute("applications", apps);
            model.addAttribute("companyMap", companyMap);
        } else {
            model.addAttribute("applications", Collections.emptyList());
            model.addAttribute("companyMap", Collections.emptyMap());
        }

        return "student/application-list";
    }

    // ==================== NEW APPLICATION FORM ====================
    @GetMapping("/applications/new")
    public String newApplicationForm(Model model) {
        Student student = getCurrentStudent();
        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("application", new Application());
        model.addAttribute("companies", companyRepository.findAll());

        return "student/application-form";
    }

    // ==================== VIEW / EDIT SINGLE APPLICATION ====================
    @GetMapping("/applications/{id}")
public String viewApplication(@PathVariable Integer id, Model model) {
    Student student = getCurrentStudent();
    if (student == null) {
        return "redirect:/login";
    }

    Application app = applicationService.getById(id);

    if (app == null || !app.getStudentId().equals(student.getId())) {
        return "redirect:/student/applications";
    }

    model.addAttribute("application", app);
    model.addAttribute("companies", companyRepository.findAll());

    return "student/application-form";
}

    // ==================== EVALUATIONS (placeholder) ====================
    @GetMapping("/evaluations")
    public String evaluations(Model model) {
        model.addAttribute("evaluations", Collections.emptyList());
        return "student/evaluation-list";
    }
}