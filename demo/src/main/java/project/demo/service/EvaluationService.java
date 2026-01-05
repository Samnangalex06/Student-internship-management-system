package project.demo.service;

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

    public Evaluation updateEvaluation(Long id, Evaluation evaluation) {
        Evaluation existing = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));

        existing.setScore(evaluation.getScore());
        existing.setComment(evaluation.getComment());
        return evaluationRepository.save(existing);
    }

    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    public List<Evaluation> getByApplication(Long applicationId) {
        return evaluationRepository.findByApplication_Id(applicationId);
    }
}
