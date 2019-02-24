package org.donntu.ai.backend.dto.diagnoses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Symptom;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymptomsListResponse {
    private Set<Symptom> symptoms;
}
