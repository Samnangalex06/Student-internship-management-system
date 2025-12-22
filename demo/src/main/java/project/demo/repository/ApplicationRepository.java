package project.demo.repository;

import project.demo.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    
    List<Application> findByStudentId(Integer studentId);
    
    List<Application> findByCompanyId(Integer companyId);
    
    Optional<Application> findByStudentIdAndCompanyId(Integer studentId, Integer companyId);
}
