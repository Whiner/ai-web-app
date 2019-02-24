package org.donntu.ai.backend.dto.animals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.Animal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponse {
    private long id;
    private String name;
    private String imageUrl;

    public static AnimalResponse of(Animal animal) {
        return new AnimalResponse(animal.getId(), animal.getName(), animal.getImageUrl());
    }
}
