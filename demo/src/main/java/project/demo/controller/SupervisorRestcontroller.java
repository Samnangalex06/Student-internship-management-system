package project.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import project.demo.Model.SupervisorDTO;
import project.demo.entity.Supervisor;
import project.demo.repository.SupervisorRepository;

public class SupervisorRestcontroller {
     @Autowired
    private SupervisorRepository supervisorRepository;

    // --- CREATE ---
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

    // --- READ ALL ---
    @GetMapping
    public List<SupervisorDTO> getAllSupervisors() {
        return supervisorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- READ ONE ---
    @GetMapping("/{id}")
    public SupervisorDTO getSupervisor(@PathVariable Integer id) {
        Supervisor sup = supervisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
        return toDTO(sup);
    }

    // --- UPDATE ---
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

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public String deleteSupervisor(@PathVariable Integer id) {
        supervisorRepository.deleteById(id);
        return "Supervisor deleted successfully";
    }

    // Helper: convert to DTO
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
}
