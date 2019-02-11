package org.donntu.ai.backend.dto.diagnoses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Symptom;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosesBySymptomsRequest {
    private Set<Symptom> symptoms;
}
