package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.*;
import org.donntu.ai.backend.entity.Diagnosis;
import org.donntu.ai.backend.entity.Symptom;
import org.donntu.ai.backend.service.DiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosesController {
    private final DiagnosesService diagnosesService;

    @Autowired
    public DiagnosesController(DiagnosesService diagnosesService) {
        this.diagnosesService = diagnosesService;
    }

    @PostMapping("/diagnoses-by-symptoms")
    public DiagnosesListResponse getDiagnosesBySymptoms(@RequestBody DiagnosesBySymptomsRequest request){
        return diagnosesService.getDiagnosesBySymptoms(request.getSymptoms());
    }

    @PostMapping("/add")
    public boolean addDiagnosis(@RequestBody AddDiagnosisRequest request) {
        return diagnosesService.addDiagnosis(new Diagnosis(request.getName(), request.getSymptoms()));
    }

    @PostMapping("/{id}/del")
    public boolean delDiagnosis(@PathVariable Long id) {
        return diagnosesService.deleteDiagnosis(id);
    }

    @PostMapping("/update")
    public MessageResponse updateDiagnosis(@RequestBody Diagnosis diagnosis) {
        try {
            diagnosesService.updateDiagnosis(diagnosis);
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
    }

    @GetMapping("/symptoms")
    public SymptomsListResponse getAllSymptoms() {
        return new SymptomsListResponse(diagnosesService.getAllSymptoms());
    }

    @PostMapping("/symptom/add")
    public boolean addSymptom(@RequestParam String name) {
        return diagnosesService.addSymptom(new Symptom(name));
    }

    @GetMapping("/all")
    public DiagnosesListResponse getAllDiagnoses() {
        return new DiagnosesListResponse(diagnosesService.getAllDiagnoses());
    }

}
