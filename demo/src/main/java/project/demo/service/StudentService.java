// package project.demo.service;

// public class StudentService {
    
// }

package project.demo.service;

import org.springframework.stereotype.Service;
import project.demo.entity.Student;
import project.demo.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    public Student create(Student s) {
        if (repo.existsByEmail(s.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return repo.save(s);
    }

    public Student update(Long id, Student s) {
        Student existing = getById(id);
        existing.setFullName(s.getFullName());
        existing.setEmail(s.getEmail());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
