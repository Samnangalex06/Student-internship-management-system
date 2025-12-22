package project.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "internship_application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer studentId;

    @Column(nullable = false)
    private Integer companyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ---------- JPA LIFECYCLE ----------
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ---------- GETTERS ----------
    public Integer getId() {
        return id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ---------- SETTERS ----------
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ---------- ENUM ----------
    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}
