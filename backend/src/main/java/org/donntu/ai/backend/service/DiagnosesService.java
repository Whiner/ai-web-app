package org.donntu.ai.backend.service;

import org.donntu.ai.backend.dto.diagnoses.DiagnosisRequest;
import org.donntu.ai.backend.dto.diagnoses.DiagnosisResponse;
import org.donntu.ai.backend.dto.diagnoses.SymptomWithFactors;
import org.donntu.ai.backend.entity.DiagnosesSymptoms;
import org.donntu.ai.backend.entity.Diagnosis;
import org.donntu.ai.backend.entity.Symptom;
import org.donntu.ai.backend.exception.ActionMakesCollisionsException;
import org.donntu.ai.backend.exception.AlreadyExistException;
import org.donntu.ai.backend.exception.NotExistException;
import org.donntu.ai.backend.repository.DiagnosesRepository;
import org.donntu.ai.backend.repository.DiagnosesSymptomsRepository;
import org.donntu.ai.backend.repository.SymptomsRepository;
import org.donntu.ai.backend.utils.SetsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.donntu.ai.backend.utils.RepositoryUtil.deleteFromRepository;

@Service
public class DiagnosesService {
    private final DiagnosesRepository diagnosesRepository;
    private final SymptomsRepository symptomsRepository;
    private final DiagnosesSymptomsRepository diagnosesSymptomsRepository;


    @Autowired
    public DiagnosesService(
            DiagnosesRepository diagnosesRepository,
            SymptomsRepository symptomsRepository,
            DiagnosesSymptomsRepository diagnosesSymptomsRepository) {
        this.diagnosesRepository = diagnosesRepository;
        this.symptomsRepository = symptomsRepository;
        this.diagnosesSymptomsRepository = diagnosesSymptomsRepository;
    }

    public Set<DiagnosisResponse> getDiagnosesBySymptoms(Set<Symptom> symptoms) {
        Set<Diagnosis> diagnoses = getAllDiagnoses();
        return diagnoses.stream()
                .filter(diagnosis -> isDiagnosisContainsOnlyThisSymptoms(diagnosis, symptoms))
                .map(DiagnosisResponse::of)
                .collect(Collectors.toSet());
    }

    public Set<DiagnosisResponse> getDiagnosesBySymptomsWithConfidence(Set<Symptom> symptoms) {
        Set<Diagnosis> diagnoses = getAllDiagnoses();
        Set<Diagnosis> diagnosesBySymptoms = new HashSet<>();
        symptoms.forEach(symptom ->
                        diagnoses.stream()
                                .filter(diagnosis ->
                                        diagnosis
                                                .getSymptoms()
                                                .contains(symptom))
                                .forEach(diagnosesBySymptoms::add)
        );

        return diagnosesBySymptoms.stream().map(diagnosis -> {
            DiagnosisResponse diagnosisResponse = DiagnosisResponse.of(diagnosis);
            calculateDiagnosisConfidence(diagnosisResponse,
                    symptoms.stream()
                    .filter(symptom -> diagnosis.getSymptoms().contains(symptom))
                    .collect(Collectors.toSet())
            );
            return diagnosisResponse;
        }).collect(Collectors.toSet());

    }

    private Float calculateConfidence(Set<SymptomWithFactors> symptoms) {
        Float confidence = 0.0F;
        Float mistrust = 0.0F;

        for (SymptomWithFactors value : symptoms) {
            confidence += (value.getConfidence() * (1 - confidence));
            mistrust += (value.getMistrust() * (1 - mistrust));
        }
        return confidence - mistrust;
    }

    private void calculateDiagnosisConfidence(DiagnosisResponse diagnosisResponse, Set<Symptom> symptoms) {
        Set<SymptomWithFactors> symptomsWithFactors = symptoms
                .stream()
                .map(symptom -> diagnosisResponse.getSymptomById(symptom.getId()))
                .collect(Collectors.toSet());

        diagnosisResponse.setConfidence(calculateConfidence(symptomsWithFactors));
    }

