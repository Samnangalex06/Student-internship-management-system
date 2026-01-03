

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
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    public Student create(Student s) {
        return repo.save(s);
    }

    public Student update(Integer id, Student s) {
        Student existing = getById(id);
        existing.setFullName(s.getFullName());
        existing.setDepartment(s.getDepartment());
        existing.setPhoneNumber(s.getPhoneNumber());
        return repo.save(existing);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
