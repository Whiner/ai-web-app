package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.diagnoses.DiagnosesBySymptomsRequest;
import org.donntu.ai.backend.dto.diagnoses.DiagnosisRequest;
import org.donntu.ai.backend.dto.diagnoses.DiagnosisResponse;
import org.donntu.ai.backend.entity.Symptom;
import org.donntu.ai.backend.exception.ActionMakesCollisionsException;
import org.donntu.ai.backend.exception.AlreadyExistException;
import org.donntu.ai.backend.exception.NotExistException;
import org.donntu.ai.backend.service.DiagnosesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosesController {
    private final DiagnosesService diagnosesService;

    @Autowired
    public DiagnosesController(DiagnosesService diagnosesService) {
        this.diagnosesService = diagnosesService;
    }

    @PostMapping("/diagnoses-by-symptoms")
    public Set<DiagnosisResponse> getDiagnosesBySymptoms(@RequestBody DiagnosesBySymptomsRequest request, Boolean confidence) {
        if (confidence) {
            return diagnosesService.getDiagnosesBySymptomsWithConfidence(request.getSymptoms())
                    .stream()
                    .filter(diagnosisResponse -> diagnosisResponse.getConfidence() >= 0.5)
                    .sorted((o1, o2) -> {
                        if (o1.getConfidence() < o2.getConfidence()) {
                            return 1;
                        } else if (o1.getConfidence().equals(o2.getConfidence())) {
                            return 0;
                        } else {
                            return -1;
                        }
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } else {
            return diagnosesService.getDiagnosesBySymptoms(request.getSymptoms())
                    .stream()
                    .sorted((o1, o2) -> {
                        if (o1.getId() > o2.getId()) {
                            return 1;
                        } else if (o1.getId().equals(o2.getId())) {
                            return 0;
                        } else {
                            return -1;
                        }
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @PostMapping("/add")
    public void addDiagnosis(@RequestBody DiagnosisRequest request) throws ActionMakesCollisionsException {
        diagnosesService.addDiagnosis(request);
    }

    @PostMapping("/{id}/del")
    public void delDiagnosis(@PathVariable Long id) throws NotExistException {
        diagnosesService.deleteDiagnosis(id);
    }

    @PostMapping("/{id}/update")
    public void updateDiagnosis(@PathVariable Long id, @RequestBody DiagnosisRequest diagnosis) throws ActionMakesCollisionsException, NotExistException {
        diagnosesService.updateDiagnosis(id, diagnosis);
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
    public Set<DiagnosisResponse> getAllDiagnoses() {
        return diagnosesService.getAllDiagnoses()
                .stream()
                .map(DiagnosisResponse::of)
                .collect(Collectors.toSet());
    }

}
