package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.demo.entity.Company;
import project.demo.repository.CompanyRepository;

@Controller
@RequestMapping("/admin/companies")
public class CompanyAdminController {

    private final CompanyRepository companyRepository;

    public CompanyAdminController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // List companies page
 

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("company", new Company());
        return "Admin/company-form";
    }

    //  Handle create submit
    @PostMapping("/create")
    public String createCompany(@ModelAttribute("company") Company company) {
        companyRepository.save(company);
        return "redirect:/admin/companies";
    }

    //  Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        model.addAttribute("company", company);
        return "Admin/company-form";
    }

    //  Handle edit submit
    @PostMapping("/edit/{id}")
    public String updateCompany(@PathVariable Integer id,
                                @ModelAttribute("company") Company company) {
        company.setId(id);
        companyRepository.save(company);
        return "redirect:/admin/companies";
    }

    //  Delete (POST because HTML form uses POST)
    @PostMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Integer id) {
        companyRepository.deleteById(id);
        return "redirect:/admin/companies";
    }
}
