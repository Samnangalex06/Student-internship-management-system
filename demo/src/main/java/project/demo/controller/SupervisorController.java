package project.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.demo.entity.Supervisor;
import project.demo.Model.SupervisorDTO;
import project.demo.repository.SupervisorRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supervisors")
public class SupervisorController {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @PostMapping
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
    @GetMapping
    public List<SupervisorDTO> getAllSupervisors() {
        return supervisorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- READ supervisor by ID ---
    @GetMapping("/{id}")
    public SupervisorDTO getSupervisor(@PathVariable Integer id) {
        Supervisor sup = supervisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
        return toDTO(sup);
    }

    // --- UPDATE supervisor ---
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
    public String deleteSupervisor(@PathVariable Integer id) {
        supervisorRepository.deleteById(id);
        return "Supervisor deleted successfully";
    }

    // --- Helper method to convert Entity → DTO ---
    private SupervisorDTO toDTO(Supervisor supervisor) {
        SupervisorDTO dto = new SupervisorDTO();
        dto.setUserId(supervisor.getUserId());
        dto.setFullName(supervisor.getFullName());
        dto.setEmail(supervisor.getEmail());
        dto.setPhone(supervisor.getPhoneNumber());
        dto.setDepartment(supervisor.getDepartment());
        return dto;
    }
}
