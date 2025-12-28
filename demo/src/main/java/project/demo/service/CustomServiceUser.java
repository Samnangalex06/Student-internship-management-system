// package project.demo.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
// import project.demo.entity.User;
// import project.demo.entity.UserRole;
// import project.demo.repository.UserRepository;

// import java.util.Collection;
// import java.util.Set;
// import java.util.stream.Collectors;

// @Service
// public class CustomServiceUser implements UserDetailsService {
//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
//         User user = userRepository.findUsersByEmail(email)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

//         Collection<GrantedAuthority> authorities = getAuthorities(user);

//         return org.springframework.security.core.userdetails.User
//                 .withUsername(user.getEmail())
//                 .password(user.getPassword())
//                 .authorities(authorities)
//                 .disabled(!user.isEnabled())
//                 .build();
//     }

//     private Collection<GrantedAuthority> getAuthorities(User user) {
//         Set<UserRole> userRoles = user.getUserRoles();
//         if (userRoles == null || userRoles.isEmpty()) {
//             return Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
//         }
//         return userRoles.stream()
//                 .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRole().getRoleName()))
//                 .collect(Collectors.toSet());
//     }
// }