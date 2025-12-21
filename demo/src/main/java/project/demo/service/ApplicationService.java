package project.demo.service;

import project.demo.entity.Application;
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
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            Application app = application.get();
            
            if (applicationDetails.getDescription() != null) {
                app.setDescription(applicationDetails.getDescription());
            }
            app.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(app);
        }
        return null;
    }
    
    // Delete application
    public void deleteApplication(Integer id) {
        applicationRepository.deleteById(id);
    }
    
    // Approve application
    public Application approveApplication(Integer id) {
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            Application app = application.get();

            app.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(app);
        }
        return null;
    }
    
    // Reject application
    public Application rejectApplication(Integer id) {
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            Application app = application.get();
            app.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(app);
        }
        return null;
    }
}
