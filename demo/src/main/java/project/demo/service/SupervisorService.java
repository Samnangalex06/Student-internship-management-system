package project.demo.service;

<<<<<<< HEAD
import org.springframework.stereotype.Service;
import project.demo.entity.Supervisor;
import project.demo.repository.SupervisorRepository;

import java.util.List;

@Service
public class SupervisorService {
    private final SupervisorRepository repo;

    public SupervisorService(SupervisorRepository repo) {
        this.repo = repo;
    }

    public List<Supervisor> getAll() { return repo.findAll(); }

    public Supervisor getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Supervisor not found"));
    }

    public Supervisor create(Supervisor s) {
        if (repo.existsByEmail(s.getEmail())) throw new IllegalArgumentException("Email already exists");
        return repo.save(s);
    }

    public Supervisor update(Long id, Supervisor s) {
        Supervisor existing = getById(id);
        existing.setFullName(s.getFullName());
        existing.setEmail(s.getEmail());
        return repo.save(existing);
    }

    public void delete(Long id) { repo.deleteById(id); }
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import project.demo.repository.SupervisorRepository;

@Service
public class SupervisorService {
    @Autowired
    private SupervisorRepository sup_Repository;

    public boolean isSupervisor(Integer userId){
        return sup_Repository.existsByUserId(userId);
    }
>>>>>>> origin/MoniRom
}
