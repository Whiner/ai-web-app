package org.donntu.ai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Diagnosis;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosesListResponse {
    private Set<Diagnosis> diagnoses;
}
