package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.demo.entity.Student;
import project.demo.entity.User;
import project.demo.enums.RoleName;
import project.demo.service.StudentService;
import project.demo.service.UserService;

@Controller
@RequestMapping("/admin/students")
public class StudentAdminController {

    private final StudentService studentService;
    private final UserService userService;

    public StudentAdminController(StudentService studentService,
                                  UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }
    
    // =========================
    // LIST STUDENTS
    // =========================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.getAll());
        return "Admin/students";
    }

    // =========================
    // CREATE STUDENT (FORM)
    // =========================
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("user", new User());
        return "Admin/create-student";
    }

    // =========================
    // CREATE STUDENT (SUBMIT)
    // =========================
    @PostMapping("/create")
    public String create(@ModelAttribute Student student,
                         @ModelAttribute User user,
                         Model model) {
        try {
            // Create user with STUDENT role
            User createdUser = userService.createUserWithRole(user, RoleName.STUDENT);

            // FIXED: match Student.java
            student.setUser(createdUser);

            // Save student
            studentService.create(student);

            return "redirect:/admin/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("student", student);
            model.addAttribute("user", user);
            return "Admin/create-student";
        }
    }

    // =========================
    // EDIT STUDENT (FORM)
    // =========================
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "Admin/edit-student";
    }

    // =========================
    // UPDATE STUDENT
    // =========================
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute Student student,
                         Model model) {
        try {
            studentService.update(id, student);
            return "redirect:/admin/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("student", student);
            return "Admin/edit-student";
        }
    }

    // =========================
    // DELETE STUDENT
    // =========================
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        studentService.delete(id);
        return "redirect:/admin/students";
    }
}
