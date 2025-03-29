package dev.sgd.currencymate;

import dev.sgd.currencymate.exchangerate.MockExchangerateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { MockExchangerateConfig.class })
class ApplicationStartTest {

    @Test
    void contextLoads() {}

}