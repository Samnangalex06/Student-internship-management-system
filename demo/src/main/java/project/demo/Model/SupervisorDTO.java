package project.demo.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SupervisorDTO {

    private Integer id;

    private Integer userId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Email must be valid")
    private String email;

    private Integer phone;

    @NotBlank(message = "Department is required")
    private String department;

    // --- Constructors ---
    public SupervisorDTO() {}

    public SupervisorDTO(Integer userId, String fullName, String email, Integer phone, String department) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    // --- Getters & Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getPhone() { return phone; }
    public void setPhone(Integer phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
