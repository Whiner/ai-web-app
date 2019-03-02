package org.donntu.ai.backend.controller;

import org.donntu.ai.backend.dto.MessageResponse;
import org.donntu.ai.backend.dto.animals.AddAnimalRequest;
import org.donntu.ai.backend.dto.animals.AnimalResponse;
import org.donntu.ai.backend.dto.animals.SignDto;
import org.donntu.ai.backend.entity.Animal;
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
    public SignDto getNextQuestion(@RequestParam(required = false) Boolean lastAnswer) throws Exception {
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
    public MessageResponse addAnimal(@RequestBody AddAnimalRequest request) {
        try {
            animalService.addAnimal(request.getName(), request.getSigns());
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
    }

    @PostMapping("/sign/add")
    public MessageResponse addSign(@RequestBody String name) {
        try {
            animalService.addSign(name);
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
    }

    @PostMapping("/animal/{id}")
    public MessageResponse updateAnimal(@PathVariable Long id, @RequestBody AddAnimalRequest request) {
        try {
            animalService.updateAnimal(id, request.getName(), request.getSigns());
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
    }

    @PostMapping("/sign/{id}")
    public MessageResponse updateSign(@PathVariable Long id, String name) {
        try {
            animalService.updateSign(id, name);
            return new MessageResponse("", 200);
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), 418);
        }
    }

    @PostMapping("/animal/{id}/del")
    public boolean delAnimal(@PathVariable Long id) {
        return animalService.deleteAnimal(id);
    }

    @PostMapping("/sign/{id}/del")
    public boolean delSign(@PathVariable Long id) {
        return animalService.deleteSign(id);
    }

}
