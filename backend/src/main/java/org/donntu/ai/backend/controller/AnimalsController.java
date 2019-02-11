package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.animals.AddAnimalRequest;
import org.donntu.ai.backend.dto.animals.AnimalResponse;
import org.donntu.ai.backend.dto.animals.SignDto;
import org.donntu.ai.backend.exception.AlreadyExistException;
import org.donntu.ai.backend.exception.NotExistException;
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
    public SignDto getNextQuestion(@RequestParam(required = false) Boolean lastAnswer) {
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
    public Set<SignDto> getAcceptedSigns() {
        return animalService
                .getAcceptedSigns()
                .stream()
                .map(SignDto::of)
                .collect(Collectors.toSet());
    }

    @GetMapping("/signs")
    public Set<SignDto> getAllSigns() {
        return animalService
                .getAllSigns()
                .stream()
                .map(SignDto::of)
                .collect(Collectors.toSet());
    }

    @GetMapping("/animals")
    public Set<AnimalResponse> getAllAnimals() {
        return animalService
                .getAllAnimals()
                .stream()
                .map(AnimalResponse::of)
                .collect(Collectors.toSet());
    }

    @PostMapping("/animal/add")
    public void addAnimal(@RequestBody AddAnimalRequest request) throws AlreadyExistException {
        animalService.addAnimal(request.getName(), request.getSigns());
    }

    @PostMapping("/sign/add")
    public void addSign(@RequestParam String name) throws AlreadyExistException {
        animalService.addSign(name);
    }

    @PostMapping("/animal/{id}")
    public void updateAnimal(@PathVariable Long id, @RequestBody AddAnimalRequest request) throws AlreadyExistException, NotExistException {
        animalService.updateAnimal(id, request.getName(), request.getSigns());
    }

    @PostMapping("/sign/{id}")
    public void updateSign(@PathVariable Long id, String name) throws AlreadyExistException, NotExistException {
        animalService.updateSign(id, name);
    }

    @PostMapping("/animal/{id}/del")
    public void delAnimal(@PathVariable Long id) throws NotExistException {
        animalService.deleteAnimal(id);
    }

    @PostMapping("/sign/{id}/del")
    public void delSign(@PathVariable Long id) throws NotExistException {
        animalService.deleteSign(id);
    }

}
