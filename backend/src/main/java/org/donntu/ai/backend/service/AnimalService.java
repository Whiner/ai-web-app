package org.donntu.ai.backend.service;

import org.donntu.ai.backend.entity.Animal;
import org.donntu.ai.backend.entity.AnimalSign;
import org.donntu.ai.backend.repository.AnimalSignRepository;
import org.donntu.ai.backend.repository.AnimalsRepository;
import org.donntu.ai.backend.utils.SetsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    private final AnimalsRepository animalRepository;
    private final AnimalSignRepository signRepository;

    private Set<Animal> possibleAnswers;
    private Set<AnimalSign> acceptedSigns;
    private List<AnimalSign> remainingSigns;

    private AnimalSign lastSign = null;

    private final Random random = new Random();
    private int requiredSignCount = 2;


    @Autowired
    public AnimalService(AnimalsRepository animalRepository, AnimalSignRepository signRepository) {
        this.animalRepository = animalRepository;
        this.signRepository = signRepository;
    }

    public Animal getAnimalByAcceptedSigns() throws Exception {
        if (acceptedSigns.size() != requiredSignCount) {
            throw new Exception("Необходимое количество ответов еще не набрано");
        }
        Set<Animal> animals = getAllAnimals();
        Set<AnimalSign> signs;
        boolean allContains;
        for (Animal animal : animals) {
            allContains = true;
            signs = animal.getSigns();
            if (signs.size() == acceptedSigns.size()) {
                for (AnimalSign sign : signs) {
                    if (!acceptedSigns.contains(sign)) {
                        allContains = false;
                        break;
                    }
                }
                if (allContains) {
                    return animal;
                }
            }
        }
        return null;
    }

    public AnimalSign nextQuestion(Boolean lastAnswer) throws Exception {
        if (lastSign == null) {
            possibleAnswers = getAllAnimals();
            remainingSigns = new ArrayList<>(getAllSigns());
            acceptedSigns = new HashSet<>();
        } else {
            if (lastAnswer) {
                acceptedSigns.add(lastSign);
                if (acceptedSigns.size() == requiredSignCount) {
                    if (possibleAnswers.size() > 1) {
                        throw new Exception("Не удалось определить животное. " +
                                "По данным признакам найдено " + possibleAnswers.size() + " результатов");
                    }
                    return null;
                } else {
                    possibleAnswers = possibleAnswers
                            .stream()
                            .filter(animal -> isAnimalContainsAllSign(animal, acceptedSigns))
                            .collect(Collectors.toSet());
                }
            } else {
                possibleAnswers = possibleAnswers
                        .stream()
                        .filter(animal -> !animal.getSigns().contains(lastSign))
                        .collect(Collectors.toSet());
            }
        }
        int nextSignIndex = random.nextInt(remainingSigns.size());
        lastSign = remainingSigns.get(nextSignIndex);
        remainingSigns.remove(lastSign);
        return lastSign;
    }

    private boolean isAnimalContainsAllSign(Animal animal, Set<AnimalSign> signs) {
        Set<AnimalSign> animalSigns = animal.getSigns();
        for (AnimalSign sign : signs) {
            if (!animalSigns.contains(sign)) {
                return false;
            }
        }
        return true;
    }

    public Set<Animal> getAllAnimals() {
        return SetsUtils.iterableToSet(animalRepository.findAll());
    }

    public Set<AnimalSign> getAllSigns() {
        return SetsUtils.iterableToSet(signRepository.findAll());
    }

    public void clear() {
        possibleAnswers = null;
        acceptedSigns = null;
        remainingSigns = null;
        lastSign = null;
    }
}
