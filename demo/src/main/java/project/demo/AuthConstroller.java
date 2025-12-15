package project.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthConstroller {

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }
    
}
