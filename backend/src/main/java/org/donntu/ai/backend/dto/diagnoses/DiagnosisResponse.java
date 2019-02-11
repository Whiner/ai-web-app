package org.donntu.ai.backend.dto.diagnoses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Diagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisResponse {
    private Long id;
    private String name;
    private Float confidence;
    private List<SymptomWithFactors> symptoms;

    public static DiagnosisResponse of(Diagnosis diagnosis) {
        DiagnosisResponse diagnosisResponse = new DiagnosisResponse();
        diagnosisResponse.setId(diagnosis.getId());
        diagnosisResponse.setName(diagnosis.getName());
        diagnosisResponse.setSymptoms(diagnosis.getDiagnosesSymptoms().stream().map(diagnosesSymptoms ->
                new SymptomWithFactors(
                        diagnosesSymptoms.getSymptom().getId(),
                        diagnosesSymptoms.getSymptom().getName(),
                        diagnosesSymptoms.getConfidence(),
                        diagnosesSymptoms.getMistrust()
                )
        ).collect(Collectors.toList()));
        return diagnosisResponse;
    }

    public SymptomWithFactors getSymptomById(Long id) {
        for (SymptomWithFactors symptom : symptoms) {
            if (symptom.getId().equals(id)) {
                return symptom;
            }
        }
        return null;
    }
}
