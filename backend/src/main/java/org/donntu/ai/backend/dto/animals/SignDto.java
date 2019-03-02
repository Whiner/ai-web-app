package org.donntu.ai.backend.dto.animals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.AnimalSign;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignDto {
    private long id;
    private String name;

    public static SignDto of(AnimalSign sign) {
        return new SignDto(sign.getId(), sign.getName());
    }
}
