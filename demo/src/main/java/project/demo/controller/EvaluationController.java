package project.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.demo.entity.Evaluation;
import project.demo.service.EvaluationService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    //@PreAuthorize("hasRole('SUPERVISOR')")
    public Evaluation addEvaluation(@Valid @RequestBody Evaluation evaluation) {
        return evaluationService.addEvaluation(evaluation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public Evaluation updateEvaluation(
            @PathVariable Long id,
            @Valid @RequestBody Evaluation evaluation) {
        return evaluationService.updateEvaluation(id, evaluation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public void deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
    }

    @GetMapping("/application/{applicationId}")
    public List<Evaluation> getByApplication(@PathVariable Long applicationId) {
        return evaluationService.getByApplication(applicationId);
    }
}
