package project.demo.service;

import project.demo.entity.Application;
import project.demo.enums.ApplicationStatus;
import project.demo.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    // Create a new application
    public Application createApplication(Application application) {
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());
        application.setApplicationDate(LocalDateTime.now());
        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.PENDING);
        }
        return applicationRepository.save(application);
    }
    
    // Get application by ID
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }
    
    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    // Get applications by student ID
    public List<Application> getApplicationsByStudent(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }
    
    // Get applications by company ID
    public List<Application> getApplicationsByCompany(Long companyId) {
        return applicationRepository.findByCompanyId(companyId);
    }
    
    // Get applications by status
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }
    
    // Get applications by student and status
    public List<Application> getApplicationsByStudentAndStatus(Long studentId, ApplicationStatus status) {
        return applicationRepository.findByStudentIdAndStatus(studentId, status);
    }
    
    // Update application
    public Application updateApplication(Long id, Application applicationDetails) {
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            Application app = application.get();
            if (applicationDetails.getStatus() != null) {
                app.setStatus(applicationDetails.getStatus());
            }
            if (applicationDetails.getDescription() != null) {
                app.setDescription(applicationDetails.getDescription());
            }
            if (applicationDetails.getStartDate() != null) {
                app.setStartDate(applicationDetails.getStartDate());
            }
            if (applicationDetails.getEndDate() != null) {
                app.setEndDate(applicationDetails.getEndDate());
            }
            app.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(app);
        }
        return null;
    }
    
    // Delete application
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
    
    // Approve application
    public Application approveApplication(Long id) {
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            Application app = application.get();
            app.setStatus(ApplicationStatus.APPROVED);
            app.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(app);
        }
        return null;
    }
    
    // Reject application
    public Application rejectApplication(Long id) {
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            Application app = application.get();
            app.setStatus(ApplicationStatus.REJECTED);
            app.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(app);
        }
        return null;
    }
}
