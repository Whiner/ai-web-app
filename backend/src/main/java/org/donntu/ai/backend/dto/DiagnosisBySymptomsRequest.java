package org.donntu.ai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Symptom;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisBySymptomsRequest {
    private Set<Symptom> symptoms;
}
