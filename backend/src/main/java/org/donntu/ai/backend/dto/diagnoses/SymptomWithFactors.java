package org.donntu.ai.backend.dto.diagnoses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Symptom;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymptomWithFactors {
    private Long id;
    private String name;

    @Min(0)
    @Max(1)
    private Float confidence;

    @Min(0)
    @Max(1)
    private Float mistrust;

}
