package project.demo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.demo.entity.Role;
import project.demo.enums.RoleName;
import project.demo.repository.RoleRepository;

@Component
public class RoleInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        createIfNotExists(RoleName.ADMIN);
        createIfNotExists(RoleName.STUDENT);
        createIfNotExists(RoleName.SUPERVISOR);
    }

    private void createIfNotExists(RoleName roleName) {
        roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
