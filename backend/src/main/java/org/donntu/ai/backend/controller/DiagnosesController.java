package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.diagnoses.AddDiagnosisRequest;
import org.donntu.ai.backend.dto.diagnoses.DiagnosesBySymptomsRequest;
import org.donntu.ai.backend.entity.Diagnosis;
import org.donntu.ai.backend.entity.Symptom;
import org.donntu.ai.backend.exception.ActionMakesCollisionsException;
import org.donntu.ai.backend.exception.AlreadyExistException;
import org.donntu.ai.backend.exception.NotExistException;
import org.donntu.ai.backend.service.DiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosesController {
    private final DiagnosesService diagnosesService;

    @Autowired
    public DiagnosesController(DiagnosesService diagnosesService) {
        this.diagnosesService = diagnosesService;
    }

    @PostMapping("/diagnoses-by-symptoms")
    public Set<Diagnosis> getDiagnosesBySymptoms(@RequestBody DiagnosesBySymptomsRequest request) {
        return diagnosesService.getDiagnosesBySymptoms(request.getSymptoms());
    }

    @PostMapping("/add")
    public void addDiagnosis(@RequestBody AddDiagnosisRequest request) throws ActionMakesCollisionsException {
        diagnosesService.addDiagnosis(new Diagnosis(request.getName(), request.getSymptoms()));
    }

    @PostMapping("/{id}/del")
    public void delDiagnosis(@PathVariable Long id) throws NotExistException {
        diagnosesService.deleteDiagnosis(id);
    }

    @PostMapping("/update")
    public void updateDiagnosis(@RequestBody Diagnosis diagnosis) throws ActionMakesCollisionsException, NotExistException {
        diagnosesService.updateDiagnosis(diagnosis);
    }

    @GetMapping("/symptoms")
    public Set<Symptom> getAllSymptoms() {
        return diagnosesService.getAllSymptoms();
    }

    @PostMapping("/symptom/add")
    public void addSymptom(@RequestParam String name) throws AlreadyExistException {
        diagnosesService.addSymptom(name);
    }

    @PostMapping("/symptom/update")
    public void updateSymptom(@RequestBody Symptom symptom) throws NotExistException {
        diagnosesService.updateSymptom(symptom);
    }

    @PostMapping("/symptom/{id}/del")
    public void delSymptom(@PathVariable Long id) throws ActionMakesCollisionsException, NotExistException {
        diagnosesService.deleteSymptom(id);
    }

    @GetMapping("/all")
    public Set<Diagnosis> getAllDiagnoses() {
        return diagnosesService.getAllDiagnoses();
    }

}
