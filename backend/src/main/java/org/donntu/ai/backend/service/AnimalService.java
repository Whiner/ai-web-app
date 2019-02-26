package org.donntu.ai.backend.service;

import org.donntu.ai.backend.dto.animals.AnimalResponse;
import org.donntu.ai.backend.dto.animals.SignResponse;
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

    public AnimalResponse getAnimalByAcceptedSigns() throws Exception {
        if (acceptedSigns.size() != requiredSignCount) {
            return AnimalResponse.of(acceptedSigns);
        }
        if(possibleAnswers.size() != 1) {
            throw new Exception("Единый ответ не найден. Количество попадающих под данные признаки больше 1");
        }
        return AnimalResponse.of(possibleAnswers.stream().findFirst().orElse(null));
    }

    public SignResponse nextQuestion(Boolean lastAnswer) throws Exception {
        if (lastSign == null) {
            remainingSigns = new ArrayList<>(getAllSigns());
            possibleAnswers = getAllAnimals();
            acceptedSigns = new HashSet<>();
        } else {
            if (lastAnswer) {
                acceptedSigns.add(lastSign);
                possibleAnswers = possibleAnswers
                        .stream()
                        .filter(animal -> isAnimalContainsAllSign(animal, acceptedSigns))
                        .collect(Collectors.toSet());
                if (acceptedSigns.size() == requiredSignCount) {
                    if (possibleAnswers.size() > 1) {
                        throw new Exception("Не удалось определить животное. " +
                                "По данным признакам найдено " + possibleAnswers.size() + " результатов");
                    }
                    return null;
                }
            } else {
                possibleAnswers = possibleAnswers
                        .stream()
                        .filter(animal -> !animal.getSigns().contains(lastSign))
                        .collect(Collectors.toSet());
            }
            remainingSigns = getUniqueSighsFromAnimalList(possibleAnswers);
            remainingSigns = remainingSigns
                    .stream()
                    .filter(sign -> !acceptedSigns.contains(sign))
                    .collect(Collectors.toList());
        }
        if(remainingSigns.isEmpty()) {
            return null;
        }
        int nextSignIndex = random.nextInt(remainingSigns.size());
        lastSign = remainingSigns.get(nextSignIndex);
        remainingSigns.remove(lastSign);
        return SignResponse.of(lastSign);
    }

    private List<AnimalSign> getUniqueSighsFromAnimalList(Set<Animal> animals) {
        List<AnimalSign> animalSigns = new ArrayList<>();
        for (Animal animal : animals) {
            Set<AnimalSign> signs = animal.getSigns();
            for (AnimalSign sign : signs) {
                if(!animalSigns.contains(sign)) {
                    animalSigns.add(sign);
                }
            }
        }
        return animalSigns;
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

    public Set<AnimalSign> getAcceptedSigns() {
        return acceptedSigns;
    }

    public void addAnimal(Animal animal) {

    }
}
