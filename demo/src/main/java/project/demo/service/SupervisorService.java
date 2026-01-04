package project.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.demo.Model.SupervisorDTO;
import project.demo.repository.SupervisorRepository;

@Service
public class SupervisorService {
    @Autowired
    private SupervisorRepository sup_Repository;

    public boolean isSupervisor(Integer userId){
        return sup_Repository.existsByUserId(userId);
    }
    public List<SupervisorDTO> getAllSupervisors() {
        return sup_Repository.findAll().stream()
                .map(s -> new SupervisorDTO(
                        s.getId(),
                        s.getUserId(),
                        s.getFullName(),
                        s.getEmail(),
                        s.getPhoneNumber(),
                        s.getDepartment()
                ))
                .collect(Collectors.toList());
    }

}
