package project.demo.repository;

import project.demo.entity.Role;
import project.demo.enums.RoleName;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
}
