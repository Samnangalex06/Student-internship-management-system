package project.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import project.demo.entity.Supervisor;
import project.demo.repository.SupervisorRepository;

@Service
public class SupervisorService {

    private final SupervisorRepository repo;

    public SupervisorService(SupervisorRepository repo) {
        this.repo = repo;
    }

    public List<Supervisor> getAll() {
        return repo.findAll();
    }

    public Supervisor getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supervisor not found: " + id));
    }

    public Supervisor create(Supervisor s) {
        return repo.save(s);
    }

    public Supervisor update(Integer id, Supervisor updated) {
        Supervisor existing = getById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        return repo.save(existing);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
