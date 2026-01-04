package project.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.entity.Supervisor;
import project.demo.Model.SupervisorDTO;
import project.demo.repository.SupervisorRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
// @RequestMapping({"/api/supervisors","/supervisors"})
public class SupervisorController {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @GetMapping("/supervisors/dashbod")
    public String getMethodName(Model model) {
        
        return "supervisor/supervisor-dashboard";
    }
    



    @PostMapping("api/supersvisors")
    public SupervisorDTO createSupervisor(@Valid @RequestBody SupervisorDTO dto) {
        if (supervisorRepository.existsByUserId(dto.getUserId())) {
            throw new RuntimeException("User is already a supervisor");
        }

        Supervisor supervisor = new Supervisor();
        supervisor.setUserId(dto.getUserId());
        supervisor.setFullName(dto.getFullName());
        supervisor.setEmail(dto.getEmail());
        supervisor.setPhoneNumber(dto.getPhone());
        supervisor.setDepartment(dto.getDepartment());

        Supervisor saved = supervisorRepository.save(supervisor);
        return toDTO(saved);
    }
    @GetMapping("api/supersvisors")
    public List<SupervisorDTO> getAllSupervisors() {
        return supervisorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    private SupervisorDTO toDTO(Supervisor su){
        return new SupervisorDTO(
            su.getId(),
            su.getUserId(),
            su.getFullName(),
            su.getEmail(),
            su.getPhoneNumber(),
            su.getDepartment()
        );
    }

    // --- READ supervisor by ID ---
    @GetMapping("api/supersvisors/{id}")
    public SupervisorDTO getSupervisor(@PathVariable Integer id) {
        Supervisor sup = supervisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
        return toDTO(sup);
    }

    // --- UPDATE supervisor ---
    @PutMapping("api/supersvisors/{id}")
    public SupervisorDTO updateSupervisor(@PathVariable Integer id, @Valid @RequestBody SupervisorDTO dto) {
        Supervisor sup = supervisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));

        sup.setFullName(dto.getFullName());
        sup.setEmail(dto.getEmail());
        sup.setPhoneNumber(dto.getPhone());
        sup.setDepartment(dto.getDepartment());

        Supervisor updated = supervisorRepository.save(sup);
        return toDTO(updated);
    }

    // --- DELETE supervisor ---
    @DeleteMapping("api/supersvisors/{id}")
    public String deleteSupervisor(@PathVariable Integer id) {
        supervisorRepository.deleteById(id);
        return "Supervisor deleted successfully";
    }

    
}
