package project.demo.repository;

import project.demo.entity.Application;
import project.demo.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    List<Application> findByStudentId(Long studentId);
    
    List<Application> findByCompanyId(Long companyId);
    
    List<Application> findByStatus(ApplicationStatus status);
    
    List<Application> findByStudentIdAndStatus(Long studentId, ApplicationStatus status);
    
    List<Application> findByCompanyIdAndStatus(Long companyId, ApplicationStatus status);
    
    Optional<Application> findByStudentIdAndCompanyId(Long studentId, Long companyId);
}
