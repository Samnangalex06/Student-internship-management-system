package project.demo.service;


import project.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import project.demo.entity.User;
import project.demo.entity.UserRole;
import project.demo.entity.UserRoleId;
import project.demo.enums.RoleName;
import project.demo.entity.Role;
import project.demo.repository.RoleRepository;
import java.util.List;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    //private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        //this.passwordEncoder = passwordEncoder;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User createUserWithRole(User user, RoleName roleName) {

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User savedUser = userRepository.save(user);
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserRoleId userRoleId = new UserRoleId(
                savedUser.getId(),
                role.getId()
        );
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUser(savedUser);
        userRole.setRole(role);
        savedUser.getUserRoles().add(userRole);

        return userRepository.save(savedUser);
    }
}
