package project.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "internship_application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ---------- FOREIGN KEYS (IDS ONLY) ----------
    @Column(name = "student_id",nullable = false)
    private Integer studentId;

    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "supervisor_id")
    private Integer supervisorId;

    // ---------- FORM FIELD ----------
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ---------- STATUS ----------
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    // ---------- NEW FIELD: SUPERVISOR COMMENT ----------
    @Column(columnDefinition = "TEXT")
    private String supervisorComment;           // ← Add this

    // ---------- TIMESTAMPS ----------
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
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
    public Integer getId() { return id; }
    public Integer getStudentId() { return studentId; }
    public Integer getCompanyId() { return companyId; }
    public Integer getSupervisorId() { return supervisorId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ApplicationStatus getStatus() { return status; }
    public String getSupervisorComment() { return supervisorComment; }     // ← Add
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ---------- SETTERS ----------
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public void setCompanyId(Integer companyId) { this.companyId = companyId; }
    public void setSupervisorId(Integer supervisorId) { this.supervisorId = supervisorId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public void setSupervisorComment(String supervisorComment) {           // ← Add
        this.supervisorComment = supervisorComment;
    }

    // ---------- ENUM ----------
    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id",insertable = false, updatable = false)
    private Supervisor supervisor;


    public Student getStudent() { return student; }
    public Company getCompany() { return company; }
    public Supervisor getSupervisor() { return supervisor; }

}
}
