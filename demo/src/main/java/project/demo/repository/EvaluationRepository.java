package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.entity.Evaluation;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    List<Evaluation> findByApplication_Id(Integer applicationId);
    Optional<Evaluation> findByApplication_IdAndSupervisor_Id(
            Integer applicationId,
            Integer supervisorId
    );
    List<Evaluation> findAllByApplication_Student_Id(Integer studentId);
}