package org.donntu.ai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtremumRequest {
    private double lowerInterval;
    private double upperInterval;
    private int chromosomesCount;
    private int maxIterationsCount;
    private double mutationChance;
    private double crossingChance;
}
