package com.example.credhuboauthconfig;

import javax.net.ssl.SSLContext;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.credhub.configuration.CredHubTemplateFactory;
import org.springframework.credhub.core.CredHubOperations;
import org.springframework.credhub.core.CredHubProperties;
import org.springframework.credhub.core.ReactiveCredHubOperations;
import org.springframework.credhub.core.ReactiveCredHubTemplate;
import org.springframework.credhub.support.ClientOptions;
import org.springframework.credhub.support.CredentialDetails;
import org.springframework.credhub.support.SimpleCredentialName;
import org.springframework.credhub.support.value.ValueCredential;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private final CredHubOperations credHubOperations;

    public IndexController(@Value("${spring.credhub.url}") String credHubUrl) {
        this.credHubOperations = buildMtlsCredHubOperations(credHubUrl);
    }

    private CredHubOperations buildMtlsCredHubOperations(String runtimeCredHubUrl) {
   		CredHubProperties credHubProperties = new CredHubProperties();
   		credHubProperties.setUrl(runtimeCredHubUrl);
   		return new CredHubTemplateFactory().credHubTemplate(credHubProperties, new ClientOptions());
   	}

    @GetMapping("/read")
    Mono<String> read() {
        return Mono.just(credHubOperations
                .credentials()
                .getByName(new SimpleCredentialName("/c/p.spring-cloud-gateway-service-scg-service-broker/client-certificate"), ValueCredential.class))
                .map(CredentialDetails::toString);
    }
}
