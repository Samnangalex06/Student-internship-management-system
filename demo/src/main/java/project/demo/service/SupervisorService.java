package project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.demo.repository.SupervisorRepository;

@Service
public class SupervisorService {
    @Autowired
    private SupervisorRepository sup_Repository;

    public boolean isSupervisor(Integer userId){
        return sup_Repository.existsByUserId(userId);
    }
}
