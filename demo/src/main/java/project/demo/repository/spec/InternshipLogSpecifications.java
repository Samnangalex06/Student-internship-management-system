package project.demo.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.demo.entity.InternshipLog;

import java.time.LocalDateTime;

public class InternshipLogSpecifications {

    public static Specification<InternshipLog> actionContains(String action) {
        return (root, query, cb) -> {
            if (action == null || action.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("action")), "%" + action.toLowerCase().trim() + "%");
        };
    }

    public static Specification<InternshipLog> performedByContains(String performedBy) {
        return (root, query, cb) -> {
            if (performedBy == null || performedBy.trim().isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("performedBy")), "%" + performedBy.toLowerCase().trim() + "%");
        };
    }

    public static Specification<InternshipLog> createdAtBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return cb.conjunction();
            if (start != null && end != null) return cb.between(root.get("createdAt"), start, end);
            if (start != null) return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
            return cb.lessThanOrEqualTo(root.get("createdAt"), end);
        };
    }
}
