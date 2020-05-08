package com.example.credhuboauthconfig;

import org.springframework.credhub.core.ReactiveCredHubOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    public IndexController(ReactiveCredHubOperations credHubOperations) {
        credHubOperations.credentials().findByPath("/")
                         .doOnNext(summary -> LOGGER.info("credentials: {}", summary))
                         .blockFirst();
    }

    @GetMapping("/")
    Mono<String> greeting() {
        return Mono.just("hello");
    }
}
