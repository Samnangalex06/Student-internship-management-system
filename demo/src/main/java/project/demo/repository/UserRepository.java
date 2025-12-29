package project.demo.repository;

import java.util.Optional;

import project.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findUsersByEmail(String email);
    
}