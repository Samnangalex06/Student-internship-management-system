package project.demo.controller;

import project.demo.entity.Application;
import project.demo.enums.ApplicationStatus;
import project.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(maxAge = 3600)
public class ApplicationController {
    
    @Autowired
    private ApplicationService applicationService;
    
    // Create a new application
    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application createdApplication = applicationService.createApplication(application);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }
    
    // Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        Optional<Application> application = applicationService.getApplicationById(id);
        return application.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Get all applications
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
    
    // Get applications by student ID
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Application>> getApplicationsByStudent(@PathVariable Long studentId) {
        List<Application> applications = applicationService.getApplicationsByStudent(studentId);
        return ResponseEntity.ok(applications);
    }
    
    // Get applications by company ID
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Application>> getApplicationsByCompany(@PathVariable Long companyId) {
        List<Application> applications = applicationService.getApplicationsByCompany(companyId);
        return ResponseEntity.ok(applications);
    }
    
    // Get applications by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable ApplicationStatus status) {
        List<Application> applications = applicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }
    
    // Update application
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application applicationDetails) {
        Application updatedApplication = applicationService.updateApplication(id, applicationDetails);
        if (updatedApplication != null) {
            return ResponseEntity.ok(updatedApplication);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    
    // Approve application
    @PutMapping("/{id}/approve")
    public ResponseEntity<Application> approveApplication(@PathVariable Long id) {
        Application approvedApplication = applicationService.approveApplication(id);
        if (approvedApplication != null) {
            return ResponseEntity.ok(approvedApplication);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Reject application
    @PutMapping("/{id}/reject")
    public ResponseEntity<Application> rejectApplication(@PathVariable Long id) {
        Application rejectedApplication = applicationService.rejectApplication(id);
        if (rejectedApplication != null) {
            return ResponseEntity.ok(rejectedApplication);
        }
        return ResponseEntity.notFound().build();
    }
}
