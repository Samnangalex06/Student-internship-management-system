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

    public Student getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
    }

    public Student create(Student s) {
        if (repo.existsByEmail(s.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return repo.save(s);
    }

    public Student update(Integer id, Student updated) {
        Student existing = getById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        return repo.save(existing);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
