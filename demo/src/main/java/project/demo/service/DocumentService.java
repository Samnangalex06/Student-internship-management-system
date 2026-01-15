package project.demo.service;

import project.demo.entity.Document;
import project.demo.entity.Application;
import project.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.entity.Student;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void saveDocuments(
            MultipartFile[] files,
            Student student,
            Application application
    ) throws IOException {

        if (files == null || files.length == 0) return;

        Path uploadPath = Paths.get("uploads");
        Files.createDirectories(uploadPath);

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
                String originalName = file.getOriginalFilename();
                String uniqueName = UUID.randomUUID() + "_" + originalName;

                Path filePath = uploadPath.resolve(uniqueName);
                Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING);

                Document document = new Document();
                document.setFileName(file.getOriginalFilename());
                document.setFilePath(filePath.toString());
                document.setStudentId(student);
                document.setInternshipAppId(application);

                documentRepository.save(document);
        }
    }
    public List<Document> getDocumentsByApplication(Application application) {
        return documentRepository.findByInternshipAppId(application);
    }
    public Document getById(Integer id) {
        return documentRepository.findById(id).orElse(null);
    }
}

