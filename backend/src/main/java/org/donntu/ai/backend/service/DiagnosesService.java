package org.donntu.ai.backend.service;

import org.donntu.ai.backend.dto.diagnoses.DiagnosesListResponse;
import org.donntu.ai.backend.entity.Diagnosis;
import org.donntu.ai.backend.entity.Symptom;
import org.donntu.ai.backend.repository.DiagnosesRepository;
import org.donntu.ai.backend.repository.SymptomsRepository;
import org.donntu.ai.backend.utils.SetsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DiagnosesService {
    private final DiagnosesRepository diagnosesRepository;
    private final SymptomsRepository symptomsRepository;


    @Autowired
    public DiagnosesService(DiagnosesRepository diagnosesRepository, SymptomsRepository symptomsRepository) {
        this.diagnosesRepository = diagnosesRepository;
        this.symptomsRepository = symptomsRepository;
    }

    public DiagnosesListResponse getDiagnosesBySymptoms(Set<Symptom> symptoms) {
        Set<Diagnosis> responseList = new HashSet<>();
        Set<Diagnosis> diagnoses = getAllDiagnoses();
        diagnoses.forEach(diagnosis -> {
            if (isDiagnosisContainsAllSymptoms(diagnosis, symptoms)) {
                responseList.add(diagnosis);
            }
        });
        return new DiagnosesListResponse(responseList);
    }

    private boolean isDiagnosisContainsAllSymptoms(Diagnosis diagnosis, Set<Symptom> symptoms) {
        return diagnosis.getSymptoms().containsAll(symptoms);
        /*for (Symptom symptom : symptoms) {
            if (!diagnosisSymptoms.contains(symptom)) {
                return false;
            }
        }
        return true;*/
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

    public boolean addSymptom(String name) {
        if (!symptomsRepository.findByName(name).isPresent()) {
            symptomsRepository.save(new Symptom(name));
            return true;
        } else {
            return false;
        }
    }

    public void updateSymptom(Symptom symptom) throws Exception {
        Optional<Symptom> symptomById = symptomsRepository.findById(symptom.getId());
        if (symptomById.isPresent()) {
            symptomsRepository.save(symptom);
        } else {
            throw new Exception("Такого симптома не существует");
        }
    }

    public void deleteSymptom(Long id) throws Exception {
        Optional<Symptom> symptomById = symptomsRepository.findById(id);
        if (symptomById.isPresent()) {
            Set<Diagnosis> diagnoses = getAllDiagnoses();
            Symptom symptom = symptomById.get();
            diagnoses.stream()
                    .filter(diagnosis -> diagnosis.getSymptoms().contains(symptom))
                    .forEach(diagnosis -> {
                        Set<Symptom> symptoms = diagnosis.getSymptoms();
                        symptoms.remove(symptom);
                    });
            for (Diagnosis diagnosis : diagnoses) {
                if (isAnyDiagnosisHaveSymptomsInput(diagnosis)) {
                    throw new Exception("Удаление данного симптома приводит к образованию коллизии");
                }
            }

            symptomsRepository.delete(symptom);
        } else {
            throw new Exception("Такого симптома не существует");
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void addDiagnosis(Diagnosis diagnosis) throws Exception {
        if (isAnyDiagnosisHaveSymptomsInput(diagnosis)
                || diagnosesRepository.findByName(diagnosis.getName()).isPresent()) {
            throw new Exception("Данный диагноз образует коллизии или уже существует");
        } else {
            Set<Symptom> fromBase = new HashSet<>();
            diagnosis.getSymptoms().forEach(symptom -> {
                Optional<Symptom> symptomById = symptomsRepository.findById(symptom.getId());
                symptomById.ifPresent(fromBase::add);
            });

            diagnosis.setSymptoms(new HashSet<>());
            Diagnosis saved = diagnosesRepository.save(diagnosis);

            fromBase.forEach(symptom -> {
                Set<Diagnosis> diagnoses = symptom.getDiagnoses();
                diagnoses.add(saved);
                symptomsRepository.save(symptom);
            });

            saved.getSymptoms().addAll(fromBase);

            diagnosesRepository.save(diagnosis);

        }
    }

    public void updateDiagnosis(Diagnosis diagnosis) throws Exception {
        Optional<Diagnosis> updatingDiagnosis = diagnosesRepository.findById(diagnosis.getId());
        if (updatingDiagnosis.isPresent()) {
            Set<Diagnosis> diagnoses = getAllDiagnoses();
            Set<Diagnosis> listWithoutUpdatingDiagnosis = new HashSet<>(diagnoses);
            listWithoutUpdatingDiagnosis.remove(updatingDiagnosis.get());
            if (isAnyDiagnosisHaveSymptomsInput(diagnosis, listWithoutUpdatingDiagnosis)) {
                throw new Exception("При таких изменениях этот диагноз образует коллизии");
            } else {
                diagnosesRepository.save(diagnosis);
            }
        } else {
            throw new Exception("Такого диагноза не существует");
        }
    }

    public boolean deleteDiagnosis(Long id) {
        Optional<Diagnosis> byId = diagnosesRepository.findById(id);
        if (byId.isPresent()) {
            diagnosesRepository.delete(byId.get());
            return true;
        } else {
            return false;
        }
    }


    public Set<Symptom> getAllSymptoms() {
        return SetsUtils.iterableToSet(symptomsRepository.findAll());
    }

    public Set<Diagnosis> getAllDiagnoses() {
        return SetsUtils.iterableToSet(diagnosesRepository.findAll());
    }

}
