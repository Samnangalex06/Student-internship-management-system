package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.entity.Supervisor;
import project.demo.service.SupervisorService;

@Controller
@RequestMapping("/admin/supervisors")
public class SupervisorAdminController {

    private final SupervisorService supervisorService;

    public SupervisorAdminController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("supervisors", supervisorService.getAll());
        return "supervisors";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("supervisor", new Supervisor());
        return "create-supervisor";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Supervisor supervisor, Model model) {
        try {
            supervisorService.create(supervisor);
            return "redirect:/admin/supervisors";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("supervisor", supervisor);
            return "create-supervisor";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("supervisor", supervisorService.getById(id));
        return "edit-supervisor";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Supervisor supervisor, Model model) {
        try {
            supervisorService.update(id, supervisor);
            return "redirect:/admin/supervisors";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("supervisor", supervisor);
            return "edit-supervisor";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        supervisorService.delete(id);
        return "redirect:/admin/supervisors";
    }
}
