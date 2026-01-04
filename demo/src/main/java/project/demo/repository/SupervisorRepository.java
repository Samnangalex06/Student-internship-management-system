package project.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.demo.entity.Supervisor;
import project.demo.entity.User;

import java.util.List;


public  interface SupervisorRepository extends JpaRepository<Supervisor,Integer> {
    boolean existsByUserId(Integer userId);
    
    boolean existsByEmail(String email);
    Optional<Supervisor> findByUserId(Integer user);

    
}
