package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.animals.AnimalResponse;
import org.donntu.ai.backend.dto.animals.SignResponse;
import org.donntu.ai.backend.entity.AnimalSign;
import org.donntu.ai.backend.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animals")
public class AnimalsController {
    private final AnimalService animalService;

    @Autowired
    public AnimalsController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/next-question")
    public SignResponse getNextQuestion(@RequestParam(required = false) Boolean lastAnswer) throws Exception {
        return animalService.nextQuestion(lastAnswer);
    }

    @PostMapping("/clear")
    public void clear() {
        animalService.clear();
    }

    @GetMapping("/answer")
    public AnimalResponse getAnswer() throws Exception {
        return animalService.getAnimalByAcceptedSigns();
    }

    @GetMapping("/accepted-signs")
    public Set<SignResponse> getAcceptedSigns() {
        return animalService
                .getAcceptedSigns()
                .stream()
                .map(SignResponse::of)
                .collect(Collectors.toSet());
    }

    @GetMapping("/signs")
    public Set<SignResponse> getAllSigns() {
        return animalService
                .getAllSigns()
                .stream()
                .map(SignResponse::of)
                .collect(Collectors.toSet());
    }


    /*@PostMapping("/add")
    public MessageResponse addDiagnosis(@RequestBody AddDiagnosisRequest request) {

    }

    @PostMapping("/{id}/del")
    public boolean delDiagnosis(@PathVariable Long id) {
    }

    @PostMapping("/update")
    public MessageResponse updateDiagnosis(@RequestBody Diagnosis diagnosis) {

    }

    @GetMapping("/symptoms")
    public SymptomsListResponse getAllSymptoms() {
    }

    @PostMapping("/symptom/add")
    public boolean addSymptom(@RequestParam String name) {
    }

    @PostMapping("/symptom/update")
    public MessageResponse updateSymptom(@RequestBody Symptom symptom) {

    }

    @PostMapping("/symptom/{id}/del")
    public MessageResponse delSymptom(@PathVariable Long id) {

    }*/

}