    private boolean isDiagnosisContainsAllSymptoms(Diagnosis diagnosis, Set<Symptom> symptoms) {
        return diagnosis.getSymptoms().containsAll(symptoms);
    }

    private boolean isDiagnosisContainsOnlyThisSymptoms(Diagnosis diagnosis, Set<Symptom> symptoms) {
        return isDiagnosisContainsAllSymptoms(diagnosis, symptoms) && diagnosis.getSymptoms().size() == symptoms.size();
    }

    private boolean isHaveSymptomsInput(Diagnosis source, Diagnosis dest) {
        if (source.getSymptoms().size() > dest.getSymptoms().size()) {
            return isDiagnosisContainsAllSymptoms(source, dest.getSymptoms());
        } else {
            return isDiagnosisContainsAllSymptoms(dest, source.getSymptoms());
        }
    }

    private boolean isAnyDiagnosisHaveSymptomsInput(Diagnosis diagnosis) {
        return getAllDiagnoses()
                .stream()
                .filter(value -> !value.equals(diagnosis))
                .anyMatch(value -> isHaveSymptomsInput(value, diagnosis));
    }

    private boolean isAnyDiagnosisHaveSymptomsInput(Diagnosis diagnosis, Set<Diagnosis> diagnoses) {
        return diagnoses
                .stream()
                .anyMatch(value -> isHaveSymptomsInput(value, diagnosis));
    }

    public void addSymptom(String name) throws AlreadyExistException {
        if (!symptomsRepository.findByName(name).isPresent()) {
            symptomsRepository.save(new Symptom(name));
        } else {
            throw new AlreadyExistException("Симптом с таким именем уже существует");
        }
    }

