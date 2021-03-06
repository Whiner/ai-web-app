package org.donntu.ai.backend.dto.animals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Animal;
import org.donntu.ai.backend.entity.AnimalSign;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponse {
    private Long id;
    private String name;

    private Set<SignDto> signs;

    public static AnimalResponse of(Animal animal) {
        AnimalResponse response = new AnimalResponse();
        if (animal != null) {
            response.setId(animal.getId());
            response.setName(animal.getName());
            response.setSigns(
                    animal.getSigns()
                            .stream()
                            .map(SignDto::of)
                            .collect(Collectors.toSet()));
        }
        return response;
    }

    public static AnimalResponse of(Set<AnimalSign> signs) {
        AnimalResponse response = new AnimalResponse();
        response.setSigns(signs
                .stream()
                .map(SignDto::of)
                .collect(Collectors.toSet()));
        return response;
    }
}
