package org.donntu.ai.backend;

import org.donntu.ai.backend.component.genetic.FitnessFunctionImpl;
import org.donntu.ai.backend.service.GeneticServiceMy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApplicationTests {

    private GeneticServiceMy geneticServiceMy = new GeneticServiceMy(new FitnessFunctionImpl());

    @Test
    public void geneticTest() {
        try {
            geneticServiceMy.getMaxFunctionValueOnInterval(
                    0,
                    15,
                    4,
                    100,
                    0.0001);
//            geneticServiceMy.crossing(new StringBuffer("11111"), new StringBuffer("00000"), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

