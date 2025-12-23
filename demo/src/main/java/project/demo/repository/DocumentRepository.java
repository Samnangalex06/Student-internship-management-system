package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
