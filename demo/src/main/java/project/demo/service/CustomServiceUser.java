package project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.demo.entity.User;
import project.demo.repository.UserRepository;

@Service
public class CustomServiceUser implements UserDetailsService{
    @Autowired
    private UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        
        User user = UserRepository.findUsersByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("STUDENT")
                .disabled(!user.isEnabled())
                .build();
    }

}