    public void updateSymptom(Symptom symptom) throws NotExistException {
        Optional<Symptom> symptomById = symptomsRepository.findById(symptom.getId());
        if (symptomById.isPresent()) {
            symptomsRepository.save(symptom);
        } else {
            throw new NotExistException("Такого симптома не существует");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSymptom(Long id) throws ActionMakesCollisionsException, NotExistException {
        Optional<Symptom> symptomById = symptomsRepository.findById(id);
        if (symptomById.isPresent()) {
            Set<Diagnosis> diagnoses = getAllDiagnoses();
            Symptom symptom = symptomById.get();
            diagnoses.stream()
                    .filter(diagnosis -> diagnosis.getSymptoms().contains(symptom))
                    .forEach(diagnosis -> {
                        Set<DiagnosesSymptoms> diagnosesSymptoms = diagnosis.getDiagnosesSymptoms();
                        diagnosesSymptoms
                                .stream()
                                .filter(value -> value.getSymptom()
                                        .equals(symptom))
                                .findFirst()
                                .ifPresent(diagnosesSymptoms::remove);
                    });
            for (Diagnosis diagnosis : diagnoses) {
                if (isAnyDiagnosisHaveSymptomsInput(diagnosis)) {
                    throw new ActionMakesCollisionsException("Удаление данного симптома приводит к образованию коллизии");
                }
            }
            symptom.getSymptomsDiagnoses().forEach(diagnosesSymptoms -> {
                diagnosesSymptoms.getDiagnosis().getDiagnosesSymptoms().remove(diagnosesSymptoms);
                diagnosesSymptoms.setDiagnosis(null);
                diagnosesSymptomsRepository.delete(diagnosesSymptoms);
            });
            symptom.setSymptomsDiagnoses(null);
            symptomsRepository.delete(symptom);
        } else {
            throw new NotExistException("Такого симптома не существует");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDiagnosis(DiagnosisRequest diagnosisRequest) throws ActionMakesCollisionsException {
        Diagnosis diagnosis = Diagnosis.of(diagnosisRequest);
        if (isAnyDiagnosisHaveSymptomsInput(diagnosis)
                || diagnosesRepository.findByName(diagnosis.getName()).isPresent()) {
            throw new ActionMakesCollisionsException("Данный диагноз образует коллизии или уже существует");
        } else {
            //сохраняем просто диагноз
            Diagnosis savedDiagnosis = diagnosesRepository.save(new Diagnosis(diagnosis.getName()));

            addConnectionsToDiagnosis(diagnosis, savedDiagnosis);

        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDiagnosis(Long id, DiagnosisRequest diagnosisRequest) throws ActionMakesCollisionsException, NotExistException {
        Diagnosis diagnosis = Diagnosis.of(diagnosisRequest);
        Optional<Diagnosis> updatingDiagnosis = diagnosesRepository.findById(id);
        if (updatingDiagnosis.isPresent()) {
            Set<Diagnosis> diagnoses = getAllDiagnoses();
            Set<Diagnosis> listWithoutUpdatingDiagnosis = new HashSet<>(diagnoses);
            Diagnosis diagnosisFromBase = updatingDiagnosis.get();
            listWithoutUpdatingDiagnosis.remove(diagnosisFromBase);
            if (isAnyDiagnosisHaveSymptomsInput(diagnosis, listWithoutUpdatingDiagnosis)) {
                throw new ActionMakesCollisionsException("При таких изменениях этот диагноз образует коллизии");
            } else {

                //удаляем все записи из таблицы соединений для этого диагноза
                diagnosisFromBase
                        .getDiagnosesSymptoms()
                        .forEach(diagnosesSymptoms -> {
                            diagnosesSymptoms.setDiagnosis(null);
                            diagnosesSymptoms.setSymptom(null);
                            diagnosesSymptomsRepository.delete(diagnosesSymptoms);
                        });
                diagnosisFromBase.getDiagnosesSymptoms().clear();

                addConnectionsToDiagnosis(diagnosis, diagnosisFromBase);

                diagnosisFromBase.setName(diagnosis.getName());
            }
        } else {
            throw new NotExistException("Такого диагноза не существует");
        }
    }

    private void addConnectionsToDiagnosis(Diagnosis newDiagnosis, Diagnosis diagnosisFromBase) {
        //добавляем записи в таблицу для связи
        Set<DiagnosesSymptoms> savedDiagnosesSymptoms = newDiagnosis.getDiagnosesSymptoms()
                .stream()
                .map(diagnosesSymptoms -> {
                    DiagnosesSymptoms entity = new DiagnosesSymptoms();
                    entity.setDiagnosis(diagnosisFromBase);
                    entity.setConfidence(diagnosesSymptoms.getConfidence());
                    entity.setMistrust(diagnosesSymptoms.getMistrust());
                    return diagnosesSymptomsRepository.save(entity);
                }).collect(Collectors.toSet());

        //связываем диагноз с таблицей для связи
        savedDiagnosesSymptoms.forEach(diagnosesSymptoms -> diagnosisFromBase.getDiagnosesSymptoms().add(diagnosesSymptoms));

        //связываем симптомы с таблицей для связи
        Iterator<Symptom> notSavedSymptoms = newDiagnosis.getSymptoms().iterator();
        savedDiagnosesSymptoms.forEach(diagnosesSymptoms -> {
            Long id = notSavedSymptoms.next().getId();
            Optional<Symptom> byId = symptomsRepository.findById(id);
            byId.ifPresent(symptom -> {
                symptom.getSymptomsDiagnoses().add(diagnosesSymptoms);
                diagnosesSymptoms.setSymptom(symptom);
            });
        });

    }

    public void deleteDiagnosis(Long id) throws NotExistException {
        if (!deleteFromRepository(diagnosesRepository, id)) {
            throw new NotExistException("Такого диагноза не существует");
        }
    }


    public Set<Symptom> getAllSymptoms() {
        return SetsUtils.iterableToSet(symptomsRepository.findAll());
    }

    public Set<Diagnosis> getAllDiagnoses() {
        return SetsUtils.iterableToSet(diagnosesRepository.findAll());
    }
}
