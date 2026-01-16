// package project.demo.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;

// import org.springframework.web.bind.annotation.PathVariable;
// import project.demo.entity.Application;
// import project.demo.repository.ApplicationRepository;
// import project.demo.repository.CompanyRepository;
// import project.demo.repository.StudentRepository;
// import project.demo.repository.SupervisorRepository;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// @Controller
// public class AdminPagesController {

//     private final CompanyRepository companyRepository;
//     private final ApplicationRepository applicationRepository;

//     @Autowired
//     private StudentRepository studentRepository;

//     @Autowired
//     private SupervisorRepository supervisorRepository;

//     public AdminPagesController(CompanyRepository companyRepository,
//                                 ApplicationRepository applicationRepository) {
//         this.companyRepository = companyRepository;
//         this.applicationRepository = applicationRepository;
//     }

//     @GetMapping("/admin/dashboard")
//     public String dashboard(Model model) {
//         model.addAttribute("studentCount", 0);
//         model.addAttribute("supervisorCount", 0);
//         model.addAttribute("applicationCount", 0);
//         model.addAttribute("recentLogs", java.util.Collections.emptyList());
//         return "Admin/admin-dashboard";
//     }

// @GetMapping("/admin/applications")
// public String applications(Model model) {

//     List<Application> apps = applicationRepository.findAll();
//     List<Map<String, Object>> displayList = new ArrayList<>();

//     for (Application app : apps) {
//         Map<String, Object> row = new HashMap<>();

//         row.put("id", app.getId());
//         row.put("status", app.getStatus() != null ? app.getStatus().name() : "UNKNOWN");

//         // default values (VERY IMPORTANT)
//         row.put("studentName", "—");
//         row.put("companyName", "—");
//         row.put("supervisorName", "—");

//         // student
//         if (app.getStudentId() != null) {
//             studentRepository.findById(app.getStudentId())
//                 .ifPresent(s -> row.put("studentName", s.getFullName()));
//         }

//         // company
//         if (app.getCompanyId() != null) {
//             companyRepository.findById(app.getCompanyId())
//                 .ifPresent(c -> row.put("companyName", c.getName()));
//         }

//         // supervisor
//         if (app.getSupervisorId() != null) {
//             supervisorRepository.findById(app.getSupervisorId())
//                 .ifPresent(sup -> row.put("supervisorName", sup.getFullName()));
//         }

//         displayList.add(row);
//     }

//     model.addAttribute("applications", displayList);
//     return "Admin/application";
// }
// @GetMapping("/admin/applications/{id}")
// public String viewApplication(@PathVariable Integer id, Model model) {

//     Application app = applicationRepository.findById(id)
//             .orElseThrow(() -> new IllegalArgumentException("Invalid application ID"));

//     model.addAttribute("application", app);

//     // student
//     if (app.getStudentId() != null) {
//         studentRepository.findById(app.getStudentId())
//                 .ifPresent(s -> model.addAttribute("student", s));
//     }

//     // company
//     if (app.getCompanyId() != null) {
//         companyRepository.findById(app.getCompanyId())
//                 .ifPresent(c -> model.addAttribute("company", c));
//     }

//     // supervisor
//     if (app.getSupervisorId() != null) {
//         supervisorRepository.findById(app.getSupervisorId())
//                 .ifPresent(sup -> model.addAttribute("supervisor", sup));
//     }

//     return "Admin/application-view";
// }


//     @GetMapping("/admin/approvals")
//     public String approvals() {
//         return "Admin/approvals";
//     }

//     @GetMapping("/admin/companies")
//     public String companies(Model model) {
//         model.addAttribute("companies", companyRepository.findAll());
//         return "Admin/companies";
//     }
// }
