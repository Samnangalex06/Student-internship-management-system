package project.demo.service;

<<<<<<< HEAD
public class EvaluationService {
    
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.demo.entity.Evaluation;
import project.demo.repository.EvaluationRepository;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Evaluation addEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public Evaluation updateEvaluation(Integer id, Evaluation evaluation) {
        Evaluation existing = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));

        existing.setComment(evaluation.getComment());
        return evaluationRepository.save(existing);
    }

    public void deleteEvaluation(Integer id) {
        evaluationRepository.deleteById(id);
    }

    public List<Evaluation> getByApplication(Integer applicationId) {
        return evaluationRepository.findByApplication_Id(applicationId);
    }
>>>>>>> origin/main
}
