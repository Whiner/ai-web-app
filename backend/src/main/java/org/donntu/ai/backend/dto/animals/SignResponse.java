package org.donntu.ai.backend.dto.animals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.donntu.ai.backend.entity.AnimalSign;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignResponse {
    private long id;
    private String name;

    public static SignResponse of(AnimalSign sign) {
        return new SignResponse(sign.getId(), sign.getName());
    }
}
