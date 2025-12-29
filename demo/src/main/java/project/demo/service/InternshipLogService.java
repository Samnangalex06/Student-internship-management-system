package project.demo.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.demo.entity.InternshipLog;
import project.demo.repository.InternshipLogRepository;
import project.demo.repository.spec.InternshipLogSpecifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class InternshipLogService {

    private final InternshipLogRepository logRepository;

    public InternshipLogService(InternshipLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(String action, String entityType, Long entityId, String description, String performedBy) {
        InternshipLog log = new InternshipLog();
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setDescription(description);
        log.setPerformedBy(performedBy == null || performedBy.isBlank() ? "UNKNOWN" : performedBy);
        logRepository.save(log);
    }

    public List<InternshipLog> filter(LocalDate startDate, LocalDate endDate, String action, String performedBy) {
        LocalDateTime start = (startDate == null) ? null : startDate.atStartOfDay();
        LocalDateTime end = (endDate == null) ? null : endDate.atTime(LocalTime.MAX);

        Specification<InternshipLog> spec =
                Specification.where(InternshipLogSpecifications.createdAtBetween(start, end))
                        .and(InternshipLogSpecifications.actionContains(action))
                        .and(InternshipLogSpecifications.performedByContains(performedBy));

        return logRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public List<InternshipLog> latest(int limit) {
        // Quick way: get all sorted desc and cut list (works fine for small apps)
        List<InternshipLog> all = logRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return all.size() <= limit ? all : all.subList(0, limit);
    }
}
