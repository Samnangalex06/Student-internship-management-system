package project.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "full_name", nullable = false)
    private String full_name;

    @Column (name = "phone_number", unique = true)
    private Integer phone_number;

    @Column (name = "email", unique = true)
    private String email;

    @Column (name = "year")
    private Integer year;

    @Column (name = "department")
    private String department;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    public void student(){}

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getFullName(){
        return full_name;
    }
    public void setFullName(String full_name){
        this.full_name = full_name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public Integer getPhoneNumber(){
        return phone_number;
    }
    public void setPhoneNumber(Integer phone_number){
        this.phone_number = phone_number;
    }
    public Integer getYear(){
        return year;
    }
    public void setYear(Integer year){
        this.year = year;
    }
    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }
    public User getUserId(){
        return userId;
    }
    public void setUserId(User userId){
        this.userId= userId;
    }
    
}
