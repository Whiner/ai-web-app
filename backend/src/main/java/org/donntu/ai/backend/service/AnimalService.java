package org.donntu.ai.backend.service;

import org.donntu.ai.backend.dto.animals.AnimalResponse;
import org.donntu.ai.backend.dto.animals.SignDto;
import org.donntu.ai.backend.entity.Animal;
import org.donntu.ai.backend.entity.AnimalSign;
import org.donntu.ai.backend.exception.AlreadyExistException;
import org.donntu.ai.backend.exception.AnswerNotFoundException;
import org.donntu.ai.backend.exception.NotExistException;
import org.donntu.ai.backend.repository.AnimalSignRepository;
import org.donntu.ai.backend.repository.AnimalsRepository;
import org.donntu.ai.backend.utils.SetsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.donntu.ai.backend.utils.RepositoryUtil.deleteFromRepository;

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

    public AnimalResponse getAnimalByAcceptedSigns() throws AnswerNotFoundException {
        if (acceptedSigns.size() != requiredSignCount) {
            return AnimalResponse.of(acceptedSigns);
        }
        if (possibleAnswers.size() != 1) {
            throw new AnswerNotFoundException("Не удалось определить животное. " +
                    "По данным признакам найдено " + possibleAnswers.size() + " результатов");
        }
        return AnimalResponse.of(possibleAnswers.stream().findFirst().orElse(null));
    }

    public SignDto nextQuestion(Boolean lastAnswer) {
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
        if (remainingSigns.isEmpty()) {
            return null;
        }
        int nextSignIndex = random.nextInt(remainingSigns.size());
        lastSign = remainingSigns.get(nextSignIndex);
        remainingSigns.remove(lastSign);
        return SignDto.of(lastSign);
    }

    private List<AnimalSign> getUniqueSighsFromAnimalList(Set<Animal> animals) {
        List<AnimalSign> animalSigns = new ArrayList<>();
        for (Animal animal : animals) {
            Set<AnimalSign> signs = animal.getSigns();
            for (AnimalSign sign : signs) {
                if (!animalSigns.contains(sign)) {
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

    public void addAnimal(String name, Set<SignDto> signs) throws AlreadyExistException {
        Set<Animal> allAnimals = getAllAnimals();
        Set<AnimalSign> fromRepository = getSignsFromRepository(signs);
        boolean anyMatch = allAnimals
                .stream()
                .anyMatch(a -> a.getSigns().containsAll(fromRepository));
        if (anyMatch) {
            throw new AlreadyExistException("Животное с такими признаками уже существует");
        } else {
            animalRepository.save(new Animal(name, fromRepository));
        }
    }


    public void updateAnimal(Long id, String name, Set<SignDto> signs) throws AlreadyExistException, NotExistException {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isPresent()) {
            Set<Animal> allAnimals = getAllAnimals();
            Set<AnimalSign> fromRepository = getSignsFromRepository(signs);
            boolean anyMatch = allAnimals
                    .stream()
                    .anyMatch(a -> a.getSigns().containsAll(fromRepository));
            if (anyMatch && name.equals(animal.get().getName())) {
                throw new AlreadyExistException("Животное с такими признаками уже существует");
            } else {
                animalRepository.save(new Animal(id, name, fromRepository));
            }
        } else {
            throw new NotExistException("Такого животного нет в базе");
        }
    }

    private Set<AnimalSign> getSignsFromRepository(Set<SignDto> signs) {
        Set<AnimalSign> fromDb = new HashSet<>();
        signs.forEach(signDto -> signRepository
                .findById(signDto.getId())
                .ifPresent(fromDb::add));
        return fromDb;
    }

    public void deleteAnimal(Long id) throws NotExistException {
        if(!deleteFromRepository(animalRepository, id)){
            throw new NotExistException("Такого животного не существует");
        }
    }

    public void deleteSign(Long id) throws NotExistException {
        if(!deleteFromRepository(signRepository, id)) {
            throw new NotExistException("Такого признака не существует");
        }
    }

    public void addSign(String name) throws AlreadyExistException {
        Optional<AnimalSign> byName = signRepository.findByName(name);
        if (byName.isPresent()) {
            throw new AlreadyExistException("Такой признак уже существует");
        } else {
            signRepository.save(new AnimalSign(name));
        }
    }

    public void updateSign(Long id, String name) throws AlreadyExistException, NotExistException {
        Optional<AnimalSign> byId = signRepository.findById(id);
        if (byId.isPresent()) {
            Optional<AnimalSign> byName = signRepository.findByName(name);
            if (byName.isPresent()) {
                throw new AlreadyExistException("Признак с таким именем уже существует");
            } else {
                byId.ifPresent(sign -> {
                    sign.setName(name);
                    signRepository.save(sign);
                });
            }
        } else {
            throw new NotExistException("Такого признака не существует");
        }
    }
}
