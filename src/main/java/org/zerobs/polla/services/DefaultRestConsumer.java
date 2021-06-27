package org.zerobs.polla.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

@Service
public class DefaultRestConsumer implements RestConsumer {
    private final Duration requestTimeout;
    private final WebClient webClient;

    public DefaultRestConsumer(String baseUrl, Duration requestTimeout) {
        this.requestTimeout = requestTimeout;
        this.webClient = WebClient.create(baseUrl);
    }

    public DefaultRestConsumer(String baseUrl) {
        this(baseUrl, Duration.ofSeconds(10));
    }

    @Override
    public <T> T get(String path, Map<String, Object> queryParams) {
        return this.<T>getResponseEntity(path, queryParams).getBody();
    }

    @Override
    public <T> ResponseEntity<T> getResponseEntity(String path, Map<String, Object> queryParams, HttpStatus... ignoreStatuses) {
        return webClient
                .get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);
                    queryParams.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .retrieve()
                .onStatus(httpStatus -> Arrays.asList(ignoreStatuses).contains(httpStatus), clientResponse -> Mono.empty())
                .bodyToMono(new ParameterizedTypeReference<ResponseEntity<T>>() {
                })
                .block(requestTimeout);
    }
}
