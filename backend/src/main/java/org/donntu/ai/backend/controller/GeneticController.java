package org.donntu.ai.backend.controller;

import lombok.RequiredArgsConstructor;
import org.donntu.ai.backend.dto.ExtremumRequest;
import org.donntu.ai.backend.service.GeneticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genetic")
@RequiredArgsConstructor
public class GeneticController {
    private final GeneticService geneticService;

    @GetMapping("/extremum")
    public Double getExtremum(ExtremumRequest request) throws Exception {
        return geneticService.getMaxExtremumX(
                request.getLowerInterval(),
                request.getUpperInterval(),
                request.getChromosomesCount(),
                request.getMaxIterationsCount(),
                request.getMutationChance(),
                request.getCrossingChance());
    }

}
