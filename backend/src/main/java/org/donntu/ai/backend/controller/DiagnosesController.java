package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.DiagnosisBySymptomsRequest;
import org.donntu.ai.backend.dto.DiagnosisBySymptomsResponse;
import org.donntu.ai.backend.service.DiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosesController {
    private final DiagnosesService diagnosesService;

    @Autowired
    public DiagnosesController(DiagnosesService diagnosesService) {
        this.diagnosesService = diagnosesService;
    }

    @PostMapping("/diagnoses-by-symptoms")
    public DiagnosisBySymptomsResponse getDiagnosesBySymptoms(@RequestBody DiagnosisBySymptomsRequest request){
        return diagnosesService.getDiagnosesBySymptoms(request.getSymptoms());
    }
}
