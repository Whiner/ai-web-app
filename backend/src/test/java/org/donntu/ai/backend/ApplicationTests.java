package org.donntu.ai.backend;

import org.donntu.ai.backend.component.genetic.FitnessFunctionImpl;
import org.donntu.ai.backend.service.GeneticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApplicationTests {

    private GeneticService geneticService = new GeneticService(new FitnessFunctionImpl());

    @Test
    public void geneticTest() {
        try {
            System.out.println(
                    geneticService.getMaxExtremumX(
                            0,
                            3,
                            20,
                            10000,
                            0.5,
                            0.5)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        // aDouble = NumericUtil.binaryStringToDouble("111111111111110", 0, 15);
    }

}

