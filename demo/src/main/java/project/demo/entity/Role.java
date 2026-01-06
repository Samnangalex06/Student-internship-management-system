package project.demo.entity;

import jakarta.persistence.*;
import project.demo.enums.RoleName;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName roleName;

    // REQUIRED by JPA
    public Role() {}

    // REQUIRED for RoleInitializer
    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
