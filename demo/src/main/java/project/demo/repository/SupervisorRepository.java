package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import project.demo.entity.Supervisor;

public  interface SupervisorRepository extends JpaRepository<Supervisor,Integer> {
    boolean existsByUserId(Integer userId);

    
}
