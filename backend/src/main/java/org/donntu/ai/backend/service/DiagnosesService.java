package org.donntu.ai.backend.service;

import org.donntu.ai.backend.repository.DiagnosesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagnosesService {
    private final DiagnosesRepository diagnosesRepository;

    @Autowired
    public DiagnosesService(DiagnosesRepository repository) {
        this.diagnosesRepository = repository;
    }

    public void test(){
        System.out.println(diagnosesRepository.findAll());
    }
}
