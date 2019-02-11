package org.donntu.ai.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.dto.diagnoses.DiagnosisRequest;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "diagnoses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "diagnosis",
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<DiagnosesSymptoms> diagnosesSymptoms = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Diagnosis diagnosis = (Diagnosis) o;
        return Objects.equals(id, diagnosis.id) &&
                name.toLowerCase().trim().equals(diagnosis.name.toLowerCase().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Diagnosis(String name) {
        this.name = name;
    }

    public Set<Symptom> getSymptoms() {
        return diagnosesSymptoms
                .stream()
                .map(DiagnosesSymptoms::getSymptom)
                .collect(Collectors.toSet());
    }

    public static Diagnosis of(DiagnosisRequest request) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName(request.getName());
        diagnosis.setDiagnosesSymptoms(
                request
                        .getSymptoms()
                        .stream()
                        .map(symptomWithFactors -> {
                            DiagnosesSymptoms diagnosesSymptoms = new DiagnosesSymptoms();
                            diagnosesSymptoms.setDiagnosis(diagnosis);
                            diagnosesSymptoms.setSymptom(
                                    new Symptom(
                                            symptomWithFactors.getId(),
                                            symptomWithFactors.getName()
                                    )
                            );
                            diagnosesSymptoms.setConfidence(symptomWithFactors.getConfidence());
                            diagnosesSymptoms.setMistrust(symptomWithFactors.getMistrust());
                            return diagnosesSymptoms;
                        }).collect(Collectors.toSet())
        );
        return diagnosis;
    }
}
