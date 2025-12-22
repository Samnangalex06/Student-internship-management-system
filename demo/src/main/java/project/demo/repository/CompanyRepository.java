package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
