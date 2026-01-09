package project.demo.service;


import project.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import project.demo.entity.User;
import project.demo.entity.UserRole;
import project.demo.enums.RoleName;
import project.demo.entity.Role;
import project.demo.repository.RoleRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User createUserWithRole(User user, RoleName roleName) {

    if(userRepository.existsByEmail(user.getEmail())){
        throw new IllegalArgumentException("Email already exists");
    }

    Role role = roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    // encode password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // create role relationship
    UserRole userRole = new UserRole(user, role);
    user.getUserRoles().add(userRole);

    // save user with cascade
    return userRepository.save(user);
}



}