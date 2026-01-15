package project.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
// import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "technical_score")
    private Integer technicalScore;

    @Column(name = "communication_score")
    private Integer communicationScore;

    @Column(name = "professionalism_score")
    private Integer professionalismScore;

    @ManyToOne
    @JoinColumn(name = "internship_app_id", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Supervisor supervisor;

    private Integer score;

    @Column(name = "evalutions_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    /* =====================
       Lifecycle callbacks
       ===================== */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    // getters & setters
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }
    public Integer getScore(){
        return score;
    }   

    public Application getapplication(){
        return application;
    }
    public void setScore(Integer score){
        this.score =score;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getTechnicalScore() {
        return technicalScore;
    }

    public void setTechnicalScore(Integer technicalScore) {
        this.technicalScore = technicalScore;
    }

    public Integer getCommunicationScore() {
        return communicationScore;
    }

    public void setCommunicationScore(Integer communicationScore) {
        this.communicationScore = communicationScore;
    }

    public Integer getProfessionalismScore() {
        return professionalismScore;
    }

    public void setProfessionalismScore(Integer professionalismScore) {
        this.professionalismScore = professionalismScore;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
    public void setApplication(Application application){
        this.application =application;
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}