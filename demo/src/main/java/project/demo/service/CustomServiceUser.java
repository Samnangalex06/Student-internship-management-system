package project.demo.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.demo.entity.User;
import project.demo.repository.UserRepository;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class CustomServiceUser implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomServiceUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmailWithRoles(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities =
            user.getUserRoles().stream()
                .map(ur -> new SimpleGrantedAuthority(
                        ur.getRole().getRoleName().name()
                ))
                .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // plain text
                .authorities(authorities)
                .build();
    }
}
