package project.demo.repository;

import project.demo.Model.ApplicationDTO;
import project.demo.entity.Application;
import project.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    
    List<Application> findByStudentId(Integer studentId);
    
    List<Application> findByCompanyId(Integer companyId);
    
    Optional<Application> findByStudentIdAndCompanyId(Integer studentId, Integer companyId);

    List<Application> findBySupervisorId(Integer supervisorId);
    List<Application> findByStatus(Application.ApplicationStatus status);
    List<Application> findBySupervisorIdAndStatus(Integer supervisorId, Application.ApplicationStatus status);
    @Query("""
        SELECT new project.demo.Model.ApplicationDTO(
            a.id,
            s.id, s.fullName,
            c.id, c.name,
            a.supervisorId,
            a.title,
            a.description,
            a.status,
            a.createdAt
        )
        FROM Application a
        JOIN Student s ON s.id = a.studentId
        JOIN Company c ON c.id = a.companyId
        WHERE a.supervisorId = :supervisorId
        AND a.status = :status
        ORDER BY a.createdAt DESC
        """)
        List<ApplicationDTO> findPendingWithDetails(
            @Param("supervisorId") Integer supervisorId,
            @Param("status") Application.ApplicationStatus status
        );

    @Query("""
        SELECT new project.demo.Model.ApplicationDTO(
            a.id,
            s.id, s.fullName,
            c.id, c.name,
            a.supervisorId,
            a.title,
            a.description,
            a.status,
            a.createdAt
        )
        FROM Application a
        JOIN Student s ON s.id = a.studentId
        JOIN Company c ON c.id = a.companyId
        WHERE a.id = :id
        AND a.supervisorId = :supervisorId
        """)
    Optional<ApplicationDTO> findByIdAndSupervisorId(@Param("id") Integer id ,@Param("supervisorId") Integer supervisorId);

    
}
