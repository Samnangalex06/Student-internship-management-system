
package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.entity.Student;
import project.demo.service.StudentService;
import project.demo.service.UserService;
import project.demo.entity.User;
import project.demo.enums.RoleName;

@Controller
@RequestMapping("/admin/students")
public class StudentAdminController {

    private StudentService studentService;
    private UserService userService;
    public StudentAdminController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", studentService.getAll());
        return "Admin/students";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("user", new User());
        return "Admin/create-student";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Student student,@ModelAttribute User user ,Model model) {
        try {
            User createUser = userService.createUserWithRole(user, RoleName.STUDENT);
            student.setUserId(createUser);
            studentService.create(student);
            return "redirect:/admin/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("student", student);
            model.addAttribute("users", user);
            return "Admin/create-student";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "Admin/edit-student";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Student student, Model model) {
        try {
            studentService.update(id, student);
            return "redirect:/admin/students";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("student", student);
            return "Admin/edit-student";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        studentService.delete(id);
        return "redirect:Admin/students";
    }
}
