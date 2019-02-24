package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.*;
import org.donntu.ai.backend.dto.diagnoses.AddDiagnosisRequest;
import org.donntu.ai.backend.dto.diagnoses.DiagnosesBySymptomsRequest;
import org.donntu.ai.backend.dto.diagnoses.DiagnosesListResponse;
import org.donntu.ai.backend.dto.diagnoses.SymptomsListResponse;
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
    public MessageResponse addDiagnosis(@RequestBody AddDiagnosisRequest request) {
        try {
            diagnosesService.addDiagnosis(new Diagnosis(request.getName(), request.getSymptoms()));
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
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
        return diagnosesService.addSymptom(name);
    }

    @PostMapping("/symptom/update")
    public MessageResponse updateSymptom(@RequestBody Symptom symptom) {
        try {
            diagnosesService.updateSymptom(symptom);
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
    }

    @PostMapping("/symptom/{id}/del")
    public MessageResponse delSymptom(@PathVariable Long id) {
        try {
            diagnosesService.deleteSymptom(id);
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
    }

    @GetMapping("/all")
    public DiagnosesListResponse getAllDiagnoses() {
        return new DiagnosesListResponse(diagnosesService.getAllDiagnoses());
    }

}
