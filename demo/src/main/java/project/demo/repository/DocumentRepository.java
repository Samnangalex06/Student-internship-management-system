package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.demo.entity.Document;
import project.demo.entity.Application;
import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findByInternshipAppId(Application internshipAppId);

    @Query("SELECT d FROM Document d WHERE d.internshipAppId.id = :appId")
    List<Document> findByApplicationId(@Param("appId") Integer appId);
    
    void deleteByInternshipAppId_Id(Integer applicationId);
    
}
