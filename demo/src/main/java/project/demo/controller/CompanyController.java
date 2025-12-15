package project.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import project.demo.entity.Company;
import project.demo.repository.CompanyRepository;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // CREATE company
    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    // VIEW all companies
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // UPDATE company
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id,
                                 @RequestBody Company company) {
        company.setId(id);
        return companyRepository.save(company);
    }

    // DELETE company
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
    }
}
