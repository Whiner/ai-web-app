package org.donntu.ai.backend.service;

import org.donntu.ai.backend.dto.DiagnosesListResponse;
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
import java.util.stream.Collectors;

@Service
public class DiagnosesService {
    private final DiagnosesRepository diagnosesRepository;
    private final SymptomsRepository symptomsRepository;

    private Set<Diagnosis> diagnoses = new HashSet<>();
    private Set<Symptom> symptoms = new HashSet<>();

    @Autowired
    public DiagnosesService(DiagnosesRepository diagnosesRepository, SymptomsRepository symptomsRepository) {
        this.diagnosesRepository = diagnosesRepository;
        this.symptomsRepository = symptomsRepository;

        diagnoses.addAll(SetsUtils.iterableToSet(diagnosesRepository.findAll()));
        symptoms.addAll(SetsUtils.iterableToSet(symptomsRepository.findAll()));
    }

    public DiagnosesListResponse getDiagnosesBySymptoms(Set<Symptom> symptoms) {
        Set<Diagnosis> diagnosesList = new HashSet<>();
        diagnoses.forEach(diagnosis -> {
            if (isDiagnosisContainsAllSymptoms(diagnosis, symptoms)) {
                diagnosesList.add(diagnosis);
            }
        });
        return new DiagnosesListResponse(diagnosesList);
    }

    private boolean isDiagnosisContainsAllSymptoms(Diagnosis diagnosis, Set<Symptom> symptoms) {
        Set<Symptom> diagnosisSymptoms = diagnosis.getSymptoms();
        for (Symptom symptom : symptoms) {
            if (!diagnosisSymptoms.contains(symptom)) {
                return false;
            }
        }
        return true;
    }

    private boolean isHaveSymptomsInput(Diagnosis source, Diagnosis dest) {
        if (source.getSymptoms().size() > dest.getSymptoms().size()) {
            return isDiagnosisContainsAllSymptoms(source, dest.getSymptoms());
        } else {
            return isDiagnosisContainsAllSymptoms(dest, source.getSymptoms());
        }
    }

    private boolean isAnyDiagnosisHaveSymptomsInput(Diagnosis diagnosis) {
        return diagnoses
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
        if (findSymptomByName(name) == null) { // в локальном списке искать
            Symptom saved = symptomsRepository.save(new Symptom(name));
            symptoms.add(saved);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateSymptom(Symptom symptom) throws Exception {
        Optional<Symptom> symptomById = getSymptomById(symptom.getId());
        if (symptomById.isPresent()) {
            Symptom saved = symptomsRepository.save(symptom);
            symptoms.remove(symptomById.get());
            return symptoms.add(saved);
        } else {
            throw new Exception("Такого симптома не существует");
        }
    }

    public boolean deleteSymptom(Long id) throws Exception {
        Optional<Symptom> symptomById = getSymptomById(id);

        if(symptomById.isPresent()) {
            Symptom symptom = symptomById.get();
            Set<Diagnosis> diagnosisContainsThisSymptom = diagnoses
                    .stream()
                    .filter(diagnosis -> diagnosis.getSymptoms().contains(symptom))
                    .map(diagnosis -> {
                        HashSet<Symptom> symptoms = new HashSet<>(diagnosis.getSymptoms());
                        symptoms.remove(symptom);
                        return new Diagnosis(diagnosis.getId(), diagnosis.getName(), symptoms);
                    }).collect(Collectors.toSet());
            for (Diagnosis diagnosis : diagnosisContainsThisSymptom) {
                if(isAnyDiagnosisHaveSymptomsInput(diagnosis)) {
                    return false;
                }
            }
            symptomsRepository.delete(symptom);
            symptoms.remove(symptom);
            diagnosisContainsThisSymptom.forEach(diagnosis -> diagnosis.getSymptoms().remove(symptom));
            return true;
        } else {
            throw new Exception("Такого симптома не существует");
        }
    }

    @Transactional
    public void addDiagnosis(Diagnosis diagnosis) throws Exception {
        if (isAnyDiagnosisHaveSymptomsInput(diagnosis)
                || findDiagnosisByName(diagnosis.getName()) != null) {
            throw new Exception("Данный диагноз образует коллизии");
        } else {
            Set<Symptom> fromBase = new HashSet<>();
            diagnosis.getSymptoms().forEach(symptom -> {
                Optional<Symptom> symptomById = getSymptomById(symptom.getId());
                symptomById.ifPresent(fromBase::add);
            });

            diagnosis.setSymptoms(new HashSet<>());
            Diagnosis saved = diagnosesRepository.save(diagnosis);

            fromBase.forEach(symptom -> {
                Set<Diagnosis> diagnoses = symptom.getDiagnoses();
                diagnoses.add(saved);
            });

            saved.getSymptoms().addAll(fromBase);
            try {
                Diagnosis saved1 = diagnosesRepository.save(diagnosis);
                diagnoses.add(saved1);
            } catch (Exception e) {
                fromBase.forEach(symptom -> {
                    Set<Diagnosis> diagnoses = symptom.getDiagnoses();
                    diagnoses.remove(saved);
                });
            }
        }
    }

    public boolean updateDiagnosis(Diagnosis diagnosis) throws Exception {
        Optional<Diagnosis> updatingDiagnosis = getDiagnosisById(diagnosis.getId());
        if (updatingDiagnosis.isPresent()) {
            Set<Diagnosis> listWithoutUpdatingDiagnosis = new HashSet<>(diagnoses);
            listWithoutUpdatingDiagnosis.remove(updatingDiagnosis.get());
            if (isAnyDiagnosisHaveSymptomsInput(diagnosis, listWithoutUpdatingDiagnosis)) {
                return false;
            } else {
                Diagnosis saved = diagnosesRepository.save(diagnosis);
                diagnoses.remove(updatingDiagnosis.get());
                diagnoses.add(saved);
                return true;
            }
        } else {
            throw new Exception("Такого диагноза не существует");
        }
    }

    public boolean deleteDiagnosis(Long id) {
        Optional<Diagnosis> byId = getDiagnosisById(id);
        if (byId.isPresent()) {
            diagnosesRepository.delete(byId.get());
            return diagnoses.remove(byId.get());
        } else {
            return false;
        }
    }

    private Optional<Diagnosis> getDiagnosisById(Long id) {
        return diagnoses
                .stream()
                .filter(diagnosis -> diagnosis.getId().equals(id))
                .findFirst();
    }

    private Optional<Symptom> getSymptomById(Long id) {
        return symptoms
                .stream()
                .filter(symptom -> symptom.getId().equals(id))
                .findFirst();
    }

    private Symptom findSymptomByName(String name) {
        for (Symptom symptom : symptoms) {
            if (symptom.getName().equals(name)) {
                return symptom;
            }
        }
        return null;
    }

    private Diagnosis findDiagnosisByName(String name) {
        for (Diagnosis diagnosis : diagnoses) {
            if (diagnosis.getName().equals(name)) {
                return diagnosis;
            }
        }
        return null;
    }

    public Set<Symptom> getAllSymptoms() {
        return symptoms;
    }

    public Set<Diagnosis> getAllDiagnoses() {
        return diagnoses;
    }

}
