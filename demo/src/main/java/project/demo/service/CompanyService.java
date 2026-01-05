package project.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import project.demo.entity.Company;
import project.demo.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository repo;

    public CompanyService(CompanyRepository repo) {
        this.repo = repo;
    }

    public List<Company> getAll() {
        return repo.findAll();
    }

    public Company getById(Integer id) {
        return repo.findById(id).orElseThrow();
    }

    public void save(Company company) {
        repo.save(company);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
