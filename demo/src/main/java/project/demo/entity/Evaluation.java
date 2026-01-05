package project.demo.entity;

import jakarta.persistence.*;


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
    // getters & setters

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
