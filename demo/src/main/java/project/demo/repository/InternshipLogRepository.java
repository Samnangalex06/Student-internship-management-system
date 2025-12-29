package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.demo.entity.InternshipLog;

public interface InternshipLogRepository
        extends JpaRepository<InternshipLog, Long>, JpaSpecificationExecutor<InternshipLog> {
}
