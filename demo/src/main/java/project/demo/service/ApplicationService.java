package project.demo.service;

import project.demo.entity.Application;
import project.demo.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    // Create a new application
    public Application createApplication(Application application) {
        // Lifecycle methods in your Entity (@PrePersist) usually handle this,
        // but keeping it here is fine as a backup.
        return applicationRepository.save(application);
    }
    
    // Get application by ID
    public Optional<Application> getApplicationById(Integer id) {
        return applicationRepository.findById(id);
    }
    
    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    // Get applications by student ID
    public List<Application> getApplicationsByStudent(Integer studentId) {
        return applicationRepository.findByStudentId(studentId);
    }
    
    // Get applications by company ID
    public List<Application> getApplicationsByCompany(Integer companyId) {
        return applicationRepository.findByCompanyId(companyId);
    }

    // Update application
    public Application updateApplication(Integer id, Application applicationDetails) {
        return applicationRepository.findById(id).map(app -> {
            if (applicationDetails.getDescription() != null) {
                app.setDescription(applicationDetails.getDescription());
            }
            if (applicationDetails.getStatus() != null) {
                app.setStatus(applicationDetails.getStatus());
            }
            return applicationRepository.save(app);
        }).orElse(null);
    }
    
    // Delete application
    public void deleteApplication(Integer id) {
        applicationRepository.deleteById(id);
    }
    
    // Approve application - FIXED to actually change status
    public Application approveApplication(Integer id) {
        return applicationRepository.findById(id).map(app -> {
            app.setStatus(Application.ApplicationStatus.APPROVED);
            return applicationRepository.save(app);
        }).orElse(null);
    }
    
    // Reject application - FIXED to actually change status
    public Application rejectApplication(Integer id) {
        return applicationRepository.findById(id).map(app -> {
            app.setStatus(Application.ApplicationStatus.REJECTED);
            return applicationRepository.save(app);
        }).orElse(null);
    }
}