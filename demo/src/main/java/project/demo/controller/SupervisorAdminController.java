package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.entity.Supervisor;
import project.demo.entity.User;
import project.demo.enums.RoleName;
import project.demo.service.SupervisorService;
import project.demo.service.UserService;

@Controller
@RequestMapping("/admin/supervisors")
public class SupervisorAdminController {

    private SupervisorService supervisorService;
    private UserService userService;


    public SupervisorAdminController(SupervisorService supervisorService ,UserService userService) {
        this.supervisorService = supervisorService;
        this.userService = userService;
    }   

    

    @GetMapping
    public String list(Model model) {
        model.addAttribute("supervisors", supervisorService.getAllSupervisors());
        return "Admin/supervisors";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("supervisor", new Supervisor());
        model.addAttribute("user", new User());
        return "Admin/create-supervisor";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Supervisor supervisor, Model model,User user) {
        try {
            User createUser = userService.createUserWithRole(user, RoleName.SUPERVISOR);
            supervisor.setUserId(createUser.getId());
            supervisorService.create(supervisor);
            return "redirect:/admin/supervisors";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("supervisor", supervisor);
            model.addAttribute("user", user);
            return "Admin/create-supervisor";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("supervisor", supervisorService.getById(id));
        return "Admin/edit-supervisor";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute Supervisor supervisor,
                         Model model) {
        try {
            supervisorService.update(id, supervisor);
            return "redirect:/admin/supervisors";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("supervisor", supervisor);
            return "Admin/edit-supervisor";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        supervisorService.delete(id);
        return "redirect:/admin/supervisors";
    }
}
