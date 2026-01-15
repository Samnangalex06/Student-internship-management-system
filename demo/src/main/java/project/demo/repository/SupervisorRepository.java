package project.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.demo.entity.Supervisor;


public  interface SupervisorRepository extends JpaRepository<Supervisor,Integer> {
    boolean existsByUserId(Integer userId);
    
    boolean existsByEmail(String email);
    Optional<Supervisor> findByUserId(Integer user);
    Optional<Supervisor> findByEmail(String email);

    
}
