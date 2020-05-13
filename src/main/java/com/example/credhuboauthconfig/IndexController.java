package com.example.credhuboauthconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.credhub.core.CredHubOperations;
import org.springframework.credhub.support.CredentialDetails;
import org.springframework.credhub.support.SimpleCredentialName;
import org.springframework.credhub.support.value.ValueCredential;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private final CredHubOperations credHubOperations;

    public IndexController(CredHubOperations credHubOperations) {
        this.credHubOperations = credHubOperations;
    }

    @GetMapping("/read")
    Mono<String> read() {
        return Mono.just(
                credHubOperations
                        .credentials()
                        .getByName(new SimpleCredentialName("/c/p.spring-cloud-gateway-service-scg-service-broker/client-certificate"), ValueCredential.class))
                   .map(CredentialDetails::toString);
    }
}
