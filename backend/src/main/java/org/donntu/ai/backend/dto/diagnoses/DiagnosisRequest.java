package org.donntu.ai.backend.dto.diagnoses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisRequest {
    private String name;
    private Set<SymptomWithFactors> symptoms;
}
