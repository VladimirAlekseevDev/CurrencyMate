package dev.sgd.currencymate.api;

import dev.sgd.currencymate.services.ServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final ServiceTest serviceTest;

    @GetMapping("/test")
    public String test() {
        return serviceTest.test().getMessage();
    }

}
