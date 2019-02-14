package org.donntu.ai.backend.dto;

import lombok.Data;
import org.donntu.ai.backend.entity.Symptom;

import java.util.Set;

@Data
public class AddDiagnosisRequest {
    private String name;
    private Set<Symptom> symptoms;
}
