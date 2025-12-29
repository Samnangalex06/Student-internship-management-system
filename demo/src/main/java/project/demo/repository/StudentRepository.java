package project.demo.repository;


import java.util.Optional;

import project.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import project.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
    Optional<Student> findByUserId(User userId);
    boolean existsByEmail(String email);

}
