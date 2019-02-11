package org.donntu.ai.backend.service;

import org.donntu.ai.backend.dto.DiagnosisBySymptomsResponse;
import org.donntu.ai.backend.entity.Diagnosis;
import org.donntu.ai.backend.entity.Symptom;
import org.donntu.ai.backend.repository.DiagnosesRepository;
import org.donntu.ai.backend.repository.SymptomsRepository;
import org.donntu.ai.backend.utils.SetsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DiagnosesService {
    private final DiagnosesRepository diagnosesRepository;
    private final SymptomsRepository symptomsRepository;

    private Set<Diagnosis> diagnoses = new HashSet<>();
    private Set<Symptom> symptoms = new HashSet<>();

    private boolean dataChanged = true;

    @Autowired
    public DiagnosesService(DiagnosesRepository diagnosesRepository, SymptomsRepository symptomsRepository) {
        this.diagnosesRepository = diagnosesRepository;
        this.symptomsRepository = symptomsRepository;

        diagnoses.addAll(SetsUtils.iterableToSet(diagnosesRepository.findAll()));
        symptoms.addAll(SetsUtils.iterableToSet(symptomsRepository.findAll()));
//        updateDataSets();
    }

    /*private void updateDataSets() {
        if (dataChanged) {
            diagnoses.clear();
            symptoms.clear();

            diagnoses.addAll(SetsUtils.iterableToSet(diagnosesRepository.findAll()));
            symptoms.addAll(SetsUtils.iterableToSet(symptomsRepository.findAll()));
            dataChanged = false;
        }
    }*/

    public DiagnosisBySymptomsResponse getDiagnosesBySymptoms(Set<Symptom> symptoms) {
//        updateDataSets();
        Set<Diagnosis> diagnosesList = new HashSet<>();
        diagnoses.forEach(diagnosis -> {
            if (isDiagnosisContainsAllSymptoms(diagnosis, symptoms)) {
                diagnosesList.add(diagnosis);
            }
        });
        return new DiagnosisBySymptomsResponse(diagnosesList);
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

    public boolean addSymptom(Symptom symptom) {
        if(!symptoms.contains(symptom)) {
            Symptom saved = symptomsRepository.save(symptom);
            symptoms.add(saved);
            return true;
        } else {
            return false;
        }
    }
}
