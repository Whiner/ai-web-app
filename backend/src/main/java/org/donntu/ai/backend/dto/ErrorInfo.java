package org.donntu.ai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorInfo implements Serializable {
    @NonNull
    private String code;
    private String message;
}
