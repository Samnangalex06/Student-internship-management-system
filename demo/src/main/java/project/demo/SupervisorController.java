package project.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SupervisorController {
	@GetMapping("/viwe_Applications_Status")
	public String viwe_Applications_Status(Model model) {
		return "here you are!";
	}
	
}
