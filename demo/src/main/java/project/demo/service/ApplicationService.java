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
        return applicationRepository.save(application);
    }
    
    // Get application by ID (returns Optional - safe)
    public Optional<Application> getApplicationById(Integer id) {
        return applicationRepository.findById(id);
    }
    
    // Helper used by StudentController: returns Application or null
    public Application getById(Integer id) {
        return getApplicationById(id).orElse(null);
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
    
    // Get applications by supervisor ID
    public List<Application> getApplicationsBySupervisor(Integer supervisorId) {
        return applicationRepository.findBySupervisorId(supervisorId);
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
    
    // Approve application
    public Application approveApplication(Integer id) {
        return applicationRepository.findById(id).map(app -> {
            app.setStatus(Application.ApplicationStatus.APPROVED);
            return applicationRepository.save(app);
        }).orElse(null);
    }
    
    // Reject application
    public Application rejectApplication(Integer id) {
        return applicationRepository.findById(id).map(app -> {
            app.setStatus(Application.ApplicationStatus.REJECTED);
            return applicationRepository.save(app);
        }).orElse(null);
    }
    public Application save(Application application) {
        return applicationRepository.save(application);
    }
    @Autowired
    private project.demo.repository.CompanyRepository companyRepository;

    public Application assignCompany(Integer applicationId, Integer companyId) {
        return applicationRepository.findById(applicationId).map(app -> {
            companyRepository.findById(companyId).ifPresent(company -> app.setCompanyId(company.getId()));
            return applicationRepository.save(app);
        }).orElse(null);
    }


}