package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.demo.entity.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    // This one is fine
    List<Evaluation> findByApplicationId(Integer applicationId);

    // ✅ FIXED: use studentId field instead of non-existent a.student
    @Query("""
        SELECT DISTINCT e
        FROM Evaluation e
        JOIN e.application a
        WHERE a.studentId = :studentId
    """)
    List<Evaluation> findByStudentId(@Param("studentId") Integer studentId);
}
