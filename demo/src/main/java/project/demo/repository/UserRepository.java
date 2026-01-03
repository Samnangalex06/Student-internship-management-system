package project.demo.repository;

import java.util.Optional;

import project.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
        SELECT u FROM User u
        LEFT JOIN FETCH u.userRoles ur
        LEFT JOIN FETCH ur.role
        WHERE u.email = :email
    """)
    Optional<User> findByEmailWithRoles(@Param("email") String email);
    
    boolean existsByEmail(String email);
}
