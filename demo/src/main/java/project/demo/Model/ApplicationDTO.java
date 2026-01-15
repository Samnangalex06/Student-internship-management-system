package project.demo.Model;

import java.time.LocalDateTime;

import project.demo.entity.Application;


public class ApplicationDTO {

    private Integer id;

    private Integer studentId;
    private String studentName;

    private Integer companyId;
    private String companyName;

    private Integer supervisorId;

    private String title;
    private String description;
    private Application.ApplicationStatus status;
    private LocalDateTime createdAt;

    // 🔑 REQUIRED constructor for JPQL
    public ApplicationDTO(
            Integer id,
            Integer studentId,
            String studentName,
            Integer companyId,
            String companyName,
            Integer supervisorId,
            String title,
            String description,
            Application.ApplicationStatus status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.supervisorId = supervisorId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    // getters only (no setters needed)
    public Integer getId() { return id; }
    public Integer getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public Integer getCompanyId() { return companyId; }
    public String getCompanyName() { return companyName; }
    public Integer getSupervisorId() { return supervisorId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Application.ApplicationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
