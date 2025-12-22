package project.demo.repository;

import project.demo.entity.Role;
import project.demo.enums.RoleName;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    List<Role> findRoleByRoleName(RoleName roleName);
}