package project.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.demo.Model.SupervisorDTO;
import project.demo.entity.Supervisor;
import project.demo.repository.SupervisorRepository;

@Service
@Transactional
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    /* =========================
       READ
       ========================= */

    public List<SupervisorDTO> getAllSupervisors() {
        return supervisorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Supervisor getById(Integer id) {
        return supervisorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supervisor not found with id: " + id));
    }

    public boolean isSupervisor(Integer userId) {
        return supervisorRepository.existsByUserId(userId);
    }

    /* =========================
       CREATE
       ========================= */

    public Supervisor create(Supervisor supervisor) {

        if (supervisorRepository.existsByEmail(supervisor.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        return supervisorRepository.save(supervisor);
    }

    /* =========================
       UPDATE
       ========================= */

    public Supervisor update(Integer id, Supervisor updated) {

        Supervisor existing = getById(id);

        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setDepartment(updated.getDepartment());

        return supervisorRepository.save(existing);
    }

    /* =========================
       DELETE
       ========================= */

    public void delete(Integer id) {
        if (!supervisorRepository.existsById(id)) {
            throw new RuntimeException("Supervisor not found");
        }
        supervisorRepository.deleteById(id);
    }

    /* =========================
       MAPPER
       ========================= */

    private SupervisorDTO toDTO(Supervisor s) {
        return new SupervisorDTO(
                s.getId(),
                s.getUserId(),
                s.getFullName(),
                s.getEmail(),
                s.getPhoneNumber(),
                s.getDepartment()
        );
    }
}
