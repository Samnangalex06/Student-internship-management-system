package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.entity.Supervisor;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    boolean existsByEmail(String email);
}
