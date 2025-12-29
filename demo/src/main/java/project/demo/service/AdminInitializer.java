// package project.demo.service;

// import jakarta.annotation.PostConstruct;
// import org.springframework.stereotype.Component;
// import project.demo.entity.User;
// import project.demo.enums.RoleName;
// import project.demo.repository.UserRepository;

// @Component
// public class AdminInitializer {

//     private final UserRepository userRepository;
//     private final UserService userService;

//     public AdminInitializer(UserRepository userRepository, UserService userService) {
//         this.userRepository = userRepository;
//         this.userService = userService;
//     }

//     @PostConstruct
//     public void createAdmin() {

//         if (userRepository.findByEmailWithRoles("admin@gmail.com").isPresent()) {
//             return; 
//         }

//         User admin = new User();
//         admin.setEmail("admin@gmail.com");
//         admin.setUsername("Admin");
//         admin.setPassword("123");
//         admin.setEnable(true);

//         userService.createUserWithRole(admin, RoleName.ADMIN);

//         System.out.println("✅ ADMIN user created");
//     }
// }
