package project.demo.repository;

<<<<<<< HEAD
public class EvaluationRepository {
    
=======
import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.entity.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    List<Evaluation> findByApplication_Id(Integer applicationId);

>>>>>>> origin/main
}
