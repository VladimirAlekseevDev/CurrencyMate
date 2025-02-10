package dev.sgd.currencymate.services;

import dev.sgd.currencymate.domain.model.TestModel;
import org.springframework.stereotype.Service;

@Service
public class ServiceTest {


    public TestModel test() {
        return new TestModel("hello!");
    }

}