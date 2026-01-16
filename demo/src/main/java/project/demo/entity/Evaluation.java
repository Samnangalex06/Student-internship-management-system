package project.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
// import jakarta.persistence.criteria.CriteriaBuilder.In;


@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;

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

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }
    public Integer getScore(){
        return score;
    }   

    public Application getApplication() {
    return application;
    }

    public void setScore(Integer score){
        this.score =score;
    }


    public void setComment(String comment) {
        this.comment = comment;
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
    }
}