package project.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentId;

    @ManyToOne
    @JoinColumn(name = "internship_app_id")
    private Application internshipAppId;

    // getters & setters
    public Integer getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }
    public Application getInternshipAppId() {
        return internshipAppId;
    }
    public void setInternshipAppId(Application internshipAppId) {
        this.internshipAppId = internshipAppId;
    }
}
