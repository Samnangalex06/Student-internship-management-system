package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import project.demo.entity.Supervisor;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    boolean existsByEmail(String email);
=======

import jakarta.persistence.criteria.CriteriaBuilder.In;
import project.demo.entity.Supervisor;

public  interface SupervisorRepository extends JpaRepository<Supervisor,Integer> {
    boolean existsByUserId(Integer userId);

    
>>>>>>> origin/MoniRom
}
