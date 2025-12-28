// // package project.demo.controller;

// // public class StudentAdminController {
    
// // }


// package project.demo.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
// import project.demo.entity.Student;
// import project.demo.service.StudentService;

// @Controller
// @RequestMapping("/admin/students")
// public class StudentAdminController {

//     private final StudentService studentService;

//     public StudentAdminController(StudentService studentService) {
//         this.studentService = studentService;
//     }

//     // LIST
//     @GetMapping
//     public String list(Model model) {
//         model.addAttribute("students", studentService.getAll());
//         return "students";
//     }

//     // CREATE FORM
//     @GetMapping("/create")
//     public String createForm(Model model) {
//         model.addAttribute("student", new Student());
//         return "create-student";
//     }

//     // CREATE SUBMIT
//     @PostMapping("/create")
//     public String create(@ModelAttribute Student student, Model model) {
//         try {
//             studentService.create(student);
//             return "redirect:/admin/students";
//         } catch (Exception e) {
//             model.addAttribute("error", e.getMessage());
//             model.addAttribute("student", student);
//             return "create-student";
//         }
//     }

//     // EDIT FORM
//     @GetMapping("/edit/{id}")
//     public String editForm(@PathVariable Long id, Model model) {
//         model.addAttribute("student", studentService.getById(id));
//         return "edit-student";
//     }

//     // EDIT SUBMIT
//     @PostMapping("/edit/{id}")
//     public String update(@PathVariable Long id, @ModelAttribute Student student, Model model) {
//         try {
//             studentService.update(id, student);
//             return "redirect:/admin/students";
//         } catch (Exception e) {
//             model.addAttribute("error", e.getMessage());
//             model.addAttribute("student", student);
//             return "edit-student";
//         }
//     }

//     // DELETE
//     @PostMapping("/delete/{id}")
//     public String delete(@PathVariable Long id) {
//         studentService.delete(id);
//         return "redirect:/admin/students";
//     }
// }
