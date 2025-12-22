package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class SupervisorController {
	@GetMapping("/viwe_Applications_Status")
	public String viwe_Applications_Status(Model model) {
		return "here you are!";
	}
	
}