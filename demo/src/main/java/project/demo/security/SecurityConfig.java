package project.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final LoginSuccessHandler successHandler;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public SecurityConfig(LoginSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
            .requestMatchers("/api/companies/**").hasAuthority("ADMIN")
            .requestMatchers("/admin/**").hasAuthority("ADMIN")   
            .requestMatchers("/supervisor/**").hasAuthority("SUPERVISOR")
            .requestMatchers("/student/**").hasAuthority("STUDENT")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(successHandler)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
        )
        .exceptionHandling(exception -> exception
            .accessDeniedPage("/access-denied")
        );

    return http.build();
}
}