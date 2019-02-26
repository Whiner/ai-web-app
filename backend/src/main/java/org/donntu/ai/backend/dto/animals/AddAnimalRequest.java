package org.donntu.ai.backend.dto.animals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.AnimalSign;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAnimalRequest {
    private String name;
    private Set<AnimalSign> signs;
}
