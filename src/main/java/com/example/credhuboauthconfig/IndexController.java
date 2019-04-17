package com.example.credhuboauthconfig;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    @GetMapping("/")
    Mono<String> greeting() {
        return Mono.just("hello");
    }
}
